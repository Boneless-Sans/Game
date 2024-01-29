package com.boneless.game.mapObjects;

import com.boneless.game.util.JsonFile;

import javax.swing.*;
import java.awt.*;

public class MapObject extends JPanel {
    protected boolean debug;
    protected String xPos;
    protected String yPos;
    protected int sizeX;
    protected int sizeY;
    protected JFrame frame;

    public MapObject(String fileName, JFrame frame, boolean debug) {
        this.debug = debug;
        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "x");
        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "y");
        this.sizeX = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "sizeX"));
        this.sizeY = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", getObjectName(), "sizeY"));
        this.frame = frame;
        System.out.println(xPos + " " + yPos + " " + " " + sizeX + " " + sizeY + "\n" +
                getX() + " " + getY() + " " + getWidth() + " " + getHeight());
        initMapObject();
    }

    protected String getObjectName() {
        return "Insert Name Here";
    }

    private void initMapObject() {
        setBackground(getBackgroundColor());
        if (containsNumbers(xPos) && containsNumbers(yPos)) {
            setBounds(Integer.parseInt(xPos), Integer.parseInt(yPos), sizeX, sizeY);
            if (debug) {
                printColor("cyan", getObjectName() + " contained no letters");
            }
        } else {
            int newXPos = switch (xPos) {
                case "l" -> 0;
                case "r" -> frame.getWidth() - sizeX - 10;
                default -> Integer.parseInt(xPos);
            };
            int newYPos = switch (yPos) {
                case "t" -> 0;
                case "b" -> frame.getHeight() - sizeY - 35;
                default -> Integer.parseInt(yPos);
            };
            setBounds(newXPos, newYPos, sizeX, sizeY);
            if (debug) {
                printColor("cyan", getObjectName() + " contained letters");
            }
        }
    }

    private boolean containsNumbers(String str) {
        return str.matches("[0-9]+");
    }

    protected Color getBackgroundColor() {
        return Color.black;
    }

    private void printColor(String cyan, String s) {
        System.out.println(s);
    }
}
