package entities;

import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;
import java.util.ArrayList;

public class MovingEntity extends Entity {
    private Sprite[] movingSprites = new Sprite[12];
    private int rightIndex = 0;
    private int leftIndex = 3;
    private int upIndex = 6;
    private int downIndex = 9;
    private int speed;
    private int times = 0;

    public Point getCenter() {
        return new Point(x + mainSprite.getWidth() / 2, y + mainSprite.getHeight() / 2);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int x) {
        speed = x;
    }

    public MovingEntity(int x, int y, Sprite[] sprites) {
        super(x, y, sprites[0]);
        for (int i = 0; i < 12; i++) movingSprites[i] = sprites[i];
    }


    public void moveRight(BombermanGame game) {
        times++;
        if (times == 3) {
            rightIndex++;
            times = 0;
        }
        if (rightIndex > 2) rightIndex = 0;
        mainSprite = movingSprites[rightIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int maxX = 30 * Sprite.SIZE;
        for (int j = pos.y; j < 31; j++) {
            if (!(game.staticEntities[pos.x][j] instanceof Grass)) {
                maxX = j * Sprite.SIZE;
                break;
            }
        }
        setX(Math.min(maxX - getWidth(), x + speed));
    }

    public void moveLeft(BombermanGame game) {
        times++;
        if (times == 3) {
            leftIndex++;
            times = 0;
        }
        if (leftIndex > 5) leftIndex = 3;
        mainSprite = movingSprites[leftIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int minX = 1 * Sprite.SIZE;
        for (int j = pos.y; j > 0; j--) {
            if (!(game.staticEntities[pos.x][j] instanceof Grass)) {
                minX = (j + 1) * Sprite.SIZE;
                break;
            }
        }
        setX(Math.max(minX, x - speed));
    }

    public void moveUp(BombermanGame game) {
        times++;
        if (times == 3) {
            upIndex++;
            times = 0;
        }
        if (upIndex > 8) upIndex = 6;
        mainSprite = movingSprites[upIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int minY = 1 * Sprite.SIZE;
        for (int i = pos.x; i > 0; i--) {
            if (!(game.staticEntities[i][pos.y] instanceof Grass)) {
                minY = (i + 1) * Sprite.SIZE;
                break;
            }
        }
        this.setY(Math.max(minY,y - speed));
    }

    public void moveDown(BombermanGame game) {
        times++;
        if (times == 3) {
            downIndex++;
            times = 0;
        }
        if (downIndex > 11) downIndex = 9;
        mainSprite = movingSprites[downIndex];

        Point center = getCenter();
        Point pos = Map.getPosition(center.x, center.y);
        int maxY = 13 * Sprite.SIZE;
        for (int i = pos.x; i < 13; i++) {
            if (!(game.staticEntities[i][pos.y] instanceof Grass)) {
                maxY = i * Sprite.SIZE;
                break;
            }
        }
        this.setY(Math.min(maxY - getHeight(),y + speed));
    }

    public void update(Graphics g) {

    }


}
