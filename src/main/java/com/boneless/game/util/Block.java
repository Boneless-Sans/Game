package com.boneless.game.util;

import com.boneless.game.Player;

import javax.swing.*;
import java.awt.*;

public class Block extends MapObject{
    private final int blockNum;
    public Block(String fileName, JFrame frame, int blockNum, boolean debug) {
        super(fileName, frame, debug);
        this.blockNum = blockNum;
        initMapObject();
    }
    @Override
    protected String getObjectName(){
        System.out.println("blockNum: " + blockNum);
        return "block" + blockNum;
    }
    @Override
    protected Color getBackgroundColor() {
        return Color.yellow;
    }
    @Override
    protected void initMapObject(){
        System.out.println("something");
    }
    public void playerCollided(Player player) {
        player.setIsAlive(false);
        player.setBounds(-100,-100,0,0);
    }
}
