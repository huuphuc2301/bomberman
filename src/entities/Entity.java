package entities;

import graphics.Sprite;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Sprite mainSprite;

    public Entity(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.mainSprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite getMainSprite() {
        return mainSprite;
    }

    public int getWidth() {
        return mainSprite.getWidth();
    }

    public int getHeight() {
        return mainSprite.getHeight();
    }

    public void setMainSprite(Sprite mainSprite) {
        this.mainSprite = mainSprite;
    }

    public Point getCenter() {
        return new Point(x + mainSprite.getWidth() / 2, y + mainSprite.getHeight() / 2);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(mainSprite.getImage(), x, y, Sprite.SIZE, Sprite.SIZE, null);
    }

}
