package com.boneless.game.util;

public class Print{
    private static final String[] debugColors = {"\u001B[37m", "\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[30m"};
    //White - 0, Red - 1, Green - 2, Yellow - 3, Blue - 4, Purple - 5, Cyan - 6,Black - 7
    public static void print(String text){
        System.out.println(text);
    }
    public static void printError(String text){
        System.out.println(debugColors[1] + text + debugColors[0]);
    }
    public static void printDebug(String text){
        System.out.println(parseColor("green") + text + "\u001b[37m");
    }
    public static void printColor(String textColor, String text){
        System.out.println(parseColor(textColor) + text + "\u001b[37m");
    }
    private static String parseColor(String textColor){
        return switch(textColor.toLowerCase()){
            case"black" -> "\u001B[30m";
            case"red" -> "\u001B[31m";
            case"green" -> "\u001B[32m";
            case"yellow" -> "\u001B[33m";
            case"blue" -> "\u001B[34m";
            case"purple" -> "\u001B[35m";
            case"cyan" -> "\u001B[36m";
            default -> "\u001B[37m";
        };
    }
}
