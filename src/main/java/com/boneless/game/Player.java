package com.boneless.game;

import com.boneless.game.util.IconResize;
import com.boneless.game.util.JsonFile;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static com.boneless.game.util.Print.printError;

public class Player extends JPanel {
    //booleans
    private final boolean debug;
    // other elements
    private final JLabel playerIcon;
    private final JFrame frame;
    //player values
    private int health;
    private double velocityX = 0.0;
    private double velocityY = 0.0;
    public Player(JFrame frame){
        int playerWidth = 50;
        int playerHeight = 50;
        this.debug = Game.getDebugState();
        this.frame = frame;
        setBounds((frame.getWidth() /2) - playerWidth, (frame.getHeight() /2) - playerHeight, playerWidth, playerHeight);

        playerIcon = new JLabel();
        playerIcon.setIcon(new IconResize("textures/player.png", playerWidth, playerHeight).getImage());
        setBackground(Color.black);
        add(playerIcon);
    }
    private final double acceleration = 1; // Adjust acceleration rate as needed
    private final double friction = 0.01; // Adjust friction coefficient as needed
    private final double maxSpeed = 10.0; // Adjust maximum speed as needed

    public void move(Set<String> directions) {
        double accelerationX = 0.0;
        double accelerationY = 0.0;

        // Determine acceleration based on pressed directions
        for (String direction : directions) {
            switch (direction) {
                case "up":
                    accelerationY -= acceleration;
                    break;
                case "down":
                    accelerationY += acceleration;
                    break;
                case "left":
                    accelerationX -= acceleration;
                    break;
                case "right":
                    accelerationX += acceleration;
                    break;
            }
        }

        // Update velocity based on acceleration
        velocityX += accelerationX;
        velocityY += accelerationY;

        // Apply friction to gradually decrease velocity
        if (!directions.contains("up") && !directions.contains("down")) {
            velocityY *= (1 - friction);
        }
        if (!directions.contains("left") && !directions.contains("right")) {
            velocityX *= (1 - friction);
        }

        // Limit velocity to maximum speed
        velocityX = Math.min(Math.max(velocityX, -maxSpeed), maxSpeed);
        velocityY = Math.min(Math.max(velocityY, -maxSpeed), maxSpeed);

        // Update player's position based on velocity
        int newX = (int) (getX() + velocityX);
        int newY = (int) (getY() + velocityY);

        // Set player's new position
        if (isValidMove(newX, newY)) {
            setLocation(newX, newY);
        }

        // Debug outputs
        System.out.println("VelocityX: " + velocityX + ", VelocityY: " + velocityY + ", Directions: " + directions);
    }
    public void update() {
        // Apply friction to gradually decrease velocity
        velocityX *= (1 - friction);
        velocityY *= (1 - friction);

        // Update player's position based on velocity
        int newX = (int) (getX() + velocityX);
        int newY = (int) (getY() + velocityY);

        // Set player's new position
        if(isValidMove(newX, newY)) {
            setLocation(newX, newY);
        }else if(!isValidMoveX(newX)){
            velocityX = 0;
        }else {
            velocityY = 0;
        }
    }

    private boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX <= frame.getWidth() - getWidth() - 10 && newY >= 0 && newY <= frame.getHeight() - getHeight() - 35;
    }
    private boolean isValidMoveX(int newX) {
        return newX >= 0 && newX <= frame.getWidth() - getWidth() - 10;
    }
}
