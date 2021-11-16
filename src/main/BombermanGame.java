package main;

import entities.*;
import entities.bomber.Bomb;
import entities.bomber.Bomber;
import entities.enemy.Balloom;
import entities.enemy.Enemy;
import entities.enemy.Oneal;
import entities.item.BombItem;
import entities.item.Item;
import graphics.Sprite;
import graphics.TimeScore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BombermanGame extends JPanel {
    public static final int WIDTH = Sprite.SIZE * 31 + 13;
    public static final int HEIGHT = Sprite.SIZE * 13 + 108;
    public static final int numColumns = 31;
    public static final int numRows = 13;
    public Entity[][] staticEntities = new Entity[13][31];
    public Bomber bomber = new Bomber(Sprite.SIZE, Sprite.SIZE);
    BufferedImage scene = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public String mapPath;
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Bomb> bombs = new ArrayList<>();
    public long createTime, pauseTime, unPauseTime;
    private int timeScoreValue = 0;
    public boolean isPaused = false;
    private boolean nextIsPaused = true;

    public BombermanGame(String mapPath) {
        this.mapPath = mapPath;
        createTime = System.currentTimeMillis();
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
                        Item bombItem = new BombItem(j * Sprite.SIZE, i * Sprite.SIZE);
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        ((Brick) staticEntities[i][j]).setItem(bombItem);
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
                        enemies.add(new Balloom(j * Sprite.SIZE, i * Sprite.SIZE));
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                        break;
                    }
                    case Map.ONEAL: {
                        enemies.add(new Oneal(j * Sprite.SIZE, i * Sprite.SIZE));
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                        break;
                    }
                    default:
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                }
            }
        }

    }

    public void runAndDraw() {
        Graphics g = scene.getGraphics();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                if (staticEntities[i][j] instanceof Brick && ((Brick) staticEntities[i][j]).isDestroyed) {
                    if (((Brick) staticEntities[i][j]).getItem() == null) {
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                    }
                    else {
                        staticEntities[i][j] = ((Brick) staticEntities[i][j]).getItem();
                    }
                    continue;
                }

                if (staticEntities[i][j] instanceof Item && ((Item) staticEntities[i][j]).isDestroyed) {
                    staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                    Oneal newOneal = new Oneal(j * Sprite.SIZE, i* Sprite.SIZE);
                    newOneal.setTarget(this);
                    enemies.add(newOneal);
                    continue;
                }
                if (staticEntities[i][j] instanceof Item && ((Item) staticEntities[i][j]).isUsed) {
                    staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                    continue;
                }


                if (staticEntities[i][j] instanceof Brick) ((Brick) staticEntities[i][j]).run();
                if (staticEntities[i][j] instanceof Item) ((Item) staticEntities[i][j]).run();

                if (staticEntities[i][j] instanceof Brick && ((Brick) staticEntities[i][j]).isExploding) {
                    if (((Brick) staticEntities[i][j]).getItem() == null) {
                        new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass).draw(g);
                    }
                    else {
                        ((Brick) staticEntities[i][j]).getItem().draw(g);
                    }
                }
                if (staticEntities[i][j] instanceof Item && ((Item) staticEntities[i][j]).isExploding) {
                    new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass).draw(g);
                }

                if (staticEntities[i][j] instanceof Bomb) {
                    Grass grass = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                    if (!((Bomb) staticEntities[i][j]).isRunning) {
                        staticEntities[i][j] = grass;
                    }
                    grass.draw(g);
                    continue;
                }
                staticEntities[i][j].draw(g);
            }
        }

        bombs.removeIf(bomb -> bomb.isRunning == false);
        for (Bomb bomb : bombs) {
            bomb.run(this);
            bomb.draw(g);
        }

        enemies.removeIf(enemy -> enemy.isDead);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(this);
            enemies.get(i).draw(g);
        }

        bomber.move(this);
        bomber.draw(g);

        TimeScore timeScore = new TimeScore((timeScoreValue + (int) (System.currentTimeMillis() - createTime)) / 1000);
        timeScore.draw(g);
    }

    public void pauseOrUnpause() {
        isPaused = nextIsPaused;
        if (nextIsPaused == true) {
            pauseTime = System.currentTimeMillis();
        }
        else {
            unPauseTime = System.currentTimeMillis();
            timeScoreValue += (int) (pauseTime - createTime);
            createTime = System.currentTimeMillis();
        }
    }

    public void addListener(BombermanGame game) {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (isPaused && e.getKeyCode() != KeyEvent.VK_ESCAPE) return;
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
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (bombs.size() == bomber.getMaxBomb()) return;
                    Point pos = Map.getPosition(bomber.getCenter().x, bomber.getCenter().y);
                    if (!(staticEntities[pos.x][pos.y] instanceof Grass)) return;
                    for (Bomb bomb : bombs) {
                        if (pos.equals(Map.getPosition(bomb.getX(), bomb.getY()))) return;
                    }
                    Bomb newBomb = new Bomb(pos.y * Sprite.SIZE, pos.x * Sprite.SIZE, bomber.getBombSize(), game);
                    bombs.add(newBomb);
                    staticEntities[pos.x][pos.y] = newBomb;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (isPaused != nextIsPaused) pauseOrUnpause();
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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    nextIsPaused = !nextIsPaused;
                }
            }
        });
    }

    public void setTargetEnemies() {
        for (Enemy enemy : enemies) enemy.setTarget(this);
    }

    public void start() {
        loadMap(mapPath);

        setTargetEnemies();

        BufferedImage bottomImage = new BufferedImage(1010, 70, BufferedImage.TYPE_INT_RGB);
        try {
            bottomImage = ImageIO.read(new File("images\\instruction_and_time.png"));
        } catch (IOException e) {
            System.out.println("Error read image " + "bottom image");
        }
        scene.getGraphics().drawImage(bottomImage, 0, 416, null);

        runAndDraw();

        BufferedImage pauseImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
        try {
            pauseImage = ImageIO.read(new File("images\\pause.png"));
        } catch (IOException e) {
            System.out.println("Error read image " + "pause image");
        }

        BufferedImage loseImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
        try {
            loseImage = ImageIO.read(new File("images\\lose.png"));
        } catch (IOException e) {
            System.out.println("Error read image " + "lose image");
        }

        while (true) {
            if (bomber.isDead) break;
            if (this.isPaused) {
                scene.getGraphics().drawImage(pauseImage, 345, 100, null);
                this.getGraphics().drawImage(scene, 0, 0, null);
                continue;
            }
            scene.getGraphics().drawImage(bottomImage, 0, 416, null);
            runAndDraw();
            this.getGraphics().drawImage(scene, 0, 0, null);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (bomber.isDead) {
            scene.getGraphics().drawImage(loseImage, 340, 100, null);
            while (true) {
                this.getGraphics().drawImage(scene, 0, 0, null);
            }
        }
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("bomberman");
        mainFrame.setSize(BombermanGame.WIDTH, BombermanGame.HEIGHT);
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
