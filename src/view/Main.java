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
    private JLabel statusBar; // <-- Adicione esta linha

    private class EditorTab {
        JTextArea textArea;
        JTextArea lineNumbers;
        JTextArea mensagensArea;
        File arquivoAtual;
        int fontSize;

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
        setTitle("Compilador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(getWidth(), 70));

        JButton btnNovo = criarBotao("Novo [Ctrl+N]", "icons/novo.png");
        JButton btnAbrir = criarBotao("Abrir [Ctrl+O]", "icons/abrir.png");
        JButton btnSalvar = criarBotao("Salvar [Ctrl+S]", "icons/salvar.png");
        JButton btnCopiar = criarBotao("Copiar [Ctrl+C]", "icons/copiar.png");
        JButton btnColar = criarBotao("Colar [Ctrl+V]", "icons/colar.png");
        JButton btnRecortar = criarBotao("Recortar [Ctrl+X]", "icons/recortar.png");
        JButton btnCompilar = criarBotao("Compilar [F7]", "icons/compilar.png");
        JButton btnEquipe = criarBotao("Equipe [F1]", "icons/equipe.png");

        Dimension tamanhoBotao = new Dimension(160, 50);
        for (JButton b : new JButton[] { btnNovo, btnAbrir, btnSalvar, btnCopiar, btnColar, btnRecortar, btnCompilar,
                btnEquipe }) {
            b.setPreferredSize(tamanhoBotao);
            toolBar.add(b);
        }

        contentPane.add(toolBar, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        adicionarNovaAba(); // Cria a primeira aba ao iniciar

        statusBar = new JLabel(); // <-- Remova a declaração local, use o atributo
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        contentPane.add(statusBar, BorderLayout.SOUTH);

        tabbedPane.addChangeListener(e -> {
            EditorTab editor = getEditorAtual();
            if (editor.arquivoAtual != null) {
                statusBar.setText(editor.arquivoAtual.getAbsolutePath());
            } else {
                statusBar.setText("");
            }
        });

        btnNovo.addActionListener(e -> acaoNovo());
        btnAbrir.addActionListener(e -> acaoAbrir());
        btnSalvar.addActionListener(e -> acaoSalvar());
        btnCopiar.addActionListener(e -> getEditorAtual().textArea.copy());
        btnColar.addActionListener(e -> getEditorAtual().textArea.paste());
        btnRecortar.addActionListener(e -> getEditorAtual().textArea.cut());
        btnCompilar.addActionListener(e -> mostrarMensagem("Compilação de programas ainda não foi implementada."));
        btnEquipe.addActionListener(e -> mostrarMensagem("Equipe: Pedro Godri, Yasmin, Gabriel."));

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

        // Adicione um atalho para alternar o dark mode (Ctrl+D, por exemplo)
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "darkMode", this::alternarDarkMode);
    }

    private JButton criarBotao(String texto, String caminhoIcone) {
        JButton botao = new JButton(texto);
        return botao;
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

    // Atualize os métodos para usar o editor da aba atual:
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
            // Atualiza o nome da aba para "Novo"
            int idx = tabbedPane.getSelectedIndex();
            tabbedPane.setTitleAt(idx, "Novo");
        }
        // Se cancelar, não faz nada
    }

    private void acaoAbrir() {
        EditorTab editor = getEditorAtual();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            editor.arquivoAtual = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(editor.arquivoAtual))) {
                editor.textArea.setText("");
                editor.textArea.setText(reader.lines().reduce("", (a, b) -> a + (a.isEmpty() ? "" : "\n") + b));
                editor.mensagensArea.setText("");
                statusBar.setText(editor.arquivoAtual.getAbsolutePath());
                SwingUtilities.invokeLater(() -> {
                    atualizarNumerosLinhas();
                });
                // Troca o nome da aba
                int idx = tabbedPane.getSelectedIndex();
                tabbedPane.setTitleAt(idx, editor.arquivoAtual.getName());
            } catch (IOException ex) {
                mostrarMensagem("Erro ao abrir o arquivo: " + ex.getMessage());
            }
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

        JScrollPane scrollEditor = new JScrollPane(
                editor.textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollEditor.setRowHeaderView(editor.lineNumbers);

        editor.textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            private void updateLineNumbers() {
                int totalLinhas = editor.textArea.getLineCount();
                StringBuilder numeros = new StringBuilder();
                for (int i = 1; i <= totalLinhas; i++) {
                    numeros.append(i).append("\n");
                }
                editor.lineNumbers.setText(numeros.toString());
            }
        });

        JScrollPane scrollMensagens = new JScrollPane(
                editor.mensagensArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, scrollMensagens);
        splitPane.setDividerLocation(500);
        splitPane.setOneTouchExpandable(true);

        // Associe o EditorTab ao splitPane
        splitPane.putClientProperty("editorTab", editor);

        tabbedPane.addTab("Novo", splitPane);
        tabbedPane.setSelectedComponent(splitPane);
    }

    private EditorTab getEditorAtual() {
        JSplitPane split = (JSplitPane) tabbedPane.getSelectedComponent();
        return (EditorTab) split.getClientProperty("editorTab");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}