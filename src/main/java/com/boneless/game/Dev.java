package com.boneless.game;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Dev extends JFrame{
    private int[][] boardData = {
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,1,0,0},
            {0,0,0,0,0},
            {0,0,0,0,1}
    };
    private JPanel[][] gameBoard = new JPanel[5][5];
    public Dev(){
        initUI();
    }
    private void initUI(){
        setSize(500,500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("I really need a name");
        setIconImage(new ImageIcon("src/main/resources/assets/textures/player.png").getImage());

        JPanel masterBoard = new JPanel(new GridLayout(5,5,1,1));
        masterBoard.setBackground(Color.gray);

        for(int i = 0; i < boardData.length; i++){
            for(int j = 0; j < boardData[i].length; j++){
                gameBoard[i][j] = new JPanel(); // Initialize the JPanel
                masterBoard.add(gameBoard[i][j]);
                if(boardData[i][j] == 1){
                    gameBoard[i][j].setBackground(Color.black);
                }
            }
        }
        add(masterBoard);

        gameLoop();

        setVisible(true);
    }
    private void gameLoop(){
        Thread checkLoop = new Thread(() -> {
            while(true) {
                updateBoard();
                try {
                    Thread.sleep(500); //~60 fps
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checkLoop.start();
    }
    private void updateBoard(){
        for(int i = 0; i < boardData.length; i++){
            for(int j = 0; j < boardData[i].length; j++){
                gameBoard[i][j].setBackground(Color.white);
                if(boardData[i][j] == 1){
                    gameBoard[i][j].setBackground(Color.black);
                }
            }
        }
    }
    public static void main(String[] args){
        new Dev();
    }
}
