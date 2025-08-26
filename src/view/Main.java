package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private int fontSize = 14; // tamanho inicial da fonte
    private boolean darkMode = false;

    private JTabbedPane tabbedPane;
    private JLabel statusBar;
    
    // Declare os botões como campos da classe para o WindowBuilder reconhecer
    private JButton btnNovo;
    private JButton btnAbrir;
    private JButton btnSalvar;
    private JButton btnCopiar;
    private JButton btnColar;
    private JButton btnRecortar;
    private JButton btnCompilar;
    private JButton btnEquipe;
    private JToolBar toolBar;

    private class EditorTab {
        JTextArea textArea;
        JTextArea lineNumbers;
        JTextArea mensagensArea;
        File arquivoAtual;
        int fontSize;
        boolean alterado = false; // NOVO: indica se houve alteração

        EditorTab(int fontSize) {
            this.fontSize = fontSize;
            textArea = new JTextArea();
            textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
            lineNumbers = new JTextArea("1");
            lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
            lineNumbers.setEditable(false);
            lineNumbers.setBackground(Color.LIGHT_GRAY);

            mensagensArea = new JTextArea();
            mensagensArea.setEditable(false);
            mensagensArea.setBackground(new Color(240, 240, 240));
            mensagensArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        }
    }

    public Main() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/assets/icons/code.png")));
        initializeComponents();
        setupEventListeners();
        setupKeyboardShortcuts();
        adicionarNovaAba(); // Cria a primeira aba ao iniciar
    }
    
    /**
     * Inicializa os componentes da interface - método compatível com WindowBuilder
     */
    private void initializeComponents() {
        setTitle("Compilador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Criar toolbar
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(getWidth(), 70));
        contentPane.add(toolBar, BorderLayout.NORTH);

        // Criar botões
        btnNovo = new JButton("Novo [Ctrl+N]");
        btnNovo.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/new.png")));
        btnNovo.setPreferredSize(new Dimension(160, 50));
        btnNovo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNovo.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnNovo);

        btnAbrir = new JButton("Abrir [Ctrl+O]");
        btnAbrir.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/open.png")));
        btnAbrir.setPreferredSize(new Dimension(160, 50));
        btnAbrir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAbrir.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnAbrir);

        btnSalvar = new JButton("Salvar [Ctrl+S]");
        btnSalvar.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/save.png")));
        btnSalvar.setPreferredSize(new Dimension(160, 50));
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnSalvar);

        btnCopiar = new JButton("Copiar [Ctrl+C]");
        btnCopiar.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/copy.png")));
        btnCopiar.setPreferredSize(new Dimension(160, 50));
        btnCopiar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCopiar.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnCopiar);

        btnColar = new JButton("Colar [Ctrl+V]");
        btnColar.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/paste.png")));
        btnColar.setPreferredSize(new Dimension(160, 50));
        btnColar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnColar.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnColar);

        btnRecortar = new JButton("Recortar [Ctrl+X]");
        btnRecortar.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/cutting.png")));
        btnRecortar.setPreferredSize(new Dimension(160, 50));
        btnRecortar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnRecortar.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnRecortar);

        btnCompilar = new JButton("Compilar [F7]");
        btnCompilar.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/play.png")));
        btnCompilar.setPreferredSize(new Dimension(160, 50));
        btnCompilar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCompilar.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnCompilar);

        btnEquipe = new JButton("Equipe [F1]");
        btnEquipe.setIcon(new ImageIcon(Main.class.getResource("/assets/icons/team.png")));
        btnEquipe.setPreferredSize(new Dimension(160, 50));
        btnEquipe.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEquipe.setVerticalTextPosition(SwingConstants.BOTTOM);
        toolBar.add(btnEquipe);

        Dimension btnSize = new Dimension(110, 65);

        btnNovo.setPreferredSize(btnSize);
        btnNovo.setMinimumSize(btnSize);
        btnNovo.setMaximumSize(btnSize);

        btnAbrir.setPreferredSize(btnSize);
        btnAbrir.setMinimumSize(btnSize);
        btnAbrir.setMaximumSize(btnSize);

        btnSalvar.setPreferredSize(btnSize);
        btnSalvar.setMinimumSize(btnSize);
        btnSalvar.setMaximumSize(btnSize);

        btnCopiar.setPreferredSize(btnSize);
        btnCopiar.setMinimumSize(btnSize);
        btnCopiar.setMaximumSize(btnSize);

        btnColar.setPreferredSize(btnSize);
        btnColar.setMinimumSize(btnSize);
        btnColar.setMaximumSize(btnSize);

        btnRecortar.setPreferredSize(btnSize);
        btnRecortar.setMinimumSize(btnSize);
        btnRecortar.setMaximumSize(btnSize);

        btnCompilar.setPreferredSize(btnSize);
        btnCompilar.setMinimumSize(btnSize);
        btnCompilar.setMaximumSize(btnSize);

        btnEquipe.setPreferredSize(btnSize);
        btnEquipe.setMinimumSize(btnSize);
        btnEquipe.setMaximumSize(btnSize);

        // Criar TabbedPane
        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // Criar status bar
        statusBar = new JLabel();
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        contentPane.add(statusBar, BorderLayout.SOUTH);
    }
    
    /**
     * Configura os event listeners
     */
    private void setupEventListeners() {
        // Event listeners dos botões
        btnNovo.addActionListener(e -> acaoNovo());
        btnAbrir.addActionListener(e -> acaoAbrir());
        btnSalvar.addActionListener(e -> acaoSalvar());
        btnCopiar.addActionListener(e -> getEditorAtual().textArea.copy());
        btnColar.addActionListener(e -> getEditorAtual().textArea.paste());
        btnRecortar.addActionListener(e -> getEditorAtual().textArea.cut());
        btnCompilar.addActionListener(e -> mostrarMensagem("Compilação de programas ainda não foi implementada."));
        btnEquipe.addActionListener(e -> mostrarMensagem("Equipe: Pedro Godri, Yasmin, Gabriel."));

        // Listener para mudança de aba
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() != null) {
                EditorTab editor = getEditorAtual();
                if (editor.arquivoAtual != null) {
                    statusBar.setText(editor.arquivoAtual.getAbsolutePath());
                } else {
                    statusBar.setText("");
                }
            }
        });
    }
    
    /**
     * Configura os atalhos de teclado
     */
    private void setupKeyboardShortcuts() {
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "novo", this::acaoNovo);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "abrir", this::acaoAbrir);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "salvar", this::acaoSalvar);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "copiar",
                () -> getEditorAtual().textArea.copy());
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "colar",
                () -> getEditorAtual().textArea.paste());
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), "recortar",
                () -> getEditorAtual().textArea.cut());
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
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, nome);
        getRootPane().getActionMap().put(nome, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acao.run();
            }
        });
    }

    private void acaoNovo() {
        String[] opcoes = { "Nova Aba", "Substituir Aba Atual", "Cancelar" };
        int escolha = JOptionPane.showOptionDialog(
                this,
                "O que deseja fazer?",
                "Novo Arquivo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha == 0) { // Nova Aba
            adicionarNovaAba();
        } else if (escolha == 1) { // Substituir Aba Atual
            EditorTab editor = getEditorAtual();
            editor.textArea.setText("");
            editor.mensagensArea.setText("");
            statusBar.setText("");
            editor.arquivoAtual = null;
            editor.alterado = false;
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

    private void acaoAbrir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            EditorTab editor = new EditorTab(fontSize);

            // Carrega o conteúdo do arquivo
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                StringBuilder sb = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    sb.append(linha).append("\n");
                }
                editor.textArea.setText(sb.toString());
                editor.arquivoAtual = arquivo; // IMPORTANTE!
                editor.alterado = false;
            } catch (IOException ex) {
                mostrarMensagem("Erro ao abrir o arquivo: " + ex.getMessage());
            }

            final JSplitPane[] splitPaneRef = new JSplitPane[1];

            JScrollPane scrollEditor = new JScrollPane(
                    editor.textArea,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollEditor.setRowHeaderView(editor.lineNumbers);

            editor.textArea.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) { updateLineNumbers(); }
                public void removeUpdate(DocumentEvent e) { updateLineNumbers(); }
                public void insertUpdate(DocumentEvent e) { updateLineNumbers(); }
                private void updateLineNumbers() {
                    int totalLinhas = editor.textArea.getLineCount();
                    StringBuilder numeros = new StringBuilder();
                    for (int i = 1; i <= totalLinhas; i++) {
                        numeros.append(i).append("\n");
                    }
                    editor.lineNumbers.setText(numeros.toString());
                    if (!editor.alterado) {
                        editor.alterado = true;
                        if (splitPaneRef[0] != null) {
                            atualizarTituloAba(splitPaneRef[0]);
                        }
                    }
                }
            });

            JScrollPane scrollMensagens = new JScrollPane(
                    editor.mensagensArea,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, scrollMensagens);
            splitPane.setDividerLocation(500);
            splitPane.setOneTouchExpandable(true);

            splitPane.putClientProperty("editorTab", editor);

            splitPaneRef[0] = splitPane;

            tabbedPane.addTab(arquivo.getName(), splitPane);
            int idx = tabbedPane.indexOfComponent(splitPane);
            tabbedPane.setTabComponentAt(idx, criarComponenteAba(arquivo.getName(), splitPane));
            tabbedPane.setSelectedComponent(splitPane);

            statusBar.setText(arquivo.getAbsolutePath());
            SwingUtilities.invokeLater(() -> {
                atualizarNumerosLinhas();
            });
        }
    }

    private void atualizarNumerosLinhas() {
        EditorTab editor = getEditorAtual();
        int totalLinhas = editor.textArea.getLineCount();
        StringBuilder numeros = new StringBuilder();
        for (int i = 1; i <= totalLinhas; i++) {
            numeros.append(i).append("\n");
        }
        editor.lineNumbers.setText(numeros.toString());
    }

    private void acaoSalvar() {
        EditorTab editor = getEditorAtual();
        try {
            if (editor.arquivoAtual == null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(
                        new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

                if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    editor.arquivoAtual = chooser.getSelectedFile();
                    if (!editor.arquivoAtual.getName().toLowerCase().endsWith(".txt")) {
                        editor.arquivoAtual = new File(editor.arquivoAtual.getAbsolutePath() + ".txt");
                    }
                    // Troca o nome da aba
                    int idx = tabbedPane.getSelectedIndex();
                    tabbedPane.setTitleAt(idx, editor.arquivoAtual.getName());
                } else {
                    return; // se cancelar, não faz nada
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(editor.arquivoAtual))) {
                editor.textArea.write(writer);
                editor.mensagensArea.setText("");
                statusBar.setText(editor.arquivoAtual.getAbsolutePath());
                editor.alterado = false; // MARCA COMO NÃO ALTERADO APÓS SALVAR
                // Atualiza o título da aba para remover o *
                JSplitPane split = (JSplitPane) tabbedPane.getSelectedComponent();
                atualizarTituloAba(split);
            }
        } catch (IOException ex) {
            mostrarMensagem("Erro ao salvar o arquivo: " + ex.getMessage());
        }
    }

    private void mostrarMensagem(String mensagem) {
        getEditorAtual().mensagensArea.setText(mensagem);
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
        editor.textArea.setFont(novaFonte);
        editor.lineNumbers.setFont(novaFonte);
    }

    private void alternarDarkMode() {
        darkMode = !darkMode;
        Color bg = darkMode ? new Color(40, 44, 52) : Color.WHITE;
        Color fg = darkMode ? new Color(187, 187, 187) : Color.BLACK;
        Color lineBg = darkMode ? new Color(30, 34, 40) : Color.LIGHT_GRAY;
        Color msgBg = darkMode ? new Color(50, 54, 62) : new Color(240, 240, 240);

        EditorTab editor = getEditorAtual();
        editor.textArea.setBackground(bg);
        editor.textArea.setForeground(fg);
        editor.lineNumbers.setBackground(lineBg);
        editor.lineNumbers.setForeground(fg);
        editor.mensagensArea.setBackground(msgBg);
        editor.mensagensArea.setForeground(fg);
        statusBar.setBackground(bg);
        statusBar.setForeground(fg);

        getContentPane().setBackground(bg);
        repaint();
    }

    private void adicionarNovaAba() {
        EditorTab editor = new EditorTab(fontSize);

        final JSplitPane[] splitPaneRef = new JSplitPane[1];

        JScrollPane scrollEditor = new JScrollPane(
                editor.textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollEditor.setRowHeaderView(editor.lineNumbers);

        editor.textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void removeUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void insertUpdate(DocumentEvent e) { updateLineNumbers(); }
            private void updateLineNumbers() {
                int totalLinhas = editor.textArea.getLineCount();
                StringBuilder numeros = new StringBuilder();
                for (int i = 1; i <= totalLinhas; i++) {
                    numeros.append(i).append("\n");
                }
                editor.lineNumbers.setText(numeros.toString());
                if (!editor.alterado) {
                    editor.alterado = true;
                    if (splitPaneRef[0] != null) {
                        atualizarTituloAba(splitPaneRef[0]);
                    }
                }
            }
        });

        JScrollPane scrollMensagens = new JScrollPane(
                editor.mensagensArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, scrollMensagens);
        splitPane.setDividerLocation(500);
        splitPane.setOneTouchExpandable(true);

        splitPane.putClientProperty("editorTab", editor);

        splitPaneRef[0] = splitPane;

        tabbedPane.addTab("Novo", splitPane);
        int idx = tabbedPane.indexOfComponent(splitPane);
        tabbedPane.setTabComponentAt(idx, criarComponenteAba("Novo", splitPane));
        tabbedPane.setSelectedComponent(splitPane);
    }

    // Adicione este método na sua classe Main
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
                if (editor.alterado) {
                    String[] opcoes = {"Sim", "Não", "Cancelar"};
                    int opt = JOptionPane.showOptionDialog(
                        this,
                        "O arquivo foi alterado. Deseja salvar antes de fechar?",
                        "Salvar alterações",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                    );
                    if (opt == 2 || opt == JOptionPane.CLOSED_OPTION) {
                        return; // Não fecha
                    }
                    if (opt == 0) { // Sim
                        tabbedPane.setSelectedIndex(idx); // Garante que está na aba certa
                        acaoSalvar();
                        if (editor.alterado) return;
                    }
                    // Se Não (opt == 1), apenas fecha
                }
                tabbedPane.remove(idx);
            }
        });

        pnl.add(btnFechar);

        return pnl;
    }

    private EditorTab getEditorAtual() {
        JSplitPane split = (JSplitPane) tabbedPane.getSelectedComponent();
        return (EditorTab) split.getClientProperty("editorTab");
    }

    // Atualiza o título da aba, adicionando/removendo o * conforme o estado de alteração
    private void atualizarTituloAba(JSplitPane splitPane) {
        EditorTab editor = (EditorTab) splitPane.getClientProperty("editorTab");
        int idx = tabbedPane.indexOfComponent(splitPane);
        if (idx != -1) {
            String nome = (editor.arquivoAtual != null) ? editor.arquivoAtual.getName() : "Novo";
            if (editor.alterado) nome = "*" + nome;
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}