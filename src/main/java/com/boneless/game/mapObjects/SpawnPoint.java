package com.boneless.game.mapObjects;

import com.boneless.game.util.JsonFile;

import javax.swing.*;
import java.awt.*;

public class SpawnPoint extends JPanel {
    private String xPos;
    private String yPos;
    private int sizeX;
    private int sizeY;
    private JFrame frame;
    public SpawnPoint(String fileName, JFrame frame){
        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn","x");
        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn","y");
        this.sizeX = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn", "sizeX"));
        this.sizeY = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn", "sizeY"));
        this.frame = frame;
        initSpawn();
    }
    private void initSpawn(){
        setBackground(Color.green);
        if(xPos.hasdigieg);
    }
}
