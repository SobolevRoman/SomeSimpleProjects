package ru.company.games.game2048;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private final int numberOfEmptyTiles;
    private final int score;
    private final Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return this.move;
    }

    public int compareTo(MoveEfficiency moveEfficiency) {
        if (this.numberOfEmptyTiles <= moveEfficiency.numberOfEmptyTiles && (this.numberOfEmptyTiles != moveEfficiency.numberOfEmptyTiles || this.score <= moveEfficiency.score)) {
            return this.numberOfEmptyTiles == moveEfficiency.numberOfEmptyTiles && this.score == moveEfficiency.score ? 0 : -1;
        } else {
            return 1;
        }
    }
}
