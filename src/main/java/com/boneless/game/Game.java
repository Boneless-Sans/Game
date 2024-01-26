package com.boneless.game;

import com.boneless.game.util.AudioPlayer;
import com.boneless.game.util.JsonFile;
import com.boneless.game.util.Print;

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

import static com.boneless.game.util.Print.*;

public class Game extends JFrame implements KeyListener {
    private static boolean getDebugState;
    private boolean debug = false;
    private static boolean playerMoving;
    private boolean movementCooldown = false;
    private Player player;
    public Game(String inDebug){
        if(Objects.equals(inDebug, "dev")){
            this.debug = true;
        }
        initUI();
        gameLoop();
    }
    private void initUI(){
        getDebugState = debug;
        if(debug){
            printDebug("Debugging Enabled!");
        }
        addWindowListener(adapter());
        addKeyListener(this);
        setSize(1200,900);
        setLocationRelativeTo(null);
        //add(new MainMenu(this));

        JPanel gameBoard = gameBoard();
        add(gameBoard);
        player = new Player(this);
        gameBoard.add(player);
        player.update();

        setVisible(true);
    }
    private void gameLoop(){
        Thread gameLoop = new Thread(() -> {
            while (true) {
                updateTick(); // Update game state
                try {
                    Thread.sleep(16); // Adjust the sleep time based on your desired frame rate (here, roughly 60 fps)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameLoop.start();
    }
    private void updateTick(){
        player.update();
    }
    private JPanel gameBoard(){
        JPanel panel = new JPanel(null);
        //panel.setBackground(Color.red);
        return panel;
    }
    private JPanel pauseMenu(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
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
    //Player input
    private String parseKeyStrokeInput(String binding){
        String key = JsonFile.read("settings.json","keybindings", binding);
        return switch (key){
            case "Esc" -> "\u001B";
            case "Space" -> " ";
            case "Enter" -> "\n";
            case "Backspace" -> "\b";
            default -> key.toLowerCase();
        };
    }
    @Override
    public void keyTyped(KeyEvent e) {
        if(String.valueOf(e.getKeyChar()).equals(parseKeyStrokeInput("pause"))){
            System.exit(0);
        }
        if(e.getKeyChar() == 'r' && debug){
            dispose();
            printColor("blue","RELOADING!");
            new Game("dev");
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (Objects.requireNonNull(getDirectionKey(e))) {
            case "up" -> player.move("up");
            case "down" -> player.move("down");
            case "left" -> player.move("left");
            case "right" -> player.move("right");
        }
    }
    @Override
    public void keyReleased(KeyEvent e){}
    private String getDirectionKey(KeyEvent e){
        String upKey = parseKeyStrokeInput("up");
        String downKey = parseKeyStrokeInput("down");
        String leftKey = parseKeyStrokeInput("left");
        String rightKey = parseKeyStrokeInput("right");
        if(String.valueOf(e.getKeyChar()).equals(upKey)){
            return "up";
        }else if(String.valueOf(e.getKeyChar()).equals(downKey)) {
            return "down";
        }else if(String.valueOf(e.getKeyChar()).equals(leftKey)) {
            return "left";
        }else if(String.valueOf(e.getKeyChar()).equals(rightKey)){
            return "right";
        }else {
            return String.valueOf(e);
        }
    }
    public static boolean getDebugState(){
        return getDebugState;
    }
    public static boolean playerMoving(){
        return playerMoving;
    }
}