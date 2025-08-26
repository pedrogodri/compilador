package view.components;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JLabel {
    private static final long serialVersionUID = 1L;
    
    public StatusBar() {
        setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(new Dimension(1500, 25));
    }
}