package entities;

import graphics.Sprite;
import main.BombermanGame;

import java.awt.*;
import java.util.ArrayList;

public class Bomb extends Entity {
    public static Sprite[] bombSprites = {
            Sprite.bomb,
            Sprite.bomb_1,
            Sprite.bomb_2,
    };
    public static Sprite[] explodeSprites = {
            Sprite.bomb_exploded,
            Sprite.bomb_exploded1,
            Sprite.bomb_exploded2
    };
    public static Sprite[] verticalSprites = {
            Sprite.explosion_vertical,
            Sprite.explosion_vertical1,
            Sprite.explosion_vertical2
    };
    public static Sprite[] horizontalSprites = {
            Sprite.explosion_horizontal,
            Sprite.explosion_horizontal1,
            Sprite.explosion_horizontal2
    };
    public static Sprite[] topSprites = {
            Sprite.explosion_vertical_top_last,
            Sprite.explosion_vertical_top_last1,
            Sprite.explosion_vertical_top_last2
    };
    public static Sprite[] bottomSprites = {
            Sprite.explosion_vertical_down_last,
            Sprite.explosion_vertical_down_last1,
            Sprite.explosion_vertical_down_last2
    };
    public static Sprite[] leftprites = {
            Sprite.explosion_horizontal_left_last,
            Sprite.explosion_horizontal_left_last1,
            Sprite.explosion_horizontal_left_last2
    };
    public static Sprite[] rightprites = {
            Sprite.explosion_horizontal_right_last,
            Sprite.explosion_horizontal_right_last1,
            Sprite.explosion_horizontal_right_last2
    };

    private long createTime;
    private long currentTime;
    public boolean isRunning = true;
    public boolean isExploding = false;
    private int bombSize;
    public static int bombSpriteLoop = 6;
    public static int explodeSpriteLoop = 5;
    private int spriteIndex = 0;
    private int indexInc;
    private int times = 0;
    private ArrayList<Flame> flames = new ArrayList<>();
    private ArrayList<Brick> explodeBrick = new ArrayList<>();
    private BombermanGame game;

    public Bomb(int x, int y, int size, BombermanGame game) {
        super(x, y, bombSprites[0]);
        this.game = game;
        bombSize = size;
        createTime = System.currentTimeMillis();

        int posRow = y / Sprite.SIZE;
        int posColumn =  x / Sprite.SIZE;

        int num = 0;
        for (int j = posColumn + 1; j <= Math.min(posColumn + bombSize - 1, 30); j++) {
            if (game.staticEntities[posRow][j] instanceof Grass) {
                flames.add(new Flame(j * Sprite.SIZE,posRow * Sprite.SIZE,  horizontalSprites));
                num++;
            }
            else break;;
        }
        if (num == size - 1 &&
                posColumn + size < 31 && game.staticEntities[posRow][posColumn + size] instanceof Grass) {
            flames.add(new Flame((posColumn + size) * Sprite.SIZE, posRow * Sprite.SIZE, rightprites));
        }

        num = 0;
        for (int j = posColumn - 1; j >= Math.max(posColumn - bombSize + 1, 0); j--) {
            if (game.staticEntities[posRow][j] instanceof Grass) {
                flames.add(new Flame(j * Sprite.SIZE,posRow * Sprite.SIZE,  horizontalSprites));
                num++;
            }
            else break;;
        }
        if (num == size - 1 &&
                posColumn - size >= 0 && game.staticEntities[posRow][posColumn - size] instanceof Grass) {
            flames.add(new Flame((posColumn - size) * Sprite.SIZE, posRow * Sprite.SIZE, leftprites));
        }

        num = 0;
        for (int i = posRow + 1; i <= Math.min(posRow + bombSize - 1, 12); i++) {
            if (game.staticEntities[i][posColumn] instanceof Grass) {
                flames.add(new Flame(posColumn * Sprite.SIZE,i * Sprite.SIZE,  verticalSprites));
            }
            else break;
        }
        if (num == size - 1 &&
                posRow + size < 13 && game.staticEntities[posRow + size ][posColumn] instanceof Grass) {
            flames.add(new Flame(posColumn * Sprite.SIZE, (posRow + size) * Sprite.SIZE, bottomSprites));
        }

        num = 0;
        for (int i = posRow - 1; i >= Math.max(posRow - bombSize + 1, 0); i--) {
            if (game.staticEntities[i][posColumn] instanceof Grass) {
                flames.add(new Flame(posColumn * Sprite.SIZE,i * Sprite.SIZE,  verticalSprites));
            }
            else break;
        }
        if (num == size - 1 &&
                posRow - size >= 0 && game.staticEntities[posRow - size ][posColumn] instanceof Grass) {
            flames.add(new Flame(posColumn * Sprite.SIZE, (posRow - size) * Sprite.SIZE, topSprites));
        }

    }

    public void run() {
        if (isExploding) {
            exploding();
            return;
        }
        times++;
        times %= bombSpriteLoop;
        if (times != 1) return;
        if (spriteIndex == 2) indexInc = -1;
        if (spriteIndex == 0) indexInc = 1;
        spriteIndex += indexInc;
        mainSprite = bombSprites[spriteIndex];

        if (getCurrentTime() - createTime >= 2900) {
            times = 0;
            spriteIndex = 0;
            indexInc = 1;
            isExploding = true;
            int posRow = y / Sprite.SIZE;
            int posColumn =  x / Sprite.SIZE;
            for (int j = posColumn - 1; j >= posColumn - bombSize; j--) {
                if (game.staticEntities[posRow][j] instanceof Brick) {
                    ((Brick) game.staticEntities[posRow][j]).isExploding = true;
                }
                if (!(game.staticEntities[posRow][j] instanceof Grass)) break;
            }
            for (int j = posColumn + 1; j <= posColumn + bombSize; j++) {
                if (game.staticEntities[posRow][j] instanceof Brick) {
                    ((Brick) game.staticEntities[posRow][j]).isExploding = true;
                }
                if (!(game.staticEntities[posRow][j] instanceof Grass)) break;
            }
            for (int i = posRow - 1; i >= posRow - bombSize; i--) {
                if (game.staticEntities[i][posColumn] instanceof Brick) {
                    ((Brick) game.staticEntities[i][posColumn]).isExploding = true;
                }
                if (!(game.staticEntities[i][posColumn] instanceof Grass)) break;
            }
            for (int i = posRow + 1; i <= posRow + bombSize; i++) {
                if (game.staticEntities[i][posColumn] instanceof Brick) {
                    ((Brick) game.staticEntities[i][posColumn]).isExploding = true;
                }
                if (!(game.staticEntities[i][posColumn] instanceof Grass)) break;
            }

        }
    }

    public void exploding() {
        times++;
        times %= explodeSpriteLoop;
        mainSprite = explodeSprites[spriteIndex];
        for (Flame flame : flames) {
            flame.setMainSprite(flame.sprites[spriteIndex]);
        }
        if (times != 0) return;
        spriteIndex += indexInc;
        if (spriteIndex == 2 && indexInc == 1) indexInc = -1;
        if (spriteIndex == 0 && indexInc == -1) isRunning = false;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (isExploding) {
            for (Flame flame : flames) {
                flame.draw(g);
            }
        }
    }
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
