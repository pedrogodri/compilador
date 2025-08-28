package view;

import java.awt.*;
import javax.swing.*;
import view.components.AppToolBar;
import view.components.StatusBar;
import view.editor.EditorManager;

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
    
    /**
     * Cria o gerenciador de editor de abas, a toolbar,
     * o layout do frame principal e cria a primeira aba automaticamente
     */
    private void initializeComponents() {
        editorManager = new EditorManager();
        
        toolBar = new AppToolBar(editorManager);
        
        statusBar = new StatusBar();
        editorManager.setStatusBar(statusBar);
        
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(editorManager, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
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