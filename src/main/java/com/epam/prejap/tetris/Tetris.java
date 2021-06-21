package com.epam.prejap.tetris;

import com.epam.prejap.tetris.block.BlockFeed;
import com.epam.prejap.tetris.game.Move;
import com.epam.prejap.tetris.game.Playfield;
import com.epam.prejap.tetris.game.Printer;
import com.epam.prejap.tetris.game.Waiter;
import com.epam.prejap.tetris.music.MusicPlayer;
import com.epam.prejap.tetris.player.Player;
import com.epam.prejap.tetris.player.RandomPlayer;

class Tetris {

    private final Playfield playfield;
    private final Waiter waiter;
    private final Player player;
    private final MusicPlayer musicPlayer;

    public Tetris(Playfield playfield, Waiter waiter, Player player, MusicPlayer musicPlayer) {
        this.playfield = playfield;
        this.waiter = waiter;
        this.player = player;
        this.musicPlayer = musicPlayer;
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

        var feed = new BlockFeed();
        var printer = new Printer(System.out);
        var playfield = new Playfield(rows, cols, feed, printer);
        var game = new Tetris(playfield, new Waiter(delay), new RandomPlayer(), new MusicPlayer());

        var score = game.play();

        System.out.println("Score: " + score.points());
    }

}
