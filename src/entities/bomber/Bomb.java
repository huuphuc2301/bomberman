package entities.bomber;

import entities.Brick;
import entities.Entity;
import entities.Grass;
import entities.Portal;
import entities.enemy.Enemy;
import entities.item.Item;
import graphics.Sprite;
import main.GameStage;
import main.Map;
import sounds.Sound;

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
    private GameStage game;

    public Bomb(int x, int y, int size, GameStage game) {
        super(x, y, bombSprites[0]);
        this.game = game;
        bombSize = size;
        createTime = System.currentTimeMillis();
        Sound.play(Sound.drop_bomb_path);
    }

    public void run(GameStage game) {
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

        if (!game.isPaused && getCurrentTime() - game.unPauseTime + game.pauseTime - createTime >= 2000) {
            this.explode(game);
        }

    }

    public void explode(GameStage game) {
        Sound.play(Sound.explosion_path);
        times = 0;
        spriteIndex = 0;
        indexInc = 1;
        isExploding = true;
        int posRow = y / Sprite.SIZE;
        int posColumn =  x / Sprite.SIZE;

        flames.add(new Flame(posColumn * Sprite.SIZE, posRow * Sprite.SIZE, explodeSprites));

        for (int j = posColumn + 1; j <= posColumn + bombSize; j++) {
            if (game.staticEntities[posRow][j] instanceof Brick) {
                ((Brick) game.staticEntities[posRow][j]).isExploding = true;
                break;
            }
            if (game.staticEntities[posRow][j] instanceof Item) {
                ((Item) game.staticEntities[posRow][j]).isExploding = true;
                break;
            }
            if (game.staticEntities[posRow][j] instanceof Bomb && !((Bomb) game.staticEntities[posRow][j]).isExploding) {
                ((Bomb) game.staticEntities[posRow][j]).explode(game);
                break;
            }
            if (game.staticEntities[posRow][j] instanceof Grass
                    || game.staticEntities[posRow][j] instanceof Portal) {
                if (j - posColumn < bombSize) {
                    flames.add(new Flame(j * Sprite.SIZE,posRow * Sprite.SIZE,  horizontalSprites));
                }
                else flames.add(new Flame((posColumn + bombSize) * Sprite.SIZE, posRow * Sprite.SIZE, rightprites));
            }
            else break;;
        }

        for (int j = posColumn - 1; j >= posColumn - bombSize; j--) {
            if (game.staticEntities[posRow][j] instanceof Brick) {
                ((Brick) game.staticEntities[posRow][j]).isExploding = true;
                break;
            }
            if (game.staticEntities[posRow][j] instanceof Item) {
                break;
            }
            if (game.staticEntities[posRow][j] instanceof Bomb && !((Bomb) game.staticEntities[posRow][j]).isExploding) {
                ((Bomb) game.staticEntities[posRow][j]).explode(game);
                break;
            }
            if (game.staticEntities[posRow][j] instanceof Grass
                    || game.staticEntities[posRow][j] instanceof Portal) {
                if (posColumn - j < bombSize) {
                    flames.add(new Flame(j * Sprite.SIZE,posRow * Sprite.SIZE,  horizontalSprites));
                }
                else flames.add(new Flame((posColumn - bombSize) * Sprite.SIZE, posRow * Sprite.SIZE, leftprites));
            }
            else break;
        }

        for (int i = posRow + 1; i <= posRow + bombSize; i++) {
            if (game.staticEntities[i][posColumn] instanceof Brick) {
                ((Brick) game.staticEntities[i][posColumn]).isExploding = true;
                break;
            }
            if (game.staticEntities[i][posColumn] instanceof Item) {
                ((Item) game.staticEntities[i][posColumn]).isExploding = true;
                break;
            }
            if (game.staticEntities[i][posColumn] instanceof Bomb && !((Bomb) game.staticEntities[i][posColumn]).isExploding) {
                ((Bomb) game.staticEntities[i][posColumn]).explode(game);
                break;
            }
            if (game.staticEntities[i][posColumn] instanceof Grass
                    || game.staticEntities[i][posColumn] instanceof Portal) {
                if (i - posRow < bombSize) {
                    flames.add(new Flame(posColumn * Sprite.SIZE,i * Sprite.SIZE,  verticalSprites));
                }
                else flames.add(new Flame(posColumn * Sprite.SIZE, (posRow + bombSize) * Sprite.SIZE, bottomSprites));
            }
            else break;
        }

        for (int i = posRow - 1; i >= posRow - bombSize; i--) {
            if (game.staticEntities[i][posColumn] instanceof Brick) {
                ((Brick) game.staticEntities[i][posColumn]).isExploding = true;
                break;
            }
            if (game.staticEntities[i][posColumn] instanceof Item) {
                ((Item) game.staticEntities[i][posColumn]).isExploding = true;
                break;
            }
            if (game.staticEntities[i][posColumn] instanceof Bomb && !((Bomb) game.staticEntities[i][posColumn]).isExploding) {
                ((Bomb) game.staticEntities[i][posColumn]).explode(game);
                break;
            }
            if (game.staticEntities[i][posColumn] instanceof Grass
                    || game.staticEntities[i][posColumn] instanceof Portal){
                if (posRow - i < bombSize) {
                    flames.add(new Flame(posColumn * Sprite.SIZE,i * Sprite.SIZE,  verticalSprites));
                }
                else flames.add(new Flame(posColumn * Sprite.SIZE, (posRow - bombSize) * Sprite.SIZE, topSprites));
            }
            else break;
        }

    }

    public void exploding() {
        for (Enemy enemy : game.enemies) {
            for (Flame flame : flames) {
                Point enemyPos = Map.getPosition(enemy.getCenter().x, enemy.getCenter().y);
                Point flamePos = Map.getPosition(flame.getX(), flame.getY());
                if (enemyPos.x == flamePos.x && enemyPos.y == flamePos.y) {
                    enemy.die();
                }
            }
        }
        for (Flame flame : flames) {
            Point bomberPos = Map.getPosition(game.bomber.getCenter().x, game.bomber.getCenter().y);
            Point flamePos = Map.getPosition(flame.getX(), flame.getY());
            if (bomberPos.x == flamePos.x && bomberPos.y == flamePos.y) {
                game.bomber.die();
            }
        }
        times++;
        times %= explodeSpriteLoop;
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
        if (isExploding) {
            for (Flame flame : flames) {
                flame.draw(g);
            }
        }
        else super.draw(g);
    }
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
