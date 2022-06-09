package ru.company.games.game2048;

import java.awt.Color;

public class Tile {
    int value;

    public Tile(int value) {
        this.value = value;
    }

    public Tile() {
    }

    public boolean isEmpty() {
        return this.value == 0;
    }

    public Color getFontColor() {
        return this.value < 16 ? new Color(7827045) : new Color(16381682);
    }

    public Color getTileColor() {
        return switch (this.value) {
            case 0 -> new Color(13484468);
            case 2 -> new Color(15656154);
            case 4 -> new Color(15589576);
            case 8 -> new Color(15905145);
            case 16 -> new Color(16094563);
            case 32 -> new Color(16153695);
            case 64 -> new Color(16145979);
            case 128 -> new Color(15585138);
            case 256 -> new Color(15584353);
            case 512 -> new Color(15583312);
            case 1024 -> new Color(15582527);
            case 2048 -> new Color(15581742);
            default -> new Color(16711680);
        };
    }
}
