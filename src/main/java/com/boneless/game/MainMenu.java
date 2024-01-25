package com.boneless.game;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends Frame{
    public MainMenu(String args){
        new Game(args);
    }
    private JLayeredPane backgroundPanel(){
        JLayeredPane panel = new JLayeredPane();
        panel.setBackground(Color.lightGray);
        return panel;
    }
}
