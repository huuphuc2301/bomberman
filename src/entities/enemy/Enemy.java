package entities.enemy;

import entities.MovingEntity;
import graphics.Sprite;
import main.BombermanGame;

public abstract class Enemy extends MovingEntity {
    int targetX;
    int targetY;

    public Enemy(int x, int y, Sprite[] movingSprites, Sprite[] deadSprites) {
        super(x, y, movingSprites,deadSprites);
    }

    public abstract void setTarget(BombermanGame game);

    @Override
    public void move(BombermanGame game) {
        if (isDying) {
            dying();
            return;
        }
        if (targetX < 0) {
            setTarget(game);
        }
        if (isTouch(targetX,targetY)) {
            setX(targetX);
            setY(targetY);
            setTarget(game);
            return;
        }
        if (x < targetX) moveRight(game);
        if (x > targetX) moveLeft(game);
        if (y < targetY) moveDown(game);
        if (y > targetY) moveUp(game);
    }
}
