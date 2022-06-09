package ru.company.games.game2048;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private final Model model;
    private final View view;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    public Tile[][] getGameTiles() {
        return this.model.getGameTiles();
    }

    public int getScore() {
        return this.model.score;
    }

    public View getView() {
        return this.view;
    }

    public void resetGame() {
        this.model.score = 0;
        this.view.isGameLost = false;
        this.view.isGameWon = false;
        this.model.resetGameTiles();
    }

    public void keyPressed(KeyEvent e) {
        if (27 == e.getKeyCode()) {
            this.resetGame();
        }

        if (!this.model.canMove()) {
            this.view.isGameLost = true;
        }

        if (!this.view.isGameLost && !this.view.isGameWon) {
            if (37 == e.getKeyCode()) {
                this.model.left();
            } else if (39 == e.getKeyCode()) {
                this.model.right();
            } else if (38 == e.getKeyCode()) {
                this.model.up();
            } else if (40 == e.getKeyCode()) {
                this.model.down();
            } else if (90 == e.getKeyCode()) {
                this.model.rollback();
            } else if (82 == e.getKeyCode()) {
                this.model.randomMove();
            } else if (65 == e.getKeyCode()) {
                this.model.autoMove();
            }
        }

        if (this.model.maxTile == 2048) {
            this.view.isGameWon = true;
        }

        this.view.repaint();
    }
}
