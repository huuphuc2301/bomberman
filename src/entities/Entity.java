package entities;

import graphics.Sprite;

import javax.swing.*;
import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Sprite sprite;

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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Entity(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;

    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(sprite.getImage(), x, y, sprite.getWidth(), sprite.getHeight(), null);
    }

}
