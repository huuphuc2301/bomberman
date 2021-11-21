package entities;

import entities.item.Item;
import graphics.Sprite;

public class Brick extends Entity {
    private static Sprite[] explodeSprites = {
            Sprite.brick_exploded,
            Sprite.brick_exploded1,
            Sprite.brick_exploded2
    };
    private int spriteLoop = 7;
    private int times = 0;
    public boolean isExploding = false;
    public boolean isDestroyed = false;
    private int explodeSpriteIndex = 0;
    private Item item;
    private Portal portal;
    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public void run() {
        if (!isExploding) return;
        times++;
        times %= spriteLoop;
        mainSprite = explodeSprites[explodeSpriteIndex];
        if (times != 0) return;
        explodeSpriteIndex++;
        if (explodeSpriteIndex == 3) isDestroyed = true;
    }
}
