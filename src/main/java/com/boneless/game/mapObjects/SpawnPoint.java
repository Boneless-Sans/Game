package com.boneless.game.mapObjects;

import com.boneless.game.util.JsonFile;
import com.boneless.game.util.Print;

import javax.swing.*;
import java.awt.*;

import static com.boneless.game.util.Print.printColor;

public class SpawnPoint extends MapObject {
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