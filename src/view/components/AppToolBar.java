package view.components;

import java.awt.*;
import javax.swing.*;
import view.editor.EditorManager;

public class AppToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;
    
    private EditorManager editorManager;
    
    // Botões da toolbar
    private JButton btnNovo;
    private JButton btnAbrir;
    private JButton btnSalvar;
    private JButton btnCopiar;
    private JButton btnColar;
    private JButton btnRecortar;
    private JButton btnCompilar;
    private JButton btnEquipe;
    
    public AppToolBar(EditorManager editorManager) {
        this.editorManager = editorManager;
        setFloatable(false);
        setPreferredSize(new Dimension(1500, 70));
        initializeButtons();
        setupEventListeners();
    }
    
    private void initializeButtons() {
        Dimension btnSize = new Dimension(110, 65);
        
        btnNovo = createToolButton("Novo [Ctrl+N]", "/assets/icons/new.png", btnSize);
        btnAbrir = createToolButton("Abrir [Ctrl+O]", "/assets/icons/open.png", btnSize);
        btnSalvar = createToolButton("Salvar [Ctrl+S]", "/assets/icons/save.png", btnSize);
        btnCopiar = createToolButton("Copiar [Ctrl+C]", "/assets/icons/copy.png", btnSize);
        btnColar = createToolButton("Colar [Ctrl+V]", "/assets/icons/paste.png", btnSize);
        btnRecortar = createToolButton("Recortar [Ctrl+X]", "/assets/icons/cutting.png", btnSize);
        btnCompilar = createToolButton("Compilar [F7]", "/assets/icons/play.png", btnSize);
        btnEquipe = createToolButton("Equipe [F1]", "/assets/icons/team.png", btnSize);
        
        // Adicionar botões à toolbar
        add(btnNovo);
        add(btnAbrir);
        add(btnSalvar);
        add(btnCopiar);
        add(btnColar);
        add(btnRecortar);
        add(btnCompilar);
        add(btnEquipe);
    }
    
    private JButton createToolButton(String text, String iconPath, Dimension size) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        return button;
    }
    
    private void setupEventListeners() {
        btnNovo.addActionListener(e -> editorManager.acaoNovo());
        btnAbrir.addActionListener(e -> editorManager.abrirArquivo());
        btnSalvar.addActionListener(e -> editorManager.salvarArquivo());
        btnCopiar.addActionListener(e -> {
            JTextArea textArea = ((EditorManager)editorManager).getClass().cast(editorManager).getEditorAtual().getTextArea();
            textArea.copy();
        });
        btnColar.addActionListener(e -> {
            JTextArea textArea = ((EditorManager)editorManager).getClass().cast(editorManager).getEditorAtual().getTextArea();
            textArea.paste();
        });
        btnRecortar.addActionListener(e -> {
            JTextArea textArea = ((EditorManager)editorManager).getClass().cast(editorManager).getEditorAtual().getTextArea();
            textArea.cut();
        });
        btnCompilar.addActionListener(e -> {
            JTextArea mensagensArea = ((EditorManager)editorManager).getClass().cast(editorManager).getEditorAtual().getMensagensArea();
            mensagensArea.setText("Compilação de programas ainda não foi implementada.");
        });
        btnEquipe.addActionListener(e -> {
            JTextArea mensagensArea = ((EditorManager)editorManager).getClass().cast(editorManager).getEditorAtual().getMensagensArea();
            mensagensArea.setText("Equipe: Gabriel Bugmann Vansuita, Pedro Henrique Godri, Yasmin Victória Alves de Souza.");
        });
    }
}