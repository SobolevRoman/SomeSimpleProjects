package ru.company.games.game2048;

import javax.swing.*;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        JFrame myGame = new JFrame();
        myGame.setTitle("2048");
        myGame.setDefaultCloseOperation(3);
        myGame.setSize(450, 500);
        myGame.setResizable(false);
        myGame.add(controller.getView());
        myGame.setLocationRelativeTo(null);
        myGame.setVisible(true);
    }
}
