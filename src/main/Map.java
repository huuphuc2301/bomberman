package main;

import entities.Brick;
import entities.Entity;
import entities.Wall;
import entities.bomber.Bomb;
import graphics.Sprite;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Map {
    public static final char WALL = '#';
    public static final char BRICK = '*';
    public static final char GRASS = ' ';
    public static final char BOMBER = 'p';
    public static final char BALLOOM = '1';
    public static final char ONEAL = '2';
    public static final char BOMB_ITEM = 'b';
    public static final char FLAME_ITEM = 'f';
    public static final char SPEED_ITEM = 's';
    public char[][] originMap = new char [13][31];

    public static Point getPosition(int x, int y) {
        return new Point(y / Sprite.SIZE, x / Sprite.SIZE);
    }

    public static boolean isBlock(Entity entity) {
        return (entity instanceof Wall || entity instanceof Brick || entity instanceof Bomb);
    }

    void readMap(String path) {
        BufferedReader buffReader = null;
        try {
            buffReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.out.println("Error read file 1");
        }
        String str;
        int i = 0;
        while (true) {
            try {
                if ((str = buffReader.readLine()) == null) break;
                for (int j = 0; j < 31; j++) {
                    originMap[i][j] = str.charAt(j);
                }
                i++;
            } catch (IOException e) {
                System.out.println("Error read file 2");
            }
        }
    }

    public Map(String path) {
        readMap(path);
    }
}
