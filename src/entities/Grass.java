package entities;

import graphics.Sprite;

public class Grass extends Entity {
    public Grass() {
        super(0, 0, Sprite.grass);
    }

    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
}
