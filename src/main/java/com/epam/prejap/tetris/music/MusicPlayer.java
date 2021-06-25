package com.epam.prejap.tetris.music;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MusicPlayer {

    public static final String MAIN_THEME_URL = "tetris8bit.wav";

    private Clip clip;
    private AudioInputStream audioStream;
  //  private final URL url;
    public URL mainMusicTheme = MusicPlayer.class.getResource(MAIN_THEME_URL);

    private MusicPlayer(MusicBuilder musicBuilder){
        this.clip = musicBuilder.clip;
        this.audioStream = musicBuilder.audioStream;
      //  this.url = musicBuilder.url;
    }



    public static class MusicBuilder {
            private Clip clip;
            private AudioInputStream audioStream;
         //   private URL url;

            public MusicBuilder clip(Clip clip){
                this.clip = clip;
                return this;
            }

            public MusicBuilder audioStream(AudioInputStream audioInputStream){
                this.audioStream = audioInputStream;
                return this;
            }

            //public MusicBuilder url(final URL url){
              //  this.url = url;
                //return this;
           // }

            public MusicPlayer build(){
                return new MusicPlayer(this);
            }
        }

    /**
     * This method is void and does not take any parameters
     * It is invoked in Tetris class, during game start of the game
     */
    public void playMusic(){
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(mainMusicTheme);
            Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
        System.out.println(e.getMessage());
        }
    }

    /**
     * This method allows to pause the melody
     */
    public void pauseTheMusic(){
        clip.stop();
    }

    /**
     * This method allows to start the melody from the beginning
     */
    public void restartMusicTheme(){
        clip.setMicrosecondPosition(0);
    }


}
