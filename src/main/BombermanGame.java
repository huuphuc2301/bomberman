package main;

import entities.bomber.Bomber;
import graphics.Sprite;
import sounds.Sound;

import javax.sound.sampled.Clip;
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
        Clip menuSound = Sound.getClip("sounds/jinx.wav");
        menuSound.loop(-1);
        menuSound.start();
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
            menuSound.stop();
            loadSinglePlay();
            return;
        }
        if (menu.draw() == 1) {
            menu.setFocusable(false);
            frame.remove(menu);
            menuSound.stop();
            loadAutoPlay();
        }

    }

    public void loadSinglePlay() {
        int indexOfStage = 0;
        long createTime = System.currentTimeMillis();
        int timeScoreValue = 0;
        GameStage gameStage = new GameStage(indexOfStage, createTime, timeScoreValue);
        Clip themeSound = Sound.getClip("sounds/queue.wav");
        themeSound.loop(-1);
        themeSound.start();
        while (indexOfStage < Map.MAP_PATHS.length) {
            if (indexOfStage > 0) frame.remove(gameStage);
            gameStage = new GameStage(indexOfStage, gameStage.bomber, gameStage.createTime, gameStage.timeScoreValue);
            gameStage.addListener(gameStage);
            gameStage.setLayout(null);
            frame.add(gameStage);
            frame.setVisible(true);
            gameStage.setFocusable(true);
            gameStage.requestFocusInWindow();

            if (gameStage.start()) indexOfStage++;
            else break;
        }
        themeSound.stop();
        gameStage.setFocusable(false);
        frame.remove(gameStage);
        loadMenu();

    }

    public void loadAutoPlay() {
        int indexOfStage = 0;
        long createTime = System.currentTimeMillis();
        int timeScoreValue = 0;
        GameStage gameStage = new AutoPlay(indexOfStage,new Bomber(Sprite.SIZE,Sprite.SIZE), createTime, timeScoreValue);

        Clip themeSound = Sound.getClip("sounds/queue.wav");
        themeSound.loop(-1);
        themeSound.start();
        while (indexOfStage < Map.MAP_PATHS.length) {
            if (indexOfStage > 0) frame.remove(gameStage);
            gameStage = new AutoPlay(indexOfStage, gameStage.bomber, gameStage.createTime, gameStage.timeScoreValue);
            gameStage.setLayout(null);
            frame.add(gameStage);
            frame.setVisible(true);
            gameStage.setFocusable(true);
            gameStage.requestFocusInWindow();
            if (gameStage.start()) indexOfStage++;
            else break;
        }
        themeSound.stop();
        gameStage.setFocusable(false);
        frame.remove(gameStage);
        loadMenu();

    }
}
