package entities;

import entities.bomber.Bomber;
import entities.item.Item;
import graphics.Sprite;

import java.awt.*;

public class Portal extends Entity {
    public Portal(int x, int y) {
        super(x, y, Sprite.portal);
    }

    @Override
    public void draw(Graphics g) {
        Grass newGrass = new Grass( x, y, Sprite.grass);
        newGrass.draw(g);
        g.drawImage(mainSprite.getImage(), x, y, Sprite.SIZE, Sprite.SIZE, null);
    }
}
