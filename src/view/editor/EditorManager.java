package view.editor;

import util.FileUtil;
import view.components.StatusBar;
import view.dialogs.DialogFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class EditorManager extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTabbedPane tabbedPane;
    private StatusBar statusBar;
    private int fontSize = 14;
    private boolean darkMode = false;
    
    public EditorManager() {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        setupKeyboardShortcuts();
        setupTabChangeListener();
    }
    
    private void setupTabChangeListener() {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() != null) {
                    EditorTab editor = getEditorAtual();
                    if (editor.getArquivoAtual() != null) {
                        statusBar.setText(editor.getArquivoAtual().getAbsolutePath());
                    } else {
                        statusBar.setText("");
                    }
                }
            }
        });
    }
    
    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }
    
    public void adicionarNovaAba() {
        EditorTab editor = new EditorTab(fontSize);
        
        // Configurar o painel de edição
        JSplitPane splitPane = criarPainelEdicao(editor);
        
        // Adicionar ao tabbedPane
        tabbedPane.addTab("Novo", splitPane);
        int idx = tabbedPane.indexOfComponent(splitPane);
        tabbedPane.setTabComponentAt(idx, criarComponenteAba("Novo", splitPane));
        tabbedPane.setSelectedComponent(splitPane);
    }
    
    private JSplitPane criarPainelEdicao(EditorTab editor) {
        // Área de edição com números de linha
        JScrollPane scrollEditor = new JScrollPane(
            editor.getTextArea(),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        scrollEditor.setRowHeaderView(editor.getLineNumbers());
        
        // Área de mensagens
        JScrollPane scrollMensagens = new JScrollPane(
            editor.getMensagensArea(),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        
        // Painel dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, scrollMensagens);
        splitPane.setDividerLocation(500);
        splitPane.setOneTouchExpandable(true);
        splitPane.putClientProperty("editorTab", editor);
        
        // Configurar o listener de document para atualizar números de linha
        editor.configurarDocumentListener(splitPane);
        
        // Configurar zoom com scroll do mouse
        adicionarZoomScroll(editor.getTextArea());
        
        return splitPane;
    }
    
    private JPanel criarComponenteAba(String titulo, Component componente) {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnl.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo + "  ");
        pnl.add(lblTitulo);

        JButton btnFechar = new JButton("x");
        btnFechar.setMargin(new Insets(0, 4, 0, 4));
        btnFechar.setBorder(null);
        btnFechar.setFocusable(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setForeground(Color.RED);

        btnFechar.addActionListener(e -> {
            int idx = tabbedPane.indexOfComponent(componente);
            if (idx != -1) {
                EditorTab editor = (EditorTab) ((JSplitPane) componente).getClientProperty("editorTab");
                if (editor.isAlterado()) {
                    String[] opcoes = {"Sim", "Não", "Cancelar"};
                    int opt = DialogFactory.showYesNoCancelDialog(
                        this,
                        "O arquivo foi alterado. Deseja salvar antes de fechar?",
                        "Salvar alterações"
                    );
                    if (opt == 2 || opt == JOptionPane.CLOSED_OPTION) {
                        return; // Não fecha
                    }
                    if (opt == 0) { // Sim
                        tabbedPane.setSelectedIndex(idx); // Garante que está na aba certa
                        salvarArquivo();
                        if (editor.isAlterado()) return;
                    }
                    // Se Não (opt == 1), apenas fecha
                }
                tabbedPane.remove(idx);
            }
        });

        pnl.add(btnFechar);
        return pnl;
    }
    
    public void abrirArquivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            try {
                String conteudo = FileUtil.readFile(arquivo);
                
                EditorTab editor = new EditorTab(fontSize);
                editor.getTextArea().setText(conteudo);
                editor.setArquivoAtual(arquivo);
                
                JSplitPane splitPane = criarPainelEdicao(editor);
                
                tabbedPane.addTab(arquivo.getName(), splitPane);
                int idx = tabbedPane.indexOfComponent(splitPane);
                tabbedPane.setTabComponentAt(idx, criarComponenteAba(arquivo.getName(), splitPane));
                tabbedPane.setSelectedComponent(splitPane);
                
                statusBar.setText(arquivo.getAbsolutePath());
                SwingUtilities.invokeLater(() -> {
                    atualizarNumerosLinhas();
                });
            } catch (IOException ex) {
                DialogFactory.showErrorMessage(this, "Erro ao abrir o arquivo: " + ex.getMessage());
            }
        }
    }
    
    public void salvarArquivo() {
        EditorTab editor = getEditorAtual();
        try {
            if (editor.getArquivoAtual() == null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt")
                );

                if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File arquivo = chooser.getSelectedFile();
                    if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
                        arquivo = new File(arquivo.getAbsolutePath() + ".txt");
                    }
                    editor.setArquivoAtual(arquivo);
                } else {
                    return; // se cancelar, não faz nada
                }
            }
            
            FileUtil.writeFile(editor.getArquivoAtual(), editor.getTextArea().getText());
            editor.getMensagensArea().setText("");
            statusBar.setText(editor.getArquivoAtual().getAbsolutePath());
            editor.setAlterado(false);
            
            // Atualiza o título da aba
            JSplitPane split = (JSplitPane) tabbedPane.getSelectedComponent();
            atualizarTituloAba(split);
            
        } catch (IOException ex) {
            DialogFactory.showErrorMessage(this, "Erro ao salvar o arquivo: " + ex.getMessage());
        }
    }
    
    private void adicionarZoomScroll(JTextArea textArea) {
        textArea.addMouseWheelListener(e -> {
            if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                if (e.getWheelRotation() < 0) {
                    aumentarZoom();
                } else if (e.getWheelRotation() > 0) {
                    diminuirZoom();
                }
                e.consume();
            }
        });
    }
    
    private void atualizarNumerosLinhas() {
        getEditorAtual().atualizarNumerosLinhas();
    }
    
    private void mostrarMensagem(String mensagem) {
        getEditorAtual().getMensagensArea().setText(mensagem);
    }
    
    private void aumentarZoom() {
        if (fontSize < 48) {
            fontSize += 2;
            atualizarFonte();
        }
    }
    
    private void diminuirZoom() {
        if (fontSize > 8) {
            fontSize -= 2;
            atualizarFonte();
        }
    }
    
    private void atualizarFonte() {
        EditorTab editor = getEditorAtual();
        Font novaFonte = new Font("Monospaced", Font.PLAIN, fontSize);
        editor.getTextArea().setFont(novaFonte);
        editor.getLineNumbers().setFont(novaFonte);
    }
    
    private void alternarDarkMode() {
        darkMode = !darkMode;
        Color bg = darkMode ? new Color(40, 44, 52) : Color.WHITE;
        Color fg = darkMode ? new Color(187, 187, 187) : Color.BLACK;
        Color lineBg = darkMode ? new Color(30, 34, 40) : Color.LIGHT_GRAY;
        Color msgBg = darkMode ? new Color(50, 54, 62) : new Color(240, 240, 240);

        EditorTab editor = getEditorAtual();
        editor.getTextArea().setBackground(bg);
        editor.getTextArea().setForeground(fg);
        editor.getLineNumbers().setBackground(lineBg);
        editor.getLineNumbers().setForeground(fg);
        editor.getMensagensArea().setBackground(msgBg);
        editor.getMensagensArea().setForeground(fg);
        statusBar.setBackground(bg);
        statusBar.setForeground(fg);

        this.setBackground(bg);
        repaint();
    }
    
    public EditorTab getEditorAtual() {
        JSplitPane split = (JSplitPane) tabbedPane.getSelectedComponent();
        return (EditorTab) split.getClientProperty("editorTab");
    }
    
    private void atualizarTituloAba(JSplitPane splitPane) {
        EditorTab editor = (EditorTab) splitPane.getClientProperty("editorTab");
        int idx = tabbedPane.indexOfComponent(splitPane);
        if (idx != -1) {
            String nome = (editor.getArquivoAtual() != null) ? editor.getArquivoAtual().getName() : "Novo";
            if (editor.isAlterado()) nome = "*" + nome;
            // Atualiza o label do componente da aba
            Component tabComponent = tabbedPane.getTabComponentAt(idx);
            if (tabComponent instanceof JPanel) {
                for (Component c : ((JPanel) tabComponent).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setText(nome + "  ");
                    }
                }
            }
        }
    }
    
    public void acaoNovo() {
        String[] opcoes = { "Nova Aba", "Substituir Aba Atual", "Cancelar" };
        int escolha = DialogFactory.showOptionDialog(
                this,
                "O que deseja fazer?",
                "Novo Arquivo",
                opcoes);

        if (escolha == 0) { // Nova Aba
            adicionarNovaAba();
        } else if (escolha == 1) { // Substituir Aba Atual
            EditorTab editor = getEditorAtual();
            editor.getTextArea().setText("");
            editor.getMensagensArea().setText("");
            statusBar.setText("");
            editor.setArquivoAtual(null);
            editor.setAlterado(false);
            // Atualiza o nome da aba para "Novo" no componente customizado
            int idx = tabbedPane.getSelectedIndex();
            Component tabComponent = tabbedPane.getTabComponentAt(idx);
            if (tabComponent instanceof JPanel) {
                for (Component c : ((JPanel) tabComponent).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setText("Novo  ");
                    }
                }
            }
        }
    }
    
    private void setupKeyboardShortcuts() {
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "novo", this::acaoNovo);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "abrir", this::abrirArquivo);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "salvar", this::salvarArquivo);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "copiar",
                () -> getEditorAtual().getTextArea().copy());
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "colar",
                () -> getEditorAtual().getTextArea().paste());
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), "recortar",
                () -> getEditorAtual().getTextArea().cut());
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "compilar",
                () -> mostrarMensagem("Compilação de programas ainda não foi implementada."));
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "equipe",
                () -> mostrarMensagem("Equipe: Pedro Godri, Yasmin, Gabriel."));

        // Atalhos de zoom
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK), "zoomMais", this::aumentarZoom);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK), "zoomMais2",
                this::aumentarZoom); // Para o teclado padrão
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), "zoomMenos",
                this::diminuirZoom);

        // Atalho para alternar dark mode
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "darkMode", this::alternarDarkMode);
    }

    private void addAtalho(KeyStroke keyStroke, String nome, Runnable acao) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, nome);
        getActionMap().put(nome, new AbstractAction() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                acao.run();
            }
        });
    }
}