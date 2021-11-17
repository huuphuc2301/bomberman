package entities.item;

import entities.bomber.Bomber;
import graphics.Sprite;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y) {
        super(x, y, Sprite.powerup_speed);
    }

    @Override
    public void upgrade(Bomber bomber) {
        this.isUsed = true;
        bomber.setSpeed(bomber.getSpeed() + 1);
    }
}
