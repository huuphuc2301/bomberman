package entities;

import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Balloom extends Enemy {
    private static Sprite[] balloomMovingSprites = {
            Sprite.balloom_right1,
            Sprite.balloom_right2,
            Sprite.balloom_right3,
            Sprite.balloom_left1,
            Sprite.balloom_left2,
            Sprite.balloom_left3,
            Sprite.balloom_right1,
            Sprite.balloom_right2,
            Sprite.balloom_right3,
            Sprite.balloom_left1,
            Sprite.balloom_left2,
            Sprite.balloom_left3,
    };

    public Balloom(int x, int y) {
        super(x, y, balloomMovingSprites);
        setSpeed(1);
    }

    @Override
    public void setTarget(BombermanGame game) {
        int col = x/Sprite.SIZE;
        int row = y/Sprite.SIZE;
        ArrayList<Point> targets = new ArrayList<Point>();
        while (++col <= game.getWidth() / Sprite.SIZE) {
            if (game.staticEntities[row][col] instanceof Bomb)
                break;
            if (game.staticEntities[row][col] instanceof Grass) {
                targets.add(new Point(col, row));
            } else break;
        }
        col = x/Sprite.SIZE;
        while (--col >= 0) {
            if (game.staticEntities[row][col] instanceof Bomb)
                break;
            if (game.staticEntities[row][col]  instanceof Grass) {
                targets.add(new Point(col, row));
            } else break;
        }
        col = x/Sprite.SIZE;
        while (++row < game.getHeight() / Sprite.SIZE) {
            if (game.staticEntities[row][col] instanceof Bomb)
                break;
            if (game.staticEntities[row][col]  instanceof Grass) {
                targets.add(new Point(col, row));
            } else break;
        }
        row = y/Sprite.SIZE;
        while (--row >= 0) {
            if (game.staticEntities[row][col] instanceof Bomb)
                break;
            if (game.staticEntities[row][col]  instanceof Grass) {
                targets.add(new Point(col, row));
            } else break;
        }
        row = y/Sprite.SIZE;
        if (targets.size()==0) targets.add(new Point(-1,-1));
        Collections.shuffle(targets);
        targetX = targets.get(0).x * Sprite.SIZE;
        targetY = targets.get(0).y * Sprite.SIZE;
        //for (int i=0;i<targets.size();i++)
            //System.out.println(String.valueOf(targets.get(i).x) + " " + String.valueOf(targets.get(i).y));

    }
}
