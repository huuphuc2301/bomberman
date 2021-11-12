package entities;

import graphics.Sprite;
import main.BombermanGame;

public abstract class Enemy extends MovingEntity{
    int targetX;
    int targetY;
    private static final Sprite[] deadSprites = {
            Sprite.mob_dead1,
            Sprite.mob_dead2,
            Sprite.mob_dead3,
    };
    public Enemy(int x, int y, Sprite[] movingSprites) {
        super(x, y, movingSprites,Enemy.deadSprites);
    }

    public abstract void setTarget(BombermanGame game);
    public void move(BombermanGame game) {
        if (targetX < 0) {
            setTarget(game);
        }
        if (isTouch(targetX,targetY)) {
            setX(targetX);
            setY(targetY);
            setTarget(game);
            return;
        }
        if (x<targetX) moveRight(game);
        if (x>targetX) moveLeft(game);
        if (y<targetY) moveDown(game);
        if (y>targetY) moveUp(game);
    }
}
