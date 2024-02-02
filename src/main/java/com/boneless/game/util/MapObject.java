package com.boneless.game.util;

import com.boneless.game.Player;

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
        System.out.println(xPos + " " + yPos + " " + " " + sizeX + " " + sizeY + "\n" +
                getX() + " " + getY() + " " + getWidth() + " " + getHeight());
        initMapObject();
    }

    protected String getObjectName() {
        return "Insert Name Here";
    }

//    private void initMapObject() {
//        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "x");
//        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "y");
//        this.sizeX = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "sizeX"));
//        this.sizeY = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "sizeY"));
//        setBackground(getBackgroundColor());
//        if (containsNumbers(xPos) && containsNumbers(yPos)) {
//            setBounds(Integer.parseInt(xPos), Integer.parseInt(yPos), sizeX, sizeY);
//            if (debug) {
//                printColor(getObjectName() + " contained no letters");
//            }
//        } else {
//            int newXPos = switch (xPos) {
//                case "l" -> 0;
//                case "r" -> frame.getWidth() - sizeX - 10;
//                case "m" -> (frame.getWidth() / 2) - 40;
//                default -> Integer.parseInt(xPos);
//            };
//            int newYPos = switch (yPos) {
//                case "t" -> 0;
//                case "b" -> frame.getHeight() - sizeY - 35;
//                case "m" -> (frame.getHeight() / 2) - 40;
//                default -> Integer.parseInt(yPos);
//            };
//            setBounds(newXPos, newYPos, sizeX, sizeY);
//            if (debug) {
//                printColor(getObjectName() + " contained letters");
//            }
//        }
//    }

    private boolean containsNumbers(String str) {
        return str.matches("[0-9]+");
    }

    protected Color getBackgroundColor() {
        return Color.black;
    }

    private void printColor(String s) {
        System.out.println(s);
    }

    protected void initMapObject() {
        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "x");
        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "y");
        this.sizeX = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "sizeX"));
        this.sizeY = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "sizeY"));
        setBackground(getBackgroundColor());
        if (containsNumbers(xPos) && containsNumbers(yPos)) {
            setBounds(Integer.parseInt(xPos), Integer.parseInt(yPos), sizeX, sizeY);
            if (debug) {
                printColor(getObjectName() + " contained no letters");
            }
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
            if (debug) {
                printColor(getObjectName() + " contained letters");
            }
        }
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
