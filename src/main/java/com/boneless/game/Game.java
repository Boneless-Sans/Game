package com.boneless.game;

import com.boneless.game.util.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

public class Game extends JFrame {
    private boolean debug = false;
    private final String[] debugColors = {"\u001B[30m", "\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m"};
    //Black - 0, Red - 1, Green - 2, Yellow - 3, Blue - 4, Purple - 5, Cyan - 6, White - 7
    //Green: General Debugging info, Red: Error
    public Game(String inDebug){
        if(Objects.equals(inDebug, "dev")){
            this.debug = true;
        }
        initUI();
    }
    private void initUI(){
        if(debug){
            System.out.println(debugColors[2] + "Debugging Enabled!" + debugColors[7]);
        }
        addWindowListener(adapter());
        setSize(1200,900);
        setLocationRelativeTo(null);

        add(gameBoard());

        setVisible(true);
    }
    private JPanel gameBoard(){
        JPanel panel = new JPanel(new GridLayout(10,10,2,2));
        panel.setBackground(Color.red);
        return panel;
    }
    private WindowAdapter adapter(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    if(!debug) {
                        AudioPlayer.playSound("bye.wav");
                        dispose();
                        Thread.sleep(1500);
                    }
                    System.exit(0);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
