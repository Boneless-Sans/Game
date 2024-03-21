package com.boneless.game.util;

import com.boneless.game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public abstract class MapObject extends JPanel {
    protected boolean debug;
    protected String xPos;
    protected String yPos;
    protected String fileName;
    protected String sizeX;
    protected String sizeY;
    protected String anchor;
    protected JFrame frame;

    public MapObject(String fileName, JFrame frame, boolean debug) {
        this.debug = debug;
        this.frame = frame;
        this.fileName = fileName;

        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "x");
        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "y");
        this.sizeX = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "sizeX");
        this.sizeY = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "sizeY");
        this.anchor = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "a");

        int newXPos = getNewX(xPos);
        int newYPos = getNewY(yPos);
        int newSizeX = getNewSizeX(sizeX);
        int newSizeY = getNewSizeY(sizeY);
        setBounds(newXPos, newYPos, newSizeX, newSizeY);
        setBackground(getBackgroundColor());
    }
    public MapObject(String fileName, JFrame frame, boolean debug, int blockNum) {
        this.debug = debug;
        this.frame = frame;
        this.fileName = fileName;

        this.xPos = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks",getObjectName() + blockNum, "x");
        this.yPos = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks",getObjectName() + blockNum, "y");
        this.sizeX = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks", getObjectName() + blockNum, "sizeX");
        this.sizeY = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks", getObjectName() + blockNum, "sizeY");
        this.anchor = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks", getObjectName() + blockNum, "a");

        int newXPos = getNewX(xPos);
        int newYPos = getNewY(yPos);
        int newSizeX = getNewSizeX(sizeX);
        int newSizeY = getNewSizeY(sizeY);
        setBounds(newXPos, newYPos, newSizeX, newSizeY);

    }
    private int getNewX(String xPos){
        return switch (xPos) {
            case "l" -> 0;
            case "r" -> frame.getWidth() - getNewSizeX(sizeX) - 10;
            case "m" -> (frame.getWidth() / 2) - (getNewSizeX(sizeX) / 2);
            default -> Integer.parseInt(xPos);
        };
    }
    private int getNewY(String yPos){
        return switch (yPos) {
            case "t" -> 0;
            case "b" -> frame.getHeight() - getNewSizeY(sizeY) - 35;
            case "m" -> (frame.getHeight() / 2) - (getNewSizeY(sizeY) / 2);
            default -> Integer.parseInt(yPos);
        };
    }
    private int getNewSizeX(String sizeX){
        if(Objects.equals(sizeX, "s")){
            return frame.getWidth();
        }else if(sizeX != null && sizeX.matches("[0-9]+")){
            return Integer.parseInt(sizeX);
        }else{
            if(debug){
                System.err.println("Invalid Size X");
            }
            return 0;
        }
    }
    private int getNewSizeY(String sizeY){
        if(Objects.equals(sizeX, "s")){
            return frame.getWidth();
        }else if(sizeY != null && sizeY.matches("[0-9]+")){
            return Integer.parseInt(sizeY);
        }else{
            if(debug){
                System.err.println("Invalid Size Y");
            }
            return 0;
        }
    }
//    private int getAnchor(String anchor){
//        return switch(anchor){
//            case"tl" -> 0;
//            default -> 0;
//        }
//    } scale properly or something
    protected String getObjectName() {
        return "Shit if fucked up if this gets returned";
    }

    private boolean containsNumbers(String str) {
        return str.matches("[0-9]+");
    }

    protected Color getBackgroundColor() {
        return Color.black;
    }
    public static class SpawnPoint extends MapObject {
        public SpawnPoint(String fileName, JFrame frame, boolean debug) {
            super(fileName, frame, debug);
        }

        @Override
        protected String getObjectName() {
            return "spawn";
        }

        @Override
        protected Color getBackgroundColor() {
            return Color.green;
        }
    }
    public static class Goal extends MapObject{
        public Goal(String fileName, JFrame frame, boolean debug) {
            super(fileName, frame, debug);
        }

        @Override
        protected String getObjectName(){
            return "goal";
        }
        @Override
        protected Color getBackgroundColor() {
            return Color.red;
        }
        public void reachedGoal() {
            if(debug) {
                System.out.println("Reached goal");
            }
        }
    }
    public static class Block extends MapObject{

        public Block(String fileName, JFrame frame, int blockNum, boolean debug) {
            super(fileName, frame, debug, blockNum);
        }
        @Override
        protected String getObjectName(){
            return "block";
        }
        @Override
        protected Color getBackgroundColor() {
            return Color.yellow;
        }
        public void playerCollided(Player player) {
            System.out.println("Player died");
            player.setIsAlive(false);
        }
    }
}
