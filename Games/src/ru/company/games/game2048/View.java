package ru.company.games.game2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View extends JPanel {
    private static final Color BG_COLOR = new Color(12299680);
    private final Controller controller;
    boolean isGameWon = false;
    boolean isGameLost = false;

    public View(Controller controller) {
        this.setFocusable(true);
        this.controller = controller;
        this.addKeyListener(controller);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);

        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                this.drawTile(g, this.controller.getGameTiles()[y][x], x, y);
            }
        }

        g.drawString("Score: " + this.controller.getScore(), 140, 465);
        if (this.isGameWon) {
            JOptionPane.showMessageDialog(this, "You've won!");
        } else if (this.isGameLost) {
            JOptionPane.showMessageDialog(this, "You've lost :(");
        }

    }

    private void drawTile(Graphics g2, Tile tile, int x, int y) {
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int value = tile.value;
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(tile.getTileColor());
        g.fillRoundRect(xOffset, yOffset, 96, 96, 8, 8);
        g.setColor(tile.getFontColor());
        int size = value < 100 ? 36 : (value < 1000 ? 32 : 24);
        Font font = new Font("Arial", 1, size);
        g.setFont(font);
        String s = String.valueOf(value);
        FontMetrics fm = this.getFontMetrics(font);
        int w = fm.stringWidth(s);
        int h = -((int) fm.getLineMetrics(s, g).getBaselineOffsets()[2]);
        if (value != 0) {
            g.drawString(s, xOffset + (96 - w) / 2, yOffset + 96 - (96 - h) / 2 - 2);
        }
    }

    private static int offsetCoors(int arg) {
        return arg * 108 + 12;
    }
}
