package entities;

import graphics.Sprite;
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


    public void moveRight() {
        times++;
        if (times == 2) {
            rightIndex++;
            times = 0;
        }
        if (rightIndex > 2) rightIndex = 0;
        mainSprite = movingSprites[rightIndex];
        this.setX(x + speed);
    }

    public void moveLeft() {
        times++;
        if (times == 2) {
            leftIndex++;
            times = 0;
        }
        if (leftIndex > 5) leftIndex = 3;
        mainSprite = movingSprites[leftIndex];
        this.setX(x - speed);
    }

    public void moveUp() {
        times++;
        if (times == 2) {
            upIndex++;
            times = 0;
        }
        if (upIndex > 8) upIndex = 6;
        mainSprite = movingSprites[upIndex];
        this.setY(y - speed);
    }

    public void moveDown() {
        times++;
        if (times == 2) {
            downIndex++;
            times = 0;
        }
        if (downIndex > 11) downIndex = 9;
        mainSprite = movingSprites[downIndex];
        this.setY(y + speed);
    }

    public void update(Graphics g) {

    }


}
