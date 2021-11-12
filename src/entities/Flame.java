package entities;

import graphics.Sprite;

public class Flame extends Entity {
    public Sprite[] sprites = new Sprite[3];
    public Flame(int x, int y, Sprite sprites[]) {
        super(x, y, sprites[0]);
        this.sprites = sprites;
    }
}
