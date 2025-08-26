package view.editor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;

public class EditorTab {
    private JTextArea textArea;
    private JTextArea lineNumbers;
    private JTextArea mensagensArea;
    private File arquivoAtual;
    private int fontSize;
    private boolean alterado = false;

    public EditorTab(int fontSize) {
        this.fontSize = fontSize;
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Área principal de edição
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        
        // Área de numeração de linhas
        lineNumbers = new JTextArea("1");
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        lineNumbers.setEditable(false);
        lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setForeground(Color.BLACK);
        
        // Área de mensagens
        mensagensArea = new JTextArea();
        mensagensArea.setEditable(false);
        mensagensArea.setBackground(new Color(240, 240, 240));
        mensagensArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
    }
    
    public void configurarDocumentListener(JSplitPane splitPane) {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void removeUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void insertUpdate(DocumentEvent e) { updateLineNumbers(); }
            
            private void updateLineNumbers() {
                atualizarNumerosLinhas();
                if (!alterado) {
                    alterado = true;
                    if (splitPane != null) {
                        atualizarTituloAba(splitPane);
                    }
                }
            }
        });
    }
    
    public void atualizarNumerosLinhas() {
        int totalLinhas = textArea.getLineCount();
        StringBuilder numeros = new StringBuilder();
        for (int i = 1; i <= totalLinhas; i++) {
            numeros.append(i).append("\n");
        }
        lineNumbers.setText(numeros.toString());
    }
    
    private void atualizarTituloAba(JSplitPane splitPane) {
        JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, splitPane);
        if (tabbedPane != null) {
            int idx = tabbedPane.indexOfComponent(splitPane);
            if (idx != -1) {
                String nome = (arquivoAtual != null) ? arquivoAtual.getName() : "Novo";
                if (alterado) nome = "*" + nome;
                
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
    }

    // Getters e Setters
    public JTextArea getTextArea() {
        return textArea;
    }
    
    public JTextArea getLineNumbers() {
        return lineNumbers;
    }
    
    public JTextArea getMensagensArea() {
        return mensagensArea;
    }
    
    public File getArquivoAtual() {
        return arquivoAtual;
    }
    
    public void setArquivoAtual(File arquivoAtual) {
        this.arquivoAtual = arquivoAtual;
    }
    
    public boolean isAlterado() {
        return alterado;
    }
    
    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
    }
}