package entities.enemy;

import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;
import java.util.*;

public class Oneal extends Enemy {
    private int steps = 0;
    private boolean isFastMode = true;
    private static final Sprite[] movingSprites = {
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
    private static final Sprite[] deadSprites = {
            Sprite.oneal_dead,
            Sprite.mob_dead1,
            Sprite.mob_dead2,
            Sprite.mob_dead3,
    };
    public Oneal(int x, int y) {
        super(x, y, movingSprites,deadSprites);

        setSpeed(1);
    }

    @Override
    public void move(BombermanGame game) {
        //if (!new Random().nextBoolean()) return;
        steps++;
        if (steps > new Random().nextInt(10000) + 100) {
            isFastMode = !isFastMode;
            steps = 0;
        }
        if (isFastMode) {
            super.move(game);
            return;
        }

        if (steps % 2 == 0) return;
        super.move(game);

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
                if (!Map.isBlock(game.staticEntities[newrow][newcol]) && dis[newrow][newcol] == 10000) {
                    dis[newrow][newcol] = dis[row][col] + 1;
                    queue.add(new Point(newrow, newcol));
                }
                //System.out.println(String.valueOf(newrow)+" "+String.valueOf(newcol)+" "+String.valueOf(dis[newrow][newcol]));
            }
        }
        int row = enemyRow;
        int col = enemyCol;
        ArrayList<Point> points = new ArrayList<>();
        while (++col <= game.getWidth() / Sprite.SIZE) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                points.add(new Point(col, row));
            } else break;
        }
        col = x / Sprite.SIZE;
        while (--col >= 0) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                points.add(new Point(col, row));
            } else break;
        }
        col = x / Sprite.SIZE;
        while (++row < game.getHeight() / Sprite.SIZE) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                points.add(new Point(col, row));
            } else break;
        }
        row = y / Sprite.SIZE;
        while (--row >= 0) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                points.add(new Point(col, row));
            } else break;
        }
        row = y / Sprite.SIZE;
        ArrayList<Point> targets = new ArrayList<>();
        int minDis = 10001;
        for (Point point : points) {
            int newcol = point.x;
            int newrow = point.y;
            if (!Map.isBlock(game.staticEntities[newrow][newcol]) && minDis > dis[newrow][newcol]) {
                minDis = dis[newrow][newcol];
                targets.clear();
            }
            if (!Map.isBlock(game.staticEntities[newrow][newcol]) && minDis == dis[newrow][newcol]) {
                targets.add(new Point(newcol, newrow));
            }

        }
        if (targets.size() == 0) targets.add(new Point(-1, -1));
        Collections.shuffle(targets);
        targetX = targets.get(0).x * Sprite.SIZE;
        targetY = targets.get(0).y * Sprite.SIZE;
        //System.out.println(String.valueOf(minDis)+" "+String.valueOf(targetY/Sprite.SIZE)+" "+String.valueOf(targetX/Sprite.SIZE));

    }
}
