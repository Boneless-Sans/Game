package com.boneless.game;

import com.boneless.game.mapObjects.Block;
import com.boneless.game.mapObjects.MapObject;
import com.boneless.game.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.boneless.game.util.Print.*;

public class Game extends JFrame implements KeyListener {
    private static boolean getDebugState;
    private boolean debug = false;
    private final boolean doCustomMap;
    private boolean gamePaused = false;
    private int mapNum;
    private final List<JComponent> mapObjects = new ArrayList<>();
    private final String mapName;
    private Player player;
    private JPanel gameBoard;

    public Game(String inDebug, int mapNum){
        if(Objects.equals(inDebug, "dev")){
            this.debug = true;
        }
        this.mapNum = mapNum;
        mapName = "level" + mapNum;
        doCustomMap = false;
        initUI();
        gameLoop();
    }
    public Game(String inDebug, String mapName){
        if(Objects.equals(inDebug, "dev")){
            this.debug = true;
        }
        this.mapName = mapName;
        doCustomMap = true;
        initUI();
        gameLoop();
    }
    private void initUI(){
        getDebugState = debug;
        addWindowListener(adapter());
        addKeyListener(this);
        setSize(1200,900);
        setLocationRelativeTo(null);
        setResizable(false);

        gameBoard = new JPanel(null);
        add(gameBoard);

        MapObject.SpawnPoint point;
        MapObject.Goal goal;
        //add collision blocks
        for(int i = 1; i < JsonFile.countBlocks("maps/" + mapName, "objects","blocks") + 1; i++){
            System.out.println("Block Number: " + i);
            gameBoard.add(new Block(mapName, this, i, debug));
        }

        point = doCustomMap ? new MapObject.SpawnPoint(mapName, this, debug) :
                new MapObject.SpawnPoint("level" + mapNum, this, debug);
        goal = doCustomMap ? new MapObject.Goal(mapName, this, debug) :
                new MapObject.Goal("level" + mapNum, this, debug);
        mapObjects.add(goal);

        player = new Player(this, point);
        gameBoard.add(player);
        player.update();

        gameBoard.add(point);
        gameBoard.add(goal);
        setVisible(true);
    }
    //Main game loop, for checking things at all times
    private void gameLoop(){
        Thread gameLoop = new Thread(() -> {
            while (true) {
                updateTick(); // Update game state
                checkCollision();
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
        if(!player.isAlive()){
            gameBoard.remove(player);
        }
    }
    private void checkCollision(){
        for(JComponent component: mapObjects){
            if(isColliding(component)){
                handleCollision((MapObject) component);
            }
        }
    }
    private boolean isColliding(JComponent component){
        int playerX = player.getX();
        int playerY = player.getY();
        int playerWidth = player.getWidth();
        int playerHeight = player.getHeight();

        int objectX = component.getX();
        int objectY = component.getY();
        int objectWidth = component.getWidth();
        int objectHeight = component.getHeight();

        return playerX < objectX + objectWidth &&
                playerX + playerWidth > objectX &&
                playerY < objectY + objectHeight &&
                playerY + playerHeight > objectY;
    }
    private void handleCollision(MapObject mapObject) {
        if (mapObject instanceof MapObject.Goal) {
            ((MapObject.Goal) mapObject).reachedGoal();
        } else if (mapObject instanceof Block) {
            ((Block) mapObject).playerCollided(player);
        }
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
            if(!gamePaused && !debug) {
                add(new PauseMenu(this));
                gamePaused = true;
            }else if(debug){
                System.exit(0);
            }
        }
        if(e.getKeyChar() == 'r' && debug){
            dispose();
            printColor("blue","RELOADING!");
            new Game("dev", mapName);
        }
    }
    private final Set<String> pressedKeys = new HashSet<>();
    @Override
    public void keyPressed(KeyEvent e) {
        // Get the direction corresponding to the pressed key
        String direction = getDirectionKey(e);

        // Add the direction to the set of pressed keys
        if (direction != null && !gamePaused) {
            pressedKeys.add(direction);

            // Call the move method with the updated set of pressed keys
            player.move(pressedKeys);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Get the direction corresponding to the released key
        String direction = getDirectionKey(e);

        // Remove the direction from the set of pressed keys
        if (direction != null) {
            pressedKeys.remove(direction);

            // Call the move method with the updated set of pressed keys
            player.move(pressedKeys);
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
            return String.valueOf(e);
        }
    }
    public static boolean getDebugState(){
        return getDebugState;
    }
    private void unLoadMap(){
        gameBoard.removeAll();
        initUI();
    }
    private void loadMap(String fileName){

    }
    private class PauseMenu extends JPanel implements KeyListener {
        private final JFrame frame;
        public PauseMenu(JFrame frame){
            this.frame = frame;
            if(debug){
                printDebug("Paused");
            }
            initUI();
        }
        private void initUI(){
            setBackground(Color.BLACK);
            setBounds(0,0,frame.getWidth(), frame.getHeight());
            addKeyListener(this);
        }
        @Override
        public void keyTyped(KeyEvent e) {
            if(String.valueOf(e.getKeyChar()).equals(parseKeyStrokeInput("Esc"))){
                Print.print("unpause");
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
    }
}