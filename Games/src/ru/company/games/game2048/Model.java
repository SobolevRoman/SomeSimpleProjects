package ru.company.games.game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Model {
    private Tile[][] gameTiles;
    private final Stack previousStates = new Stack();
    private final Stack previousScores = new Stack();
    private boolean isSaveNeeded = true;
    public int score;
    public int maxTile;

    public Model() {
        this.resetGameTiles();
        this.score = 0;
        this.maxTile = 2;
    }

    public Tile[][] getGameTiles() {
        return this.gameTiles;
    }

    private void addTile() {
        if (this.getEmptyTiles().size() > 0) {
            List<Tile> list = this.getEmptyTiles();
            Tile tile = list.get((int) (Math.random() * (double) list.size()));
            tile.value = Math.random() < 0.9D ? 2 : 4;
        }

    }

    private List<Tile> getEmptyTiles() {
        List<Tile> list = new ArrayList<>();

        for (Tile[] gameTile : this.gameTiles) {
            for (int j = 0; j < this.gameTiles.length; ++j) {
                if (gameTile[j].isEmpty()) {
                    list.add(gameTile[j]);
                }
            }
        }
        return list;
    }

    public void resetGameTiles() {
        this.gameTiles = new Tile[4][4];

        for (int i = 0; i < this.gameTiles.length; ++i) {
            for (int j = 0; j < this.gameTiles.length; ++j) {
                this.gameTiles[i][j] = new Tile();
            }
        }

        this.addTile();
        this.addTile();
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean flag = false;
        for (int j = 0; j < tiles.length; ++j) {
            for (int i = 0; i < tiles.length - 1; ++i) {
                if (tiles[i].isEmpty() && !tiles[i + 1].isEmpty()) {
                    Tile tmpTile = tiles[i];
                    tiles[i] = tiles[i + 1];
                    tiles[i + 1] = tmpTile;
                    flag = true;
                }
            }
        }
        return flag;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean flag = false;
        for (int i = 0; i < tiles.length - 1; ++i) {
            if (tiles[i].value == tiles[i + 1].value) {
                if (tiles[i].value != 0 & tiles[i + 1].value != 0) {
                    flag = true;
                }

                int locCount = tiles[i].value + tiles[i + 1].value;
                tiles[i] = new Tile(locCount);

                if (tiles.length - 1 - (i + 1) >= 0)
                    System.arraycopy(tiles, i + 1 + 1, tiles, i + 1, tiles.length - 1 - (i + 1));

                tiles[tiles.length - 1] = new Tile();
                this.score += locCount;
                this.maxTile = Math.max(this.maxTile, locCount);
            }
        }
        return flag;
    }

    public void left() {
        if (this.isSaveNeeded) {
            this.saveState(this.gameTiles);
        }

        boolean localFlag = false;
        for (int i = 0; i < 4; ++i) {
            if (this.compressTiles(this.gameTiles[i]) || this.mergeTiles(this.gameTiles[i])) {
                localFlag = true;
            }
        }

        if (localFlag) {
            this.addTile();
        }
        this.isSaveNeeded = true;
    }

    public void up() {
        this.saveState(this.gameTiles);
        this.turn90Right();
        this.turn90Right();
        this.turn90Right();
        this.left();
        this.turn90Right();
    }

    public void down() {
        this.saveState(this.gameTiles);
        this.turn90Right();
        this.left();
        this.turn90Right();
        this.turn90Right();
        this.turn90Right();
    }

    public void right() {
        this.saveState(this.gameTiles);
        this.turn90Right();
        this.turn90Right();
        this.left();
        this.turn90Right();
        this.turn90Right();
    }

    private void turn90Right() {
        Tile[][] turn90Mass = new Tile[4][4];

        for (int j = 0; j < 4; ++j) {
            for (int i = 3; i >= 0; --i) {
                turn90Mass[j][3 - i] = this.gameTiles[i][j];
            }
        }
        this.gameTiles = Arrays.copyOf(turn90Mass, 4);
    }

    public boolean canMove() {
        if (this.getEmptyTiles().size() > 0) {
            return true;
        } else {
            for (int i = 0; i < this.gameTiles.length - 1; ++i) {
                for (int j = 0; j < this.gameTiles.length - 1; ++j) {
                    if (this.gameTiles[i][j].value == this.gameTiles[i][j + 1].value && this.gameTiles[i][j].value != 0) {
                        return true;
                    }
                    if (this.gameTiles[i][j].value == this.gameTiles[i + 1][j].value && this.gameTiles[i][j].value != 0) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private void saveState(Tile[][] tiles) {
        Tile[][] saveMass = new Tile[4][4];

        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles.length; ++j) {
                saveMass[i][j] = new Tile(tiles[i][j].value);
            }
        }

        this.previousStates.push(saveMass);
        this.previousScores.push(this.score);
        this.isSaveNeeded = false;
    }

    public void rollback() {
        if (!this.previousStates.empty() && !this.previousScores.empty()) {
            this.gameTiles = (Tile[][]) ((Tile[][]) this.previousStates.pop());
            this.score = (Integer) this.previousScores.pop();
        }

    }

    public void randomMove() {
        int n = (int) (Math.random() * 100.0D) % 4;
        switch (n) {
            case 0 -> this.up();
            case 1 -> this.down();
            case 2 -> this.right();
            case 3 -> this.left();
        }
    }

    public boolean hasBoardChanged() {
        Tile[][] mass = (Tile[][]) this.previousStates.peek();

        for (int i = 0; i < mass.length; ++i) {
            for (int j = 0; j < mass.length; ++j) {
                if (mass[i][j].value != this.gameTiles[i][j].value) {
                    return true;
                }
            }
        }
        return false;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency;
        if (!this.hasBoardChanged()) {
            moveEfficiency = new MoveEfficiency(-1, 0, move);
        } else {
            moveEfficiency = new MoveEfficiency(this.getEmptyTiles().size(), this.score, move);
        }

        this.rollback();
        return moveEfficiency;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue(4, Collections.reverseOrder());
        priorityQueue.offer(this.getMoveEfficiency(this::left));
        priorityQueue.offer(this.getMoveEfficiency(this::right));
        priorityQueue.offer(this.getMoveEfficiency(this::up));
        priorityQueue.offer(this.getMoveEfficiency(this::down));
        assert priorityQueue.peek() != null;
        priorityQueue.peek().getMove().move();
    }
}

