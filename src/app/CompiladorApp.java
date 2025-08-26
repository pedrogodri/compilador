package app;

import view.MainFrame;
import java.awt.EventQueue;

public class CompiladorApp {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
