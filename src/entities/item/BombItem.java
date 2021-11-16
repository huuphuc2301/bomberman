package entities.item;

import entities.bomber.Bomber;
import graphics.Sprite;

public class BombItem extends Item {
    public BombItem(int x, int y) {
        super(x, y, Sprite.powerup_bombs);
    }

    @Override
    public void upgrade(Bomber bomber) {
        this.isUsed = true;
        bomber.setMaxBomb(bomber.getMaxBomb() + 1);
    }
}
