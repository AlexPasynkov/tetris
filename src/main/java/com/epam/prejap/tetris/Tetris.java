package com.epam.prejap.tetris;

import com.epam.prejap.tetris.block.BlockFeed;
import com.epam.prejap.tetris.game.Move;
import com.epam.prejap.tetris.game.Playfield;
import com.epam.prejap.tetris.game.Printer;
import com.epam.prejap.tetris.game.Waiter;
import com.epam.prejap.tetris.music.MusicPlayer;
import com.epam.prejap.tetris.player.Player;
import com.epam.prejap.tetris.player.RandomPlayer;

import javax.sound.sampled.*;

import java.net.URL;
import java.util.Scanner;

import static com.epam.prejap.tetris.music.MusicPlayer.MAIN_THEME_URL;

class Tetris extends Thread {

    private final Playfield playfield;
    private final Waiter waiter;
    private final Player player;
    private final MusicPlayer musicPlayer;
    private boolean isGameGoingOn;

    private Tetris(TetrisBuilder tetrisBuilder) {
        this.playfield = tetrisBuilder.playfield;
        this.waiter = tetrisBuilder.waiter;
        this.player = tetrisBuilder.player;
        this.musicPlayer = tetrisBuilder.musicPlayer;
    }

        public static class TetrisBuilder{
            private Playfield playfield;
            private Waiter waiter;
            private Player player;
            private MusicPlayer musicPlayer;

                public TetrisBuilder playfield(Playfield playfield){
                    this.playfield = playfield;
                    return this;
                }

                public TetrisBuilder waiter(Waiter waiter){
                    this.waiter = waiter;
                    return this;
                }

                public TetrisBuilder player(Player player){
                    this.player = player;
                    return this;
                }

                public TetrisBuilder musicPlayer(MusicPlayer musicPlayer){
                    this.musicPlayer = musicPlayer;
                    return this;
                }

                public Tetris build(){
                    return new Tetris(this);
                }
        }

    public Score play() {
        boolean moved;
        musicPlayer.playMusic();
        int score = 0;
        do {
            moved = false;

            playfield.nextBlock();
            score++;

            boolean nextMove;
            do {
                waiter.waitForIt();
                Move move = player.nextMove().orElse(Move.NONE);
                moved |= (nextMove = playfield.move(move));
            } while (nextMove);

        } while (moved);

        return new Score(score);
    }

    public static void main(String[] args) {

        int rows = 10;
        int cols = 20;
        int delay = 500;
        Clip clip = null;
        AudioInputStream audioInputStream = null;

        var feed = new BlockFeed();
        var printer = new Printer(System.out);
        var playfield = new Playfield(rows, cols, feed, printer);

        MusicPlayer musicPlayer = new MusicPlayer.
                MusicBuilder().
                clip(clip).
                audioStream(audioInputStream).build();

        var game = new TetrisBuilder().
                        playfield(playfield).
                waiter(new Waiter(delay)).
                player(new RandomPlayer()).
                musicPlayer(musicPlayer).
                build();
        UserControls userControls = new UserControls();
        Thread t1 = new Thread(userControls);
        t1.start();
        var score = game.play();


        System.out.println("Score: " + score.points());
    }

    static class UserControls extends Thread{

        private boolean isGameGoingOn;

        public void terminate(){
            this.isGameGoingOn = false;
        }
        Scanner userInput = new Scanner(System.in);

        @Override
        public void run() {
            while (isGameGoingOn){
                System.out.println("I am here");
                String userCommand = userInput.nextLine();
                switch (userCommand){
                    case("m") -> new MusicPlayer.MusicBuilder().build().pauseTheMusic();
                    case("r") -> new MusicPlayer.MusicBuilder().build().restartMusicTheme();
                }
            }
        }
    }

}