package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextArea textArea;
    private JTextArea mensagensArea;
    private JLabel statusBar;
    private File arquivoAtual;
    private JTextArea lineNumbers;

    private int fontSize = 14; // tamanho inicial da fonte
    private boolean darkMode = false;

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
        for (JButton b : new JButton[]{btnNovo, btnAbrir, btnSalvar, btnCopiar, btnColar, btnRecortar, btnCompilar, btnEquipe}) {
            b.setPreferredSize(tamanhoBotao);
            toolBar.add(b);
        }

        contentPane.add(toolBar, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));

        JScrollPane scrollEditor = new JScrollPane(
                textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        lineNumbers = new JTextArea("1");
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        lineNumbers.setEditable(false);
        lineNumbers.setBackground(Color.LIGHT_GRAY);
        scrollEditor.setRowHeaderView(lineNumbers);
        scrollEditor.setRowHeaderView(lineNumbers);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void removeUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void insertUpdate(DocumentEvent e) { updateLineNumbers(); }

            private void updateLineNumbers() {
                int totalLinhas = textArea.getLineCount();
                StringBuilder numeros = new StringBuilder();
                for (int i = 1; i <= totalLinhas; i++) {
                    numeros.append(i).append("\n");
                }
                lineNumbers.setText(numeros.toString());
            }
        });

        mensagensArea = new JTextArea();
        mensagensArea.setEditable(false);
        mensagensArea.setBackground(new Color(240, 240, 240));
        mensagensArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollMensagens = new JScrollPane(
                mensagensArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollEditor, scrollMensagens);
        splitPane.setDividerLocation(500);
        splitPane.setOneTouchExpandable(true);
        contentPane.add(splitPane, BorderLayout.CENTER);

        statusBar = new JLabel();
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        contentPane.add(statusBar, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> acaoNovo());
        btnAbrir.addActionListener(e -> acaoAbrir());
        btnSalvar.addActionListener(e -> acaoSalvar());
        btnCopiar.addActionListener(e -> textArea.copy());
        btnColar.addActionListener(e -> textArea.paste());
        btnRecortar.addActionListener(e -> textArea.cut());
        btnCompilar.addActionListener(e -> mostrarMensagem("Compilação de programas ainda não foi implementada."));
        btnEquipe.addActionListener(e -> mostrarMensagem("Equipe: Pedro Godri, Yasmin, Gabriel."));

        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "novo", this::acaoNovo);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "abrir", this::acaoAbrir);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "salvar", this::acaoSalvar);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "copiar", textArea::copy);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), "colar", textArea::paste);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), "recortar", textArea::cut);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "compilar", () -> mostrarMensagem("Compilação de programas ainda não foi implementada."));
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "equipe", () -> mostrarMensagem("Equipe: Pedro Godri, Yasmin, Gabriel."));

        // Atalhos de zoom
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK), "zoomMais", this::aumentarZoom);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK), "zoomMais2", this::aumentarZoom); // Para o teclado padrão
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), "zoomMenos", this::diminuirZoom);

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

    private void acaoNovo() {
        textArea.setText("");
        mensagensArea.setText("");
        statusBar.setText("");
        arquivoAtual = null;
    }

    private void acaoAbrir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            arquivoAtual = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoAtual))) {
                textArea.setText(""); 
                textArea.setText(reader.lines().reduce("", (a, b) -> a + (a.isEmpty() ? "" : "\n") + b));
                mensagensArea.setText("");
                statusBar.setText(arquivoAtual.getAbsolutePath());
                SwingUtilities.invokeLater(() -> {
                    atualizarNumerosLinhas();
                });
            } catch (IOException ex) {
                mostrarMensagem("Erro ao abrir o arquivo: " + ex.getMessage());
            }
        }
    }

    private void atualizarNumerosLinhas() {
        int totalLinhas = textArea.getLineCount();
        StringBuilder numeros = new StringBuilder();
        for (int i = 1; i <= totalLinhas; i++) {
            numeros.append(i).append("\n");
        }
        // Se o lineNumbers for um atributo:
        lineNumbers.setText(numeros.toString());
    }


    private void acaoSalvar() {
        try {
            if (arquivoAtual == null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

                if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    arquivoAtual = chooser.getSelectedFile();
                    if (!arquivoAtual.getName().toLowerCase().endsWith(".txt")) {
                        arquivoAtual = new File(arquivoAtual.getAbsolutePath() + ".txt");
                    }
                } else {
                    return; // se cancelar, não faz nada
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoAtual))) {
                textArea.write(writer);
                mensagensArea.setText("");
                statusBar.setText(arquivoAtual.getAbsolutePath());
            }
        } catch (IOException ex) {
            mostrarMensagem("Erro ao salvar o arquivo: " + ex.getMessage());
        }
    }

    private void mostrarMensagem(String mensagem) {
        mensagensArea.setText(mensagem);
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
        Font novaFonte = new Font("Monospaced", Font.PLAIN, fontSize);
        textArea.setFont(novaFonte);
        lineNumbers.setFont(novaFonte);
    }

    private void alternarDarkMode() {
        darkMode = !darkMode;
        Color bg = darkMode ? new Color(40, 44, 52) : Color.WHITE;
        Color fg = darkMode ? new Color(187, 187, 187) : Color.BLACK;
        Color lineBg = darkMode ? new Color(30, 34, 40) : Color.LIGHT_GRAY;
        Color msgBg = darkMode ? new Color(50, 54, 62) : new Color(240, 240, 240);

        textArea.setBackground(bg);
        textArea.setForeground(fg);
        lineNumbers.setBackground(lineBg);
        lineNumbers.setForeground(fg);
        mensagensArea.setBackground(msgBg);
        mensagensArea.setForeground(fg);
        statusBar.setBackground(bg);
        statusBar.setForeground(fg);

        // Opcional: barra de ferramentas e painel principal
        getContentPane().setBackground(bg);
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}
