package entities;

import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;

public class Bomber extends MovingEntity {
    public static final int SPEED = 2;
    public static Sprite[] bomberSprites = {
            Sprite.player_right,
            Sprite.player_right_1,
            Sprite.player_right_2,
            Sprite.player_left,
            Sprite.player_left_1,
            Sprite.player_left_2,
            Sprite.player_up,
            Sprite.player_up_1,
            Sprite.player_up_2,
            Sprite.player_down,
            Sprite.player_down_1,
            Sprite.player_down_2
        };

    public Bomber(int x, int y) {
        super(x, y, bomberSprites);
        this.setSpeed(SPEED);
    }

    @Override
    public void moveRight(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(x + getWidth() + 1, center.y);
        Point topPos = Map.getPosition(x + getWidth() + 1, y);
        Point bottomPos = Map.getPosition(x + getWidth() + 1, y + getHeight() - 1);
        if (!(game.staticEntities[centerPos.x][centerPos.y] instanceof Grass)) {
            super.moveRight(game);
            return;
        }
        if (game.staticEntities[topPos.x][centerPos.y] instanceof Grass
                 && game.staticEntities[bottomPos.x][centerPos.y] instanceof Grass) {
            super.moveRight(game);
            return;
        }
        int targetY = centerPos.x * Sprite.SIZE;
        if (!(game.staticEntities[topPos.x][centerPos.y] instanceof Grass)) {
            if (y + getSpeed() <= targetY) super.moveDown(game);
            else {
                setSpeed(targetY - y);
                super.moveDown(game);
                setSpeed(SPEED);
            }
        } else {
            if (y - getSpeed() >= targetY) super.moveUp(game);
            else {
                setSpeed(y - targetY);
                super.moveUp(game);
                setSpeed(SPEED);
            }
        }
    }

    @Override
    public void moveLeft(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(x - 1, center.y);
        Point topPos = Map.getPosition(x - 1, y);
        Point bottomPos = Map.getPosition(x - 1, y + getHeight() - 1);
        if (!(game.staticEntities[centerPos.x][centerPos.y] instanceof Grass)) {
            super.moveLeft(game);
            return;
        }
        if (game.staticEntities[topPos.x][centerPos.y] instanceof Grass
                && game.staticEntities[bottomPos.x][centerPos.y] instanceof Grass) {
            super.moveLeft(game);
            return;
        }
        int targetY = centerPos.x * Sprite.SIZE;
        if (!(game.staticEntities[topPos.x][centerPos.y] instanceof Grass)) {
            if (y + getSpeed() <= targetY) super.moveDown(game);
            else {
                setSpeed(targetY - y);
                super.moveDown(game);
                setSpeed(SPEED);
            }
        } else {
            if (y - getSpeed() >= targetY) super.moveUp(game);
            else {
                setSpeed(y - targetY);
                super.moveUp(game);
                setSpeed(SPEED);
            }
        }
    }

    @Override
    public void moveUp(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(center.x, y - 1);
        Point leftPos = Map.getPosition(x, y - 1);
        Point rightPos = Map.getPosition(x + getWidth() - 1, y - 1);
        if (!(game.staticEntities[centerPos.x][centerPos.y] instanceof Grass)) {
            super.moveUp(game);
            return;
        }
        if (game.staticEntities[centerPos.x][leftPos.y] instanceof Grass
                && game.staticEntities[centerPos.x][rightPos.y] instanceof Grass) {
            super.moveUp(game);
            return;
        }
        if (!(game.staticEntities[centerPos.x][leftPos.y] instanceof Grass)) {
            int targetX = centerPos.y * Sprite.SIZE;
            if (x + getSpeed() <= targetX) super.moveRight(game);
            else {
                setSpeed(targetX - x);
                super.moveRight(game);
                setSpeed(SPEED);
            }
        } else {
            int targetX = (centerPos.y + 1) * Sprite.SIZE - bomberSprites[6].getWidth();
            if (x - getSpeed() >= targetX) super.moveLeft(game);
            else {
                setSpeed(x - targetX);
                super.moveLeft(game);
                setSpeed(SPEED);
            }
        }
    }

    @Override
    public void moveDown(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(center.x, y + getHeight());
        Point leftPos = Map.getPosition(x, y + getHeight());
        Point rightPos = Map.getPosition(x + getWidth() - 1, y + getHeight());
        if (!(game.staticEntities[centerPos.x][centerPos.y] instanceof Grass)) {
            super.moveDown(game);
            return;
        }
        if (game.staticEntities[centerPos.x][leftPos.y] instanceof Grass
                && game.staticEntities[centerPos.x][rightPos.y] instanceof Grass) {
            super.moveDown(game);
            return;
        }
        if (!(game.staticEntities[centerPos.x][leftPos.y] instanceof Grass)) {
            int targetX = centerPos.y * Sprite.SIZE;
            if (x + getSpeed() <= targetX) super.moveRight(game);
            else {
                setSpeed(targetX - x);
                super.moveRight(game);
                setSpeed(SPEED);
            }
        } else {
            int targetX = (centerPos.y + 1) * Sprite.SIZE - bomberSprites[9].getWidth();
            if (x - getSpeed() >= targetX) super.moveLeft(game);
            else {
                setSpeed(x - targetX);
                super.moveLeft(game);
                setSpeed(SPEED);
            }
        }
    }

}
