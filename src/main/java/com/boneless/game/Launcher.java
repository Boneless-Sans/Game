package com.boneless.game;

public class Launcher {
    public static void main(String[] args) {
        //run game
        System.out.println("Launching Game!");
        String defaultArg = "run";
        String arg = (args != null && args.length > 0) ? args[0] : defaultArg;
        new MainMenu(arg);
    }
}