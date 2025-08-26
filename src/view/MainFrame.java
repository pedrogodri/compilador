package view;

import view.components.StatusBar;
import view.components.AppToolBar;
import view.editor.EditorManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private EditorManager editorManager;
    private StatusBar statusBar;
    private AppToolBar toolBar;

    public MainFrame() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/icons/code.png")));
        initializeComponents();
        configureFrame();
    }
    
    private void initializeComponents() {
        // Criar gerenciador de editor com abas
        editorManager = new EditorManager();
        
        // Criar toolbar com botões
        toolBar = new AppToolBar(editorManager);
        
        // Criar barra de status
        statusBar = new StatusBar();
        editorManager.setStatusBar(statusBar);
        
        // Layout do frame principal
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(editorManager, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        // Cria a primeira aba
        editorManager.adicionarNovaAba();
    }
    
    private void configureFrame() {
        setTitle("Compilador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setResizable(true);
        setLocationRelativeTo(null);
    }
}