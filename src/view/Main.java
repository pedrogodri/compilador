package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import utils.gals.Lexico;
import utils.gals.Semantico;
import utils.gals.Sintatico;
import utils.gals.exceptions.LexicalError;
import utils.gals.exceptions.SemanticError;
import utils.gals.exceptions.SyntaticError;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    /// Tamanho inicial da fonte
    private int fontSize = 14;
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
        boolean alterado = false;

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
        adicionarNovaAba();
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

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setPreferredSize(new Dimension(getWidth(), 70));
        contentPane.add(toolBar, BorderLayout.NORTH);

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
        btnCompilar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                acaoCompilar();
        	}
        });
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

        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        statusBar = new JLabel();
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setPreferredSize(new Dimension(getWidth(), 25));
        contentPane.add(statusBar, BorderLayout.SOUTH);
    }
    
    /**
     * Configura os event listeners
     */
    private void setupEventListeners() {
        btnNovo.addActionListener(e -> acaoNovo());
        btnAbrir.addActionListener(e -> acaoAbrir());
        btnSalvar.addActionListener(e -> acaoSalvar());
        btnCopiar.addActionListener(e -> getEditorAtual().textArea.copy());
        btnColar.addActionListener(e -> getEditorAtual().textArea.paste());
        btnRecortar.addActionListener(e -> getEditorAtual().textArea.cut());
        btnEquipe.addActionListener(e -> mostrarMensagem("Equipe: Gabriel Bugmann Vansuita, Pedro Henrique Godri, Yasmin Victória Alves de Souza."));

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
                () -> acaoCompilar());
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "equipe",
                () -> mostrarMensagem("Equipe: Gabriel Bugmann Vansuita, Pedro Henrique Godri, Yasmin Victória Alves de Souza."));

        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK), "zoomMais", this::aumentarZoom);
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK), "zoomMais2",
                this::aumentarZoom); // Para o teclado padrão
        addAtalho(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), "zoomMenos",
                this::diminuirZoom);

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
        if (tabbedPane.getTabCount() == 0) {
            adicionarNovaAba();
        } else {
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
    
            if (escolha == 0) {
                adicionarNovaAba();
            } else if (escolha == 1) {
                EditorTab editor = getEditorAtual();
                editor.textArea.setText("");
                editor.mensagensArea.setText("");
                statusBar.setText("");
                editor.arquivoAtual = null;
                editor.alterado = false;

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
    }

    private void acaoAbrir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto (*.txt)", "txt"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            EditorTab editor = new EditorTab(fontSize);

            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                StringBuilder sb = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    sb.append(linha).append("\n");
                }
                editor.textArea.setText(sb.toString());
                editor.arquivoAtual = arquivo;
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

            adicionarZoomScroll(editor.textArea);
        }
    }

    /**
     * Realiza a compilação lexica da aba atual 
     */
    private void acaoCompilar() {
        // Lexico lexico = new Lexico();
        // System.out.println(getEditorAtual().textArea.getLineCount());
        // lexico.setInput(getEditorAtual().textArea.getText());
        // try {
        //     Token t = null;
        //     String texto = String.format("%-10s %-20s %-10s%n","Linha", "Classe", "Lexema");

        //     while ( (t = lexico.nextToken()) != null ) {
        //     texto += String.format("%-10s %-20s %-10s%n", t.getPosition(), 
        //         Constants.TOKEN_NAMES.getOrDefault(t.getId(), "Token desconhecido"), t.getLexeme());
            
                // só escreve o lexema, necessário escrever t.getId, t.getPosition()
        
                // t.getId () - retorna o identificador da classe (ver Constants.java) 
                // necessário adaptar, pois deve ser apresentada a classe por extenso
            
                // t.getPosition () - retorna a posição inicial do lexema no editor 
                // necessário adaptar para mostrar a linha	

                // esse código apresenta os tokens enquanto não ocorrer erro
                // no entanto, os tokens devem ser apresentados SÓ se não ocorrer erro,
                // necessário adaptar para atender o que foi solicitado		   
            // }
            
        //     texto += "\nprograma compilado com sucesso";
        //     mostrarMensagem(texto);
        // }
        // catch (AnalysisError error) {  // tratamento de erros
        //     if (error instanceof SimboloInvalidoError simError)
        //         mostrarMensagem("linha " + simError.getPosition() + ": " + simError.getCaracter() + " " 
        //         + simError.getMessage());
        //     else
        //         mostrarMensagem("linha " + error.getPosition() + ": " + error.getMessage());
        
            // e.getMessage() - retorna a mensagem de erro de SCANNER_ERRO (ver ScannerConstants.java)
            // necessário adaptar conforme o enunciado da parte 2
        
            // e.getPosition() - retorna a posição inicial do erro 
            // necessário adaptar para mostrar a linha  
        // }

            Lexico lexico = new Lexico();
            Sintatico sintatico = new Sintatico();
            Semantico semantico = new Semantico();
            lexico.setInput(getEditorAtual().textArea.getText());
            
            try {
                sintatico.parse(lexico, semantico);    // tradução dirigida pela sintaxe
                mostrarMensagem("programa compilado com sucesso");
            }
            // mensagem: programa compilado com sucesso - na área reservada para mensagens
            catch ( LexicalError e ) {
                // tratar erros léxicos, conforme especificação da parte 2 - analisador léxico
            }
            catch ( SyntaticError e ) {
                mostrarMensagem(e.getMessage() + " em " + e.getPosition());

                // e.getMessage() são os símbolos esperados
                // e.getMessage() - retorna a mensagem de erro de PARSER_ERROR (ver ParserConstants.java)
                // necessário adaptar conforme o enunciado da parte 3
                
                // e.getPosition() - retorna a posição inicial do erro 
                // necessário adaptar para mostrar a linha  
                        
                // necessário mostrar também o símbolo encontrado 
            }
            catch ( SemanticError e ) {
                // trata erros semânticos na parte 4
            }
    }

    private void adicionarZoomScroll(JTextArea textArea) {
        textArea.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                    if (e.getWheelRotation() < 0) {
                        aumentarZoom();
                    } else if (e.getWheelRotation() > 0) {
                        diminuirZoom();
                    }
                    e.consume();
                }
            }
        });
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
                    
                    int idx = tabbedPane.getSelectedIndex();
                    tabbedPane.setTitleAt(idx, editor.arquivoAtual.getName());
                } else {
                    return;
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(editor.arquivoAtual))) {
                editor.textArea.write(writer);
                editor.mensagensArea.setText("");
                statusBar.setText(editor.arquivoAtual.getAbsolutePath());
                editor.alterado = false;
                
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

        adicionarZoomScroll(editor.textArea);

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

    /**
     * Adicione este método na sua classe Main
     * @param titulo Título do componente
     * @param componente O componente em si
     * @return Nulo para sair em certos casos
     */
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
                        return;
                    }
                    if (opt == 0) { // Sim
                        tabbedPane.setSelectedIndex(idx);
                        acaoSalvar();
                        if (editor.alterado) return;
                    }
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

    /**
     * Atualiza o título da aba, adicionando/removendo o * conforme o estado de alteração
     * @param splitPane Painel
     */
    private void atualizarTituloAba(JSplitPane splitPane) {
        EditorTab editor = (EditorTab) splitPane.getClientProperty("editorTab");
        int idx = tabbedPane.indexOfComponent(splitPane);
        if (idx != -1) {
            String nome = (editor.arquivoAtual != null) ? editor.arquivoAtual.getName() : "Novo";
            if (editor.alterado) nome = "*" + nome;
            
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