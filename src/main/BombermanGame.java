package main;

import entities.*;
import graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BombermanGame extends JPanel {
    public static final int WIDTH = Sprite.SIZE * 31;
    private static final int HEIGHT = Sprite.SIZE * 13 ;
    public static final int FRAME_WIDTH = WIDTH + 13;
    public static final int FRAME_HEIGHT = HEIGHT + 120;
    public Entity[][] staticEntities = new Entity[13][31];
    public Bomber bomber = new Bomber(Sprite.SIZE, Sprite.SIZE);
    BufferedImage scene = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public String mapPath;
    char[][] currentMap = new char[13][31];

    public BombermanGame(String mapPath) {
        this.mapPath = mapPath;
    }

    void loadMap(String path) {
        char[][] originMap = new Map(path).originMap;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                switch (originMap[i][j]) {
                    case Map.WALL: {
                        staticEntities[i][j] = new Wall(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.wall);
                        break;
                    }
                    case Map.BRICK: {
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        break;
                    }
                    case Map.BOMB_ITEM: {
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        break;
                    }
                    case Map.FLAME_ITEM: {
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        break;
                    }
                    case Map.SPEED_ITEM: {
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        break;
                    }
                    default:
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                }
            }
        }

    }

    public void draw() {
        Graphics g = scene.getGraphics();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                staticEntities[i][j].draw(g);
            }
        }

        bomber.draw(g);
    }

    public void addListener(BombermanGame game) {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bomber.moveRight(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bomber.moveLeft(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bomber.moveUp(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bomber.moveDown(game);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bomber.moveRight(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bomber.moveLeft(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bomber.moveUp(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bomber.moveDown(game);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bomber.moveRight(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bomber.moveLeft(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bomber.moveUp(game);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bomber.moveDown(game);
                }
            }
        });
    }


    public void start() {
        loadMap(mapPath);
        draw();
        while (true) {
            draw();
            this.getGraphics().drawImage(scene, 0, 0, null);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("bomberman");
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        BombermanGame myGame = new BombermanGame("src\\map-config\\map1.txt");
        myGame.setFocusable(true);
        myGame.requestFocusInWindow();
        myGame.addListener(myGame);
        myGame.setLayout(null);

        mainFrame.add(myGame);
        mainFrame.setVisible(true);
        myGame.start();
    }

}
