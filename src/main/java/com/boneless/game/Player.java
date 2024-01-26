package com.boneless.game;

import com.boneless.game.util.IconResize;

import javax.swing.*;
import java.awt.*;

import static com.boneless.game.util.Print.printError;

public class Player extends JPanel {
    //ints and doubles
    private int health;
    //booleans
    private final boolean debug;
    // other elements
    private final JLabel playerIcon;
    private final JFrame frame;
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
    public void killPlayer(){
        if(debug){
            System.out.println("Killed Player");
        }
        remove(playerIcon);
        setOpaque(true);
        setBackground(new Color(0,0,0,0));
    }
    private double velocityX = 0.0;
    private double velocityY = 0.0;
    private final double acceleration = 1; // Adjust acceleration rate as needed
    private final double friction = 0.05; // Adjust friction coefficient as needed
    private final double maxSpeed = 5.0; // Adjust maximum speed as needed

    public void move(String direction) {
        double accelerationX = 0.0;
        double accelerationY = 0.0;

        // Determine acceleration based on direction
        switch(direction) {
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

        // Update velocity based on acceleration
        velocityX += accelerationX;
        velocityY += accelerationY;

        // Apply friction to gradually decrease velocity
        velocityX *= (1 - friction);
        velocityY *= (1 - friction);

        // Limit velocity to maximum speed
        velocityX = Math.min(Math.max(velocityX, -maxSpeed), maxSpeed);
        velocityY = Math.min(Math.max(velocityY, -maxSpeed), maxSpeed);

        // Update player's position based on velocity
        int newX = (int) (getX() + velocityX);
        int newY = (int) (getY() + velocityY);

        // Set player's new position
        setLocation(newX, newY);

        // Debug outputs
        System.out.println("VelocityX: " + velocityX + ", VelocityY: " + velocityY + ", Direction: " + direction);
    }

    public void update() {
        // Apply friction to gradually decrease velocity
        velocityX *= (1 - friction);
        velocityY *= (1 - friction);

        // Update player's position based on velocity
        int newX = (int) (getX() + velocityX);
        int newY = (int) (getY() + velocityY);

        // Set player's new position
        setLocation(newX, newY);
    }

    private boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX <= frame.getWidth() - getWidth() - 10 && newY >= 0 && newY <= frame.getHeight() - getHeight() - 29;
    }

    //Vars
    public void setHealth(int health){
        this.health = health;
    }
    //public void setMoveSpeed(int moveSpeed){
//        this.moveSpeed = moveSpeed;
//    }
//    public void setMoving(boolean isMoving){
//        this.isMoving = isMoving;
//    }
    public int getHealth(){
        return health;
    }
//    public double getMoveSpeed(){
//        return moveSpeed;
//    }
//    public boolean getIsMoving(){
//        return isMoving;
//    }
}
