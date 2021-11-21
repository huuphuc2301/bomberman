package main;

import graphics.Sprite;

import javax.swing.*;

public class BombermanGame {
    public static final int WIDTH = Sprite.SIZE * 31 + 13;
    public static final int HEIGHT = Sprite.SIZE * 13 + 108;
    private JFrame frame;

    public BombermanGame() {
        frame = new JFrame("bomberman");
        frame.setSize(BombermanGame.WIDTH, BombermanGame.HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

    }

    public static void main(String[] args) {
        BombermanGame myGame = new BombermanGame();
        myGame.loadMenu();
    }

    public void loadMenu() {
        Menu menu = new Menu();
        menu.addListener(this);
        menu.requestFocusInWindow();
        menu.setLayout(null);
        frame.add(menu);
        frame.setVisible(true);
        menu.setFocusable(true);
        menu.requestFocusInWindow();

        if (menu.draw() == 0) {
            menu.setFocusable(false);
            frame.remove(menu);
            loadSinglePlay();
        }

    }

    public void loadSinglePlay() {
        int indexOfStage = 0;
        GameStage gameStage = new GameStage(indexOfStage);
        while (indexOfStage < Map.MAP_PATHS.length) {
            if (indexOfStage > 0) frame.remove(gameStage);
            gameStage = new GameStage(indexOfStage);
            gameStage.addListener(gameStage);
            gameStage.setLayout(null);
            frame.add(gameStage);
            frame.setVisible(true);
            gameStage.setFocusable(true);
            gameStage.requestFocusInWindow();
            if (gameStage.start()) indexOfStage++;
            else break;
        }
        gameStage.setFocusable(false);
        frame.remove(gameStage);
        loadMenu();

    }
}
