package main;

import entities.*;
import entities.bomber.Bomb;
import entities.bomber.Bomber;
import entities.enemy.Balloom;
import entities.enemy.Enemy;
import entities.enemy.Oneal;
import entities.item.*;
import graphics.Sprite;
import graphics.TimeScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameStage extends JPanel {
    public static final int numColumns = 31;
    public static final int numRows = 13;
    public Entity[][] staticEntities = new Entity[13][31];
    public Bomber bomber = new Bomber(Sprite.SIZE, Sprite.SIZE);
    BufferedImage scene = new BufferedImage(BombermanGame.WIDTH, BombermanGame.HEIGHT, BufferedImage.TYPE_INT_RGB);
    public String mapPath;
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Bomb> bombs = new ArrayList<>();
    public long createTime, pauseTime, unPauseTime;
    public int timeScoreValue;
    public boolean isPaused = false;
    private boolean nextIsPaused = true;
    private int indexOfStage;
    private boolean isWin = false;
    private TimeScore timeScore;

    public GameStage(int indexOfStage, long createTime, int timeScoreValue) {
        this.indexOfStage = indexOfStage;
        this.mapPath = Map.MAP_PATHS[indexOfStage];
        this.createTime = createTime;
        this.timeScoreValue = timeScoreValue;
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
                        Item flameItem = new FlameItem(j * Sprite.SIZE, i * Sprite.SIZE);
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        ((Brick) staticEntities[i][j]).setItem(flameItem);
                        break;
                    }
                    case Map.SPEED_ITEM: {
                        Item speedItem = new SpeedItem(j * Sprite.SIZE, i * Sprite.SIZE);
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        ((Brick) staticEntities[i][j]).setItem(speedItem);
                        break;
                    }
                    case Map.PORTAL: {
                        Portal portal = new Portal(j * Sprite.SIZE, i * Sprite.SIZE);
                        staticEntities[i][j] = new Brick(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.brick);
                        ((Brick) staticEntities[i][j]).setPortal(portal);
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

                    if (((Brick) staticEntities[i][j]).getItem() != null) {
                        staticEntities[i][j] = ((Brick) staticEntities[i][j]).getItem();

                    } else if  (((Brick) staticEntities[i][j]).getPortal() != null){
                        staticEntities[i][j] = ((Brick) staticEntities[i][j]).getPortal();
                    }
                    else {
                        staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                    }
                    continue;
                }

                if (staticEntities[i][j] instanceof Item && ((Item) staticEntities[i][j]).isDestroyed) {
                    staticEntities[i][j] = new Grass(j * Sprite.SIZE, i * Sprite.SIZE, Sprite.grass);
                    Oneal newOneal = new Oneal(j * Sprite.SIZE, i * Sprite.SIZE);
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
                    } else {
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

        bombs.removeIf(bomb -> !bomb.isRunning);
        for (Bomb bomb : bombs) {
            bomb.run(this);
            bomb.draw(g);
        }

        enemies.removeIf(enemy -> enemy.isDead);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(this);
            enemies.get(i).draw(g);
        }
        bomber.inPortal = false;
        bomber.move(this);
        bomber.draw(g);

        timeScore = new TimeScore((timeScoreValue + (int) (System.currentTimeMillis() - createTime)) / 1000);
        timeScore.draw(g);
        if (bomber.inPortal && enemies.size() == 0) {
            isWin = true;
        }
    }

    public void pauseOrUnpause() {
        isPaused = nextIsPaused;
        if (nextIsPaused) {
            pauseTime = System.currentTimeMillis();
        } else {
            unPauseTime = System.currentTimeMillis();
            timeScoreValue += (int) (pauseTime - createTime);
            createTime = System.currentTimeMillis();
        }
    }

    public void addListener(GameStage game) {
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
                    int notExplodingBombs = 0;
                    for (Bomb bomb:bombs) {
                        if (!bomb.isExploding) notExplodingBombs++;
                    }
                    if (bomber.isDying || notExplodingBombs == bomber.getMaxBomb()) return;
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

    public boolean start() {
        Graphics g = scene.getGraphics();
        loadMap(mapPath);

        setTargetEnemies();

        runAndDraw();
        g.drawImage(Sprite.bottomOfStage.getImage(), 0, 416, null);

        long time1 = System.currentTimeMillis();

        timeScore = new TimeScore((timeScoreValue + (int) (System.currentTimeMillis() - createTime)) / 1000);
        timeScore.draw(g);

        g.setFont(new Font("Calibri", Font.BOLD, 50));
        g.setColor(new Color(35, 29, 116));
        g.drawString("STAGE " + (indexOfStage + 1), 400, 200);
        while (System.currentTimeMillis() - time1 < 1000) {
            isPaused = true;
            this.getGraphics().drawImage(scene, 0, 0, null);
        }
        isPaused = false;

        long secondTime = System.currentTimeMillis();
        int count = 0;
        int sleepTime = 0;
        long startTime = System.currentTimeMillis();
        while (!isWin && !bomber.isDead) {
            if (this.isPaused) {
                g.drawImage(Sprite.pause.getImage(), 345, 100, null);
                this.getGraphics().drawImage(scene, 0, 0, null);
                continue;
            }
            if (System.currentTimeMillis() - secondTime > 1000) {
                System.out.println("FPS " + count);
                count = 0;
                secondTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - startTime > 16 && sleepTime > 0) sleepTime--;
            else if (System.currentTimeMillis() - startTime < 13) sleepTime++;
            startTime = System.currentTimeMillis();
            count++;

            g.drawImage(Sprite.bottomOfStage.getImage(), 0, 416, null);
            runAndDraw();
            this.getGraphics().drawImage(scene, 0, 0, null);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (bomber.isDead) {
            g.drawImage(Sprite.lose.getImage(), 340, 100, null);
            long time2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - time2 < 2000) {
                this.getGraphics().drawImage(scene, 0, 0, null);
            }
            return false;
        }
        if (indexOfStage == Map.MAP_PATHS.length - 1) {
            g.drawImage(Sprite.win.getImage(), 340, 90, null);
            g.setColor(new Color(35, 29, 116));
            g.setFont(new Font("Calibri", Font.BOLD, 40));
            g.drawString("YOUR TIME: " + timeScore.getValue(), 370, 255);
            long time2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - time2 < 3000) {
                this.getGraphics().drawImage(scene, 0, 0, null);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("bomberman");
        mainFrame.setSize(BombermanGame.WIDTH, BombermanGame.HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        //Sound.load();
        GameStage myGame = new GameStage(1, System.currentTimeMillis(), 0);
        myGame.setFocusable(true);
        myGame.requestFocusInWindow();
        myGame.addListener(myGame);
        myGame.setLayout(null);

        mainFrame.add(myGame);
        mainFrame.setVisible(true);

        myGame.start();

    }

}
