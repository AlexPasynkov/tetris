package com.epam.prejap.tetris.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    File file;
    /**
     * This method is void and does not take any parameters
     * It is invoked in Tetris class, during game start of the game
     */
    public void playMusic(){
        file = new File("src/main/resources/tetris8bit.wav");
        AudioInputStream audioStream;
        if(fileExists(file)){
            try {
                audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error, please contact support for help");
        }
    }

    public boolean fileExists(File file){
        return file.exists();
    }
}