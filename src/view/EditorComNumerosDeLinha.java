package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class EditorComNumerosDeLinha extends JPanel {

    private JTextArea textArea;
    private JTextArea lineNumbers;

    public EditorComNumerosDeLinha() {
        setLayout(new BorderLayout());

        // Área principal de edição
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Força barras de rolagem sempre visíveis
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Área de numeração de linhas
        lineNumbers = new JTextArea("1");
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, 14));
        lineNumbers.setEditable(false);
        lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setForeground(Color.BLACK);

        // Coloca a numeração na "row header" do JScrollPane
        scrollPane.setRowHeaderView(lineNumbers);

        // Atualiza numeração quando o texto muda
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void removeUpdate(DocumentEvent e) { updateLineNumbers(); }
            public void insertUpdate(DocumentEvent e) { updateLineNumbers(); }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateLineNumbers() {
        int totalLinhas = textArea.getLineCount();
        StringBuilder numeros = new StringBuilder();
        for (int i = 1; i <= totalLinhas; i++) {
            numeros.append(i).append("\n");
        }
        lineNumbers.setText(numeros.toString());
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
