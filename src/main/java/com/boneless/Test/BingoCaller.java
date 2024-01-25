package com.boneless.Test;

import java.util.Random;

public class BingoCaller{
    private static final String[] COLUMN_LETTERS = {"B","I","N","G","O"};
    private static boolean[] numbersCalled = new boolean[75];

    public static boolean hasBeenCalled(int num){
        return numbersCalled[num];
    }
    public static int getRandomNumber(String letter){
        Random rand = new Random();
        int randomNumber = 0;
        if (letter.equalsIgnoreCase("b")) {
            randomNumber = rand.nextInt(15) + 1; // Range: 1-15
        } else if (letter.equalsIgnoreCase("i")) {
            randomNumber = rand.nextInt(15) + 16; // Range: 16-30
        } else if (letter.equalsIgnoreCase("n")) {
            randomNumber = rand.nextInt(15) + 31; // Range: 31-45
        } else if (letter.equalsIgnoreCase("g")) {
            randomNumber = rand.nextInt(15) + 46; // Range: 46-60
        } else if (letter.equalsIgnoreCase("o")) {
            randomNumber = rand.nextInt(15) + 61; // Range: 61-75
        }
        return randomNumber;
    }
    public static String makeCall(){
        Random rand = new Random();
        String randLetter = COLUMN_LETTERS[rand.nextInt(COLUMN_LETTERS.length)];
        int randNumber = 0;

        // Generate a random number until we find one that hasn't been called
        do {
            randNumber = getRandomNumber(randLetter);
        } while (hasBeenCalled(randNumber));

        // Mark the number as called
        numbersCalled[randNumber - 1] = true;

        return randLetter + randNumber;
    }
}
