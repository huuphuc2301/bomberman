package main;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import entities.Brick;
import entities.Entity;
import entities.Grass;
import entities.Wall;
import graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BombermanGame extends JPanel {

    private Canvas canvas;
    private BufferedImage view;
    public static final int PosY = 0;
    public static final int WIDTH = 1488;
    private static final int HEIGHT = 650 + PosY;
    public static final int M = 13;
    public static final int N = 31;


    Entity[][] staticEntities = new Entity[13][31];

    void loadMap(String path) {
        Map map1 = new Map(path);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                switch (map1.entities[i][j]) {
                    case Map.WALL:
                        staticEntities[i][j] = new Wall(j * 48, i * 48 + PosY, Sprite.wall);
                        break;
                    case Map.BRICK:
                        staticEntities[i][j] = new Brick(j * 48, i * 48 + PosY, Sprite.brick);
                        break;
                    default:
                        staticEntities[i][j] = new Grass(j * 48, i * 48 + PosY, Sprite.grass);
                }
            }
        }
    }



    public void paint(Graphics g) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                staticEntities[i][j].draw(g);
            }
        }
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("bomberman");
        mainFrame.setSize(1000, HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        BombermanGame myGame = new BombermanGame();
        myGame.loadMap("src\\map-config\\map1.txt");
        mainFrame.add(myGame);
        mainFrame.setVisible(true);
    }

}
