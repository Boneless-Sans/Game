package com.boneless.Test;

import java.util.Random;

public class BingoCaller{
    public static void main
    private static final String[] COLUMN_LETTERS = {"B","I","N","G","O"};
    private static boolean[] numbersCalled;

    public static boolean hasBeenCalled(int num){
        return numbersCalled[num];
    }
    public static int getRandomNumber(String letter){
        Random rand = new Random();
        return switch(letter.toLowerCase()){
            case"b" -> rand.nextInt(1) + 15;
            case"i" -> rand.nextInt(16) + 30;
            case"n" -> rand.nextInt(31) + 45;
            case"g" -> rand.nextInt(46) + 60;
            case"o" -> rand.nextInt(61) + 75;
            default -> 0;
        };
    }
    public static String makeCall(){

    }
}
