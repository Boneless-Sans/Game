package com.boneless.game.mapObjects;

import com.boneless.game.util.JsonFile;

import javax.swing.*;
import java.awt.*;

public abstract class MapObject extends JPanel {
    protected boolean debug;
    protected String xPos;
    protected String yPos;
    protected String fileName;
    protected int sizeX;
    protected int sizeY;
    protected JFrame frame;

    public MapObject(String fileName, JFrame frame, boolean debug) {
        this.debug = debug;
        this.frame = frame;
        this.fileName = fileName;

        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "x");
        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "y");
        this.sizeX = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "sizeX"));
        this.sizeY = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "objects", getObjectName(), "sizeY"));
        setBackground(getBackgroundColor());
        if (containsNumbers(xPos) && containsNumbers(yPos)) {
            setBounds(Integer.parseInt(xPos), Integer.parseInt(yPos), sizeX, sizeY);
        } else {
            int newXPos = switch (xPos) {
                case "l" -> 0;
                case "r" -> frame.getWidth() - sizeX - 10;
                case "m" -> (frame.getWidth() / 2) - 40;
                default -> Integer.parseInt(xPos);
            };
            int newYPos = switch (yPos) {
                case "t" -> 0;
                case "b" -> frame.getHeight() - sizeY - 35;
                case "m" -> (frame.getHeight() / 2) - 40;
                default -> Integer.parseInt(yPos);
            };
            setBounds(newXPos, newYPos, sizeX, sizeY);
        }
    }
    public MapObject(String fileName, JFrame frame, boolean debug, int blockNum) {
        this.debug = debug;
        this.frame = frame;
        this.fileName = fileName;

        System.out.println("Block: " + blockNum);
        System.out.println(fileName);
        this.xPos = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks",getObjectName() + blockNum, "x");
        this.yPos = JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks",getObjectName() + blockNum, "y");
        this.sizeX = Integer.parseInt(JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks", getObjectName() + blockNum, "sizeX"));
        this.sizeY = Integer.parseInt(JsonFile.readWithFourKeys("maps/" + fileName, "objects", "blocks", getObjectName() + blockNum, "sizeY"));
        setBackground(getBackgroundColor());
        if (containsNumbers(xPos) && containsNumbers(yPos)) {
            setBounds(Integer.parseInt(xPos), Integer.parseInt(yPos), sizeX, sizeY);
        } else {
            int newXPos = switch (xPos) {
                case "l" -> 0;
                case "r" -> frame.getWidth() - sizeX - 10;
                case "m" -> (frame.getWidth() / 2) - 40;
                default -> Integer.parseInt(xPos);
            };
            int newYPos = switch (yPos) {
                case "t" -> 0;
                case "b" -> frame.getHeight() - sizeY - 35;
                case "m" -> (frame.getHeight() / 2) - 40;
                default -> Integer.parseInt(yPos);
            };
            setBounds(newXPos, newYPos, sizeX, sizeY);
        }
    }

    protected String getObjectName() {
        return "Insert Name Here";
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
}
