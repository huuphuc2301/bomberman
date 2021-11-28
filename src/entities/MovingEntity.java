package entities;

import entities.bomber.Bomb;
import entities.bomber.Bomber;
import entities.enemy.Enemy;
import entities.enemy.Kondoria;
import graphics.Sprite;
import main.GameStage;
import main.Map;
import sounds.Sound;

import java.awt.*;

public abstract class MovingEntity extends Entity {
    private Sprite[] movingSprites;
    private Sprite[] deadSprites;
    private int rightIndex = 0;
    private int leftIndex = 3;
    private int upIndex = 6;
    private int downIndex = 9;
    private int speed;
    private int times = 0;
    private int spriteLoop = 5;
    private int spriteIndex = 0;
    public boolean left, right, up, down;
    public boolean isDead = false;
    public boolean isDying = false;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int x) {
        speed = x;
    }

    public void setSpriteLoop(int x) {
        spriteLoop = x;
    }

    public MovingEntity(int x, int y, Sprite[] movingSprites, Sprite[] deadSprites) {
        super(x, y, movingSprites[0]);
        this.movingSprites = movingSprites;
        this.deadSprites = deadSprites;
    }

    public void moveRight(GameStage game) {
        times++;
        if (times == spriteLoop) {
            rightIndex++;
            times = 0;
        }
        if (rightIndex > 2) rightIndex = 0;
        mainSprite = movingSprites[rightIndex];
        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int maxX = game.numColumns * Sprite.SIZE;
        if (pos.y + 1 < game.numColumns) {
            int j = pos.y + 1;
            if (game.staticEntities[pos.x][j] instanceof Wall || !(this instanceof Kondoria) )
            if (Map.isBlock(game.staticEntities[pos.x][j])) {
                maxX = j * Sprite.SIZE;
                if (this instanceof Enemy && x + speed > maxX - getWidth()) {
                    ((Enemy) this).setTarget(game);
                }
            }
        }
        setX(Math.min(maxX - getWidth(), x + speed));
    }

    public void moveLeft(GameStage game) {
        times++;
        if (times == spriteLoop) {
            leftIndex++;
            times = 0;
        }
        if (leftIndex > 5) leftIndex = 3;
        mainSprite = movingSprites[leftIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int minX = Sprite.SIZE;
        if (pos.y - 1 >= 0) {
            int j = pos.y - 1;
            if (game.staticEntities[pos.x][j] instanceof Wall || !(this instanceof Kondoria) )
            if (Map.isBlock(game.staticEntities[pos.x][j])) {
                minX = (j + 1) * Sprite.SIZE;
                if (this instanceof Enemy && x - speed < minX) {
                    ((Enemy) this).setTarget(game);
                }
            }
        }
        setX(Math.max(minX, x - speed));
    }

    public void moveUp(GameStage game) {
        times++;
        if (times == spriteLoop) {
            upIndex++;
            times = 0;
        }
        if (upIndex > 8) upIndex = 6;
        mainSprite = movingSprites[upIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int minY = Sprite.SIZE;
        if (pos.x - 1 >= 0) {
            int i = pos.x - 1;
            if (game.staticEntities[i][pos.y] instanceof Wall || !(this instanceof Kondoria) )
            if (Map.isBlock(game.staticEntities[i][pos.y])) {
                minY = (i + 1) * Sprite.SIZE;
                if (this instanceof Enemy && y - speed < minY) {
                    ((Enemy) this).setTarget(game);
                }
            }
        }
        this.setY(Math.max(minY, y - speed));
    }

    public void moveDown(GameStage game) {
        times++;
        if (times == spriteLoop) {
            downIndex++;
            times = 0;
        }
        if (downIndex > 11) downIndex = 9;
        mainSprite = movingSprites[downIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int maxY = game.numRows * Sprite.SIZE;
        if (pos.x + 1 < game.numRows) {
            int i = pos.x + 1;
            if (game.staticEntities[i][pos.y] instanceof Wall || !(this instanceof Kondoria) )
            if (Map.isBlock(game.staticEntities[i][pos.y])) {
                maxY = i * Sprite.SIZE;
                if (this instanceof Enemy && y + speed > maxY - getHeight()) {
                    ((Enemy) this).setTarget(game);
                }
            }
        }
        this.setY(Math.min(maxY - getHeight(), y + speed));
    }

    public boolean isTouch(int targetX, int targetY) {
        return (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed);
    }

    public abstract void move(GameStage game);

    public void die() {
        if (isDying) return;
        isDying = true;
        times = 0;
        spriteIndex = 0;
        if (this instanceof Bomber) spriteLoop = 35;
        else spriteLoop = 25;
        if (this instanceof Bomber) Sound.play(Sound.bomber_die_path);
        else Sound.play(Sound.enemy_die_path);
    }

    public void dying() {
        mainSprite = deadSprites[spriteIndex];
        times++;
        times %= spriteLoop;
        if (times != 0) return;
        if (spriteIndex == 0) spriteLoop -= 10;
        spriteIndex++;
        if (spriteIndex == deadSprites.length) isDead = true;
    }

}
