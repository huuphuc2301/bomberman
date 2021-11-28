package entities.enemy;

import graphics.Sprite;
import main.GameStage;
import main.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Balloom extends Enemy {
    private int steps;
    private static Sprite[] movingSprites = {
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
    private static final Sprite[] deadSprites = {
            Sprite.balloom_dead,
            Sprite.mob_dead1,
            Sprite.mob_dead2,
            Sprite.mob_dead3,
    };
    public Balloom(int x, int y) {
        super(x, y, movingSprites,deadSprites);
        setSpeed(1);
    }

    @Override
    public void move(GameStage game) {
        if (isDying) {
            super.move(game);
            return;
        }
        steps=(steps+1)%4;
        if (steps==0) return;
        super.move(game);
    }

    @Override
    public void setTarget(GameStage game) {
        int col = x/Sprite.SIZE;
        int row = y/Sprite.SIZE;
        ArrayList<Point> targets = new ArrayList<>();
        while (++col <= game.getWidth() / Sprite.SIZE) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                targets.add(new Point(col, row));
            } else break;
        }
        col = x/Sprite.SIZE;
        while (--col >= 0) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                targets.add(new Point(col, row));
            } else break;
        }
        col = x/Sprite.SIZE;
        while (++row < game.getHeight() / Sprite.SIZE) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
                targets.add(new Point(col, row));
            } else break;
        }
        row = y/Sprite.SIZE;
        while (--row >= 0) {
            if (!Map.isBlock(game.staticEntities[row][col])) {
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
