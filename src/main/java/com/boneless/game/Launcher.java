package com.boneless.game;

import com.boneless.game.menus.MainMenu;

import java.util.Objects;

import static com.boneless.game.util.Print.print;
import static com.boneless.game.util.Print.printDebug;

public class Launcher {
    public static void main(String[] args) {
        //run game
        print("Launching!");
        String defaultArg = "run";
        String arg = (args != null && args.length > 0) ? args[0] : defaultArg;
        if(!Objects.equals(arg, "dev")){
            printDebug("Debugging Enabled!");
            new MainMenu(arg);
        }else{
            new Game(arg, "testMap.json");
        }
    }
}