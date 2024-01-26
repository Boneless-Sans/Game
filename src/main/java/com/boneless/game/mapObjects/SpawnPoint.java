package com.boneless.game.mapObjects;

import com.boneless.game.util.JsonFile;
import com.boneless.game.util.Print;

import javax.swing.*;
import java.awt.*;

import static com.boneless.game.util.Print.printColor;

public class SpawnPoint extends JPanel {
    private boolean debug;
    private String xPos;
    private String yPos;
    private int sizeX;
    private int sizeY;
    private JFrame frame;
    public SpawnPoint(String fileName, JFrame frame, boolean debug){
        this.debug = debug;
        this.xPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn","x");
        this.yPos = JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn","y");
        this.sizeX = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn", "sizeX"));
        this.sizeY = Integer.parseInt(JsonFile.readWithThreeKeys("maps/" + fileName, "object", "spawn", "sizeY"));
        this.frame = frame;
        initSpawn();
    }
    private void initSpawn(){
        setBackground(Color.green);
        if(containsNumbers(xPos) && containsNumbers(yPos)){
            setBounds(Integer.parseInt(xPos), Integer.parseInt(yPos), sizeX, sizeY);
            if(debug){
                printColor("cyan", "Spawn contained no letters");
            }
        }else{
            int newXPos = switch(xPos){
                case"l" -> 0;
                case"r" -> frame.getWidth();
                default -> Integer.parseInt(xPos);
            };
            int newYPos = switch(yPos){
                case"t" -> 0;
                case"b" -> frame.getHeight();
                default -> Integer.parseInt(yPos);
            };
            setBounds(newXPos, newYPos, sizeX, sizeY);
            if(debug){
                printColor("cyan", "Spawn contained letters");
            }
        }
    }
    private boolean containsNumbers(String str){
        return str.matches("[0-9]+");
    }
}
