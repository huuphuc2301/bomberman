package entities;

import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;

public class Bomber extends MovingEntity {
    public static final int SPEED = 1;
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
        Point centerPos = Map.getPosition(x + getWidth() , center.y);
        Point topPos = Map.getPosition(x + getWidth() , y);
        Point bottomPos = Map.getPosition(x + getWidth() , y + getHeight() - 1);
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

    public void moveRightUp(BombermanGame game) {
        Point rightTop = new Point(x + getWidth() - 1, y);
        Point topPos = Map.getPosition(rightTop.x,rightTop.y - 1);
        if (game.staticEntities[topPos.x][topPos.y] instanceof Grass) {
            moveUp(game);
        }
        rightTop = new Point(x + getWidth() - 1, y);
        Point rightPos = Map.getPosition(rightTop.x + 1, rightTop.y);
        if (game.staticEntities[rightPos.x][rightPos.y] instanceof Grass) {
            moveRight(game);
        }
    }

    public void moveRightDown(BombermanGame game) {
        Point rightBottom = new Point(x + getWidth() - 1, y + getHeight() - 1);
        Point bottomPos = Map.getPosition(rightBottom.x,rightBottom.y + 1);
        if (game.staticEntities[bottomPos.x][bottomPos.y] instanceof Grass) {
            moveDown(game);
        }
        rightBottom = new Point(x + getWidth() - 1, y + getHeight() - 1);
        Point rightPos = Map.getPosition(rightBottom.x + 1, rightBottom.y);
        if (game.staticEntities[rightPos.x][rightPos.y] instanceof Grass) {
            moveRight(game);
        }
    }

    public void moveLeftUp(BombermanGame game) {
        Point leftTop = new Point(x, y);
        Point topPos = Map.getPosition(leftTop.x,leftTop.y - 1);
        if (game.staticEntities[topPos.x][topPos.y] instanceof Grass) {
            moveUp(game);
        }
        leftTop = new Point(x, y);
        Point leftPos = Map.getPosition(leftTop.x - 1, leftTop.y);
        if (game.staticEntities[leftPos.x][leftPos.y] instanceof Grass) {
            moveLeft(game);
        }
    }

    public void moveLeftDown(BombermanGame game) {
        Point leftBottom = new Point(x, y + getHeight() - 1);
        Point bottomPos = Map.getPosition(leftBottom.x,leftBottom.y + 1);
        if (game.staticEntities[bottomPos.x][bottomPos.y] instanceof Grass) {
            moveDown(game);
        }
        leftBottom = new Point(x, y + getHeight() - 1);
        Point leftPos = Map.getPosition(leftBottom.x - 1, leftBottom.y);
        if (game.staticEntities[leftPos.x][leftPos.y] instanceof Grass) {
            moveLeft(game);
        }
    }

    public void move(BombermanGame game) {
        int sum = 0;
        if (left) sum++; if (right) sum++; if (up) sum++; if (down) sum++;
        if (sum == 2 && up && right) {
            moveRightUp(game);
            return;
        }
        if (sum == 2 && down && right) {
            moveRightDown(game);
            return;
        }
        if (sum == 2 && up && left) {
            moveLeftUp(game);
            return;
        }
        if (sum == 2 && down && left) {
            moveLeftDown(game);
            return;
        }
        if (left) moveLeft(game);
        if (right) moveRight(game);
        if (up) moveUp(game);
        if (down) moveDown(game);
    }

}
