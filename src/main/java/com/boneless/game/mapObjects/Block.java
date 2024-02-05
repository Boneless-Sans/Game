package com.boneless.game.mapObjects;

import com.boneless.game.Player;

import javax.swing.*;
import java.awt.*;

public class Block extends MapObject{

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
        player.setIsAlive(false);
        player.setBounds(-100,-100,0,0);
    }
}
