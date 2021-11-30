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
    public static final char MINVO = '3';
    public static final char KONDORIA = '4';
    public static final char BOMB_ITEM = 'b';
    public static final char FLAME_ITEM = 'f';
    public static final char SPEED_ITEM = 's';
    public static final char PORTAL = 'x';
    public int num_rows;
    public int num_columns;
    public char[][] originMap = new char [50][100];
    public static final String[] MAP_PATHS = {
            "map-config\\map1.txt",
            "map-config\\map2.txt",
            "map-config\\map3.txt"
    };

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
        try {
            String[] first_line = buffReader.readLine().split(" ");
            int stage_index = Integer.parseInt(first_line[0]);
            num_rows = Integer.parseInt(first_line[1]);
            num_columns = Integer.parseInt(first_line[2]);
        } catch(IOException e) {
            System.out.println("Error read file");
        }
        int i = 0;
        while (i < num_rows) {
            try {
                if ((str = buffReader.readLine()) == null) break;
                for (int j = 0; j < num_columns; j++) {
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
