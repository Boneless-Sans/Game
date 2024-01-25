package com.boneless.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends JPanel {
    //ints and doubles
    private int health;
    private int slipTime = 100;
    private final int ogSlipTime;
    private double moveSpeed = 10;
    private final double ogMoveSpeed;
    //booleans
    private boolean isMoving;
    private final boolean debug;
    //Strings
    // other elements
    private final JLabel playerIcon;
    private Timer timer;
    private final JFrame frame;
    public Player(JFrame frame){
        this.debug = Game.getDebugState();
        this.frame = frame;
        ogSlipTime = slipTime;
        ogMoveSpeed = moveSpeed;
        setBounds(500,500,50,50);

        playerIcon = new JLabel();
        playerIcon.setIcon(new ImageIcon("src/main/resources/assets/textures/player.png"));
        //setBackground(Color.black);
        add(playerIcon);

        if(getX() <= 0 || getY() <= 0 || getX() >= frame.getWidth() || getY() >= frame.getHeight()){
            //killPlayer();
        }
    }
    public void killPlayer(){
        if(debug){
            System.out.println("Killed Player");
        }
        remove(playerIcon);
    }
    public void move(String direction){
        int newX = getX();
        int newY = getY();

        switch(direction) {
            case "up":
                newY = Math.max(0, (int) (getY() - moveSpeed));
                break;
            case "down":
                newY = Math.min(frame.getHeight() - getHeight(), (int) (getY() + moveSpeed));
                break;
            case "left":
                newX = Math.max(0, (int) (getX() - moveSpeed));
                break;
            case "right":
                newX = Math.min(frame.getWidth() - getWidth(), (int) (getX() + moveSpeed));
                break;
        }

        // Check if the new position is within bounds
        if (newX >= 0 && newX <= frame.getWidth() - getWidth() && newY >= 0 && newY <= frame.getHeight() - getHeight()) {
            setLocation(newX, newY);
        }

        slipTime = ogSlipTime;
    }

    public void stopMoving(String direction){
        int slipSpeed = 10; // Adjust the slip speed as needed
        double slipIncrement = 0.1; // Adjust the slip increment as needed
        timer = new Timer(slipSpeed, e ->{
            switch(direction) {
                case "up" -> setLocation(getX(), (int) (getY() - moveSpeed));
                case "down" -> setLocation(getX(), (int) (getY() + moveSpeed));
                case "left" -> setLocation((int) (getX() - moveSpeed), getY());
                case "right" -> setLocation((int) (getX() + moveSpeed), getY());
            }
            slipTime--;
            moveSpeed -= slipIncrement;
            if(slipTime < 0){
                timer.stop();
                moveSpeed = ogMoveSpeed;
                slipTime = ogSlipTime;
            }
            if (moveSpeed <= 0) {
                timer.stop();
                moveSpeed = ogMoveSpeed;
                slipTime = ogSlipTime;
            }
        });
        timer.start();
    }

    //Vars
    public void setHealth(int health){
        this.health = health;
    }
    public void setMoveSpeed(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }
    public void setMoving(boolean isMoving){
        this.isMoving = isMoving;
    }
    public int getHealth(){
        return health;
    }
    public double getMoveSpeed(){
        return moveSpeed;
    }
    public boolean getIsMoving(){
        return isMoving;
    }
}
