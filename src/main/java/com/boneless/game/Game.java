package com.boneless.game;

import com.boneless.game.util.AudioPlayer;
import com.boneless.game.util.JsonFile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game extends JFrame implements KeyListener {
    private static boolean getDebugState;
    private boolean debug = false;
    private static boolean playerMoving;
    private boolean movementCooldown = false;
    private final String[] debugColors = {"\u001B[37m", "\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[30m"};
    //White - 0, Red - 1, Green - 2, Yellow - 3, Blue - 4, Purple - 5, Cyan - 6,Black - 7
    private final Player player = new Player(this);
    public Game(String inDebug){
        if(Objects.equals(inDebug, "dev")){
            this.debug = true;
        }
        initUI();
    }
    private void initUI(){
        getDebugState = debug;
        if(debug){
            System.out.println(debugColors[2] + "Debugging Enabled!" + debugColors[7]);
        }
        addWindowListener(adapter());
        addKeyListener(this);
        setSize(1200,900);
        setLocationRelativeTo(null);
        //add(new MainMenu(this));

        JPanel gameBoard = gameBoard();
        add(gameBoard);
        gameBoard.add(player);

        setVisible(true);
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
        if(e.getKeyChar() == 'r'){
            dispose();
            printError("RELOADING!");
            new Game("dev");
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        String direction = getDirectionKey(e);
        if (direction != null) {
            int nextX = player.getX();
            int nextY = player.getY();

            // Calculate next position based on direction
            switch (direction) {
                case "up":
                    nextY -= player.getMoveSpeed();
                    break;
                case "down":
                    nextY += player.getMoveSpeed();
                    break;
                case "left":
                    nextX -= player.getMoveSpeed();
                    break;
                case "right":
                    nextX += player.getMoveSpeed();
                    break;
            }

            // Check if next position is within bounds
            if (nextX >= 0 && nextX <= getWidth() - player.getWidth() && nextY >= 0 && nextY <= getHeight() - player.getHeight()) {
                player.move(direction);
            } else {
                // If next move would exceed boundaries, do nothing
                if (debug) {
                    printError("Next move would exceed boundaries!");
                }
            }
        } else if (debug) {
            printError("Somehow, direction is null");
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        String direction = getDirectionKey(e);
        if(direction != null){
            player.stopMoving(direction);
        }
    }
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
            return null;
        }
    }

    private void printError(String text){
        System.out.println(debugColors[1] + text + debugColors[0]);
    }
    public static boolean getDebugState(){
        return getDebugState;
    }
    public static boolean playerMoving(){
        return playerMoving;
    }
}