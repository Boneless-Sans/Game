package com.boneless.game.menus;

import com.boneless.game.Game;
import com.boneless.game.util.ScrollGridPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.boneless.game.util.Print.*;

public class MainMenu extends JFrame{
    private boolean debug = false;
    private String selectedMap;
    public MainMenu(String args){
        if(Objects.equals(args, "dev")){
            debug = true;
            printDebug("MainMenu Skipped!");
        }
        initUI();
    }
    private void initUI(){
        setSize(720,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Game?");
        setIconImage(new ImageIcon("src/main/resources/assets/textures/player.png").getImage());

        ScrollGridPanel backgroundPanel = new ScrollGridPanel(new BorderLayout());

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(new Color(0,0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = 0;

        JLabel title = new JLabel("Game");
        title.setForeground(Color.white);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));

        titlePanel.add(title, gbc);

        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(mainPanel(), BorderLayout.CENTER);
        backgroundPanel.add(spacer(100), BorderLayout.SOUTH);
        backgroundPanel.add(spacer(100), BorderLayout.EAST);
        backgroundPanel.add(spacer(100), BorderLayout.WEST);

        add(backgroundPanel);

        setVisible(true);
    }
    private JPanel mainPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setPreferredSize(new Dimension(100,100));

        JButton button = new JButton();
        button.setText("text");
        button.addActionListener(e -> {
            if(debug){
                dispose();
                new Game("dev", "testMap");
            }else{
                dispose();
                new Game(null, 1);
            }
        });

        panel.add(button);
        return panel;
    }
    private JPanel spacer(int size){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(size, size));
        panel.setBackground(new Color(0,0,0,0));
        panel.setOpaque(true);
        return panel;
    }
}
