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
        int posX = x/Sprite.SIZE;
        int posY = y/Sprite.SIZE;
        ArrayList<Point> targets = new ArrayList<Point>();
        while (++posX <= game.getWidth() / Sprite.SIZE) {
            if (game.staticEntities[posY][posX] instanceof Grass) {
                targets.add(new Point(posX, posY));
            } else break;
        }
        posX = x/Sprite.SIZE;
        while (--posX >= 0) {
            if (game.staticEntities[posY][posX]  instanceof Grass) {
                targets.add(new Point(posX, posY));
            } else break;
        }
        posX = x/Sprite.SIZE;
        while (++posY < game.getHeight() / Sprite.SIZE) {
            if (game.staticEntities[posY][posX]  instanceof Grass) {
                targets.add(new Point(posX, posY));
            } else break;
        }
        posY = y/Sprite.SIZE;
        while (--posY >= 0) {
            if (game.staticEntities[posY][posX]  instanceof Grass) {
                targets.add(new Point(posX, posY));
            } else break;
        }
        posY = y/Sprite.SIZE;
        Collections.shuffle(targets);
        targetX = targets.get(0).x * Sprite.SIZE;
        targetY = targets.get(0).y * Sprite.SIZE;
        //for (int i=0;i<targets.size();i++)
            //System.out.println(String.valueOf(targets.get(i).x) + " " + String.valueOf(targets.get(i).y));

    }
}
