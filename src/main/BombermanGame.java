package main;

import entities.*;
import graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BombermanGame extends JPanel {
    public static final int WIDTH = Sprite.SIZE * 31;
    public static final int HEIGHT = Sprite.SIZE * 13 ;
    public static final int FRAME_WIDTH = WIDTH + 13;
    public static final int FRAME_HEIGHT = HEIGHT + 120;
    public static int numColumns=31;
    public static int numRows=13;
    public Entity[][] staticEntities = new Entity[13][31];
    public Bomber bomber = new Bomber(Sprite.SIZE, Sprite.SIZE);
    public ArrayList<Enemy> enemies=new ArrayList<Enemy>();
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
                    case Map.BALLOOM: {
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                        Balloom balloom=new Balloom(j * Sprite.SIZE, i * Sprite.SIZE);
                        enemies.add(balloom);
                        break;
                    }
                    case Map.ONEAL: {
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                        Oneal oneal=new Oneal(j * Sprite.SIZE, i * Sprite.SIZE);
                        enemies.add(oneal);
                        break;
                    }
                    default:
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                }
            }
        }

    }

    public void drawGame() {
        Graphics g = scene.getGraphics();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                staticEntities[i][j].draw(g);
            }
        }

        bomber.move(this);
        bomber.draw(g);

        for (int i=0;i<enemies.size();i++) {
            enemies.get(i).move(this);
            enemies.get(i).draw(g);
        }
    }

    public void addListener(BombermanGame game) {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bomber.right = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bomber.left = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bomber.up = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bomber.down = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    bomber.right = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    bomber.left = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bomber.up = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    bomber.down = false;
                }
            }
        });
    }
    public void setTargetEnemies() {
        for (int i=0;i<enemies.size();i++)
            enemies.get(i).setTarget(this);
    }
    public void start() {
        loadMap(mapPath);
        setTargetEnemies();
        drawGame();
        while (true) {
            drawGame();
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

        BombermanGame myGame = new BombermanGame("map-config\\map1.txt");
        myGame.setFocusable(true);
        myGame.requestFocusInWindow();
        myGame.addListener(myGame);
        myGame.setLayout(null);

        mainFrame.add(myGame);
        mainFrame.setVisible(true);
        myGame.start();
    }

}
