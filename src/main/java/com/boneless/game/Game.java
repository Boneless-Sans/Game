package com.boneless.game;

import com.boneless.game.util.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

public class Game extends JFrame implements KeyListener {
    private boolean debug = false;
    private final String[] debugColors = {"\u001B[30m", "\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m"};
    //Black - 0, Red - 1, Green - 2, Yellow - 3, Blue - 4, Purple - 5, Cyan - 6, White - 7
    //Rule of Thumb: Green: General Debugging info, Red: Error
    private final Player player = new Player();
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
        addKeyListener(this);
        setSize(1200,900);
        setLocationRelativeTo(null);
        add(new MainMenu());

//        JPanel gameBoard = gameBoard();
//        add(gameBoard);
//        gameBoard.add(player);

        setVisible(true);
    }
    private JPanel gameBoard(){
        JPanel panel = new JPanel(null);
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

    @Override
    public void keyTyped(KeyEvent e) {
        if(debug && e.getKeyChar() == 'w'){

        }
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e){}

    private static class MainMenu extends JPanel{
        public MainMenu(){
            setBackground(Color.red);
        }
    }
}
