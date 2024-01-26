package com.boneless.game.util;

public class Print{
    public static void print(String text){
        System.out.println(text);
    }
    public static void printError(String text){
        System.out.println("\u001B[31m" + text +  "\u001b[37m");
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
