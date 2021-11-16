package entities.enemy;

import entities.Grass;
import entities.bomber.Bomb;
import entities.enemy.Enemy;
import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Oneal extends Enemy {
    private static Sprite[] balloomMovingSprites = {
            Sprite.oneal_right1,
            Sprite.oneal_right2,
            Sprite.oneal_right3,
            Sprite.oneal_left1,
            Sprite.oneal_left2,
            Sprite.oneal_left3,
            Sprite.oneal_right1,
            Sprite.oneal_right2,
            Sprite.oneal_right3,
            Sprite.oneal_left1,
            Sprite.oneal_left2,
            Sprite.oneal_left3,
    };

    public Oneal(int x, int y) {
        super(x, y, balloomMovingSprites);
        setSpeed(1);
    }

    @Override
    public void setTarget(BombermanGame game) {
        int enemyCol = x / Sprite.SIZE;
        int enemyRow = y / Sprite.SIZE;
        int bomberCol = game.bomber.getX() / Sprite.SIZE;
        int bomberRow = game.bomber.getY() / Sprite.SIZE;
        int[] X = {0, 0, 1, -1};
        int[] Y = {1, -1, 0, 0};
        int[][] dis = new int[game.getHeight()][game.getWidth()];
        for (int i = 0; i < game.getHeight(); i++)
            for (int j = 0; j < game.getWidth(); j++)
                dis[i][j] = 10000;

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(bomberRow, bomberCol));
        dis[bomberRow][bomberCol] = 0;
        while (!queue.isEmpty()) {
            int row = queue.element().x;
            int col = queue.element().y;
            queue.remove();
            for (int i = 0; i < 4; i++) {
                int newrow = row + Y[i];
                int newcol = col + X[i];
                if (newcol < 0 || newrow < 0 || newrow >= BombermanGame.numRows || newcol >= BombermanGame.numColumns)
                    continue;
                if (!Map.isBlock(game.staticEntities[newrow][newcol]) && dis[newrow][newcol]==10000) {
                    dis[newrow][newcol] = dis[row][col]+1;
                    queue.add(new Point(newrow,newcol));
                }
                //System.out.println(String.valueOf(newrow)+" "+String.valueOf(newcol)+" "+String.valueOf(dis[newrow][newcol]));
            }
        }
        int minDis=10001;
        for (int i = 0; i < 4; i++) {
            int newcol = enemyCol + X[i];
            int newrow = enemyRow + Y[i];

            if (!Map.isBlock(game.staticEntities[newrow][newcol]) && minDis>dis[newrow][newcol]) {
                targetX=newcol * Sprite.SIZE;
                targetY=newrow * Sprite.SIZE;
                minDis = dis[newrow][newcol];

            }


        }
        //System.out.println(String.valueOf(minDis)+" "+String.valueOf(targetY/Sprite.SIZE)+" "+String.valueOf(targetX/Sprite.SIZE));

    }
}
