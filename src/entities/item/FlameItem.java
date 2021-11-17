package entities.item;

import entities.bomber.Bomber;
import graphics.Sprite;

public class FlameItem extends Item {
    public FlameItem(int x, int y) {
        super(x, y, Sprite.powerup_flames);
    }

    @Override
    public void upgrade(Bomber bomber) {
        this.isUsed = true;
        bomber.setBombSize(bomber.getBombSize() + 1);
    }
}
