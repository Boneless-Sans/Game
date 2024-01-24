package com.boneless.game.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    public static void playSound(String fileName)throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("src/main/resources/assets/sounds/" + fileName);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();
    }
    public static void stopSound(Clip clip){
        //stop sound somehow
        clip.stop();
    }
}
