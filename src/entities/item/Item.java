package entities.item;

import entities.Entity;
import entities.Grass;
import entities.bomber.Bomber;
import graphics.Sprite;

public abstract class Item extends Entity {
    private static Sprite[] explodeSprites = {
            Sprite.item_exploded,
            Sprite.item_exploded1,
            Sprite.item_exploded2
    };
    private int spriteLoop = 7;
    private int times = 0;
    public boolean isExploding = false;
    public boolean isDestroyed = false;
    public boolean isUsed = false;
    private int explodeSpriteIndex = 0;

    public Item(int x, int y, Sprite sprite) {
        super(x, y, sprite);
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

    public abstract void upgrade(Bomber bomber);

}
