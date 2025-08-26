package view.dialogs;

import javax.swing.*;
import java.awt.*;

public class DialogFactory {
    
    /**
     * Exibe uma mensagem de erro
     */
    public static void showErrorMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Exibe uma mensagem informativa
     */
    public static void showInfoMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Informação",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Exibe um diálogo com opções Sim/Não/Cancelar
     * @return 0 para Sim, 1 para Não, 2 para Cancelar
     */
    public static int showYesNoCancelDialog(Component parent, String message, String title) {
        String[] opcoes = {"Sim", "Não", "Cancelar"};
        return JOptionPane.showOptionDialog(
            parent,
            message,
            title,
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );
    }
    
    /**
     * Exibe um diálogo com opções personalizadas
     * @return índice da opção escolhida (0 para a primeira opção)
     */
    public static int showOptionDialog(Component parent, String message, String title, String[] options) {
        return JOptionPane.showOptionDialog(
            parent,
            message,
            title,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
    }
}