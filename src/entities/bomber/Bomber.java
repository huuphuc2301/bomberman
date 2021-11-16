package entities.bomber;

import entities.Grass;
import entities.MovingEntity;
import entities.enemy.Enemy;
import entities.item.Item;
import graphics.Sprite;
import main.BombermanGame;
import main.Map;

import java.awt.*;

public class Bomber extends MovingEntity {
    public static final int SPEED = 1;
    private int bombSize = 1;
    private int maxBomb = 1;
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
    public static Sprite[] deadSprites = {
            Sprite.player_dead1,
            Sprite.player_dead2,
            Sprite.player_dead3,
            Sprite.transparent
    };

    public int getBombSize() {
        return bombSize;
    }

    public void setBombSize(int bomb_size) {
        this.bombSize = bomb_size;
    }

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }

    public Bomber(int x, int y) {
        super(x, y, bomberSprites, deadSprites);
        this.setSpeed(SPEED);
    }

    public boolean isFree(Point point, BombermanGame game) {
        Point pos = Map.getPosition(point.x, point.y);
        return (!Map.isBlock(game.staticEntities[pos.x][pos.y]));
    }

    @Override
    public void moveRight(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(x + getWidth() , center.y);
        Point topPos = Map.getPosition(x + getWidth() , y);
        Point bottomPos = Map.getPosition(x + getWidth() , y + getHeight() - 1);
        if (Map.isBlock(game.staticEntities[centerPos.x][centerPos.y])) {
            super.moveRight(game);
            return;
        }
        if (!Map.isBlock(game.staticEntities[topPos.x][centerPos.y])
                 && !Map.isBlock(game.staticEntities[bottomPos.x][centerPos.y])) {
            super.moveRight(game);
            return;
        }
        int targetY = centerPos.x * Sprite.SIZE;
        if (Map.isBlock(game.staticEntities[topPos.x][centerPos.y])) {
            if (y + getSpeed() <= targetY) super.moveDown(game);
            else {
                setY(targetY);
            }
        } else {
            if (y - getSpeed() >= targetY) super.moveUp(game);
            else {
                setY(targetY);
            }
        }
    }

    @Override
    public void moveLeft(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(x - 1, center.y);
        Point topPos = Map.getPosition(x - 1, y);
        Point bottomPos = Map.getPosition(x - 1, y + getHeight() - 1);
        if (Map.isBlock(game.staticEntities[centerPos.x][centerPos.y])) {
            super.moveLeft(game);
            return;
        }
        if (!Map.isBlock(game.staticEntities[topPos.x][centerPos.y])
                && !Map.isBlock(game.staticEntities[bottomPos.x][centerPos.y])) {
            super.moveLeft(game);
            return;
        }
        int targetY = centerPos.x * Sprite.SIZE;
        if (Map.isBlock(game.staticEntities[topPos.x][centerPos.y])) {
            if (y + getSpeed() <= targetY) super.moveDown(game);
            else {
                setY(targetY);
            }
        } else {
            if (y - getSpeed() >= targetY) super.moveUp(game);
            else {
                setY(targetY);
            }
        }
    }

    @Override
    public void moveUp(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(center.x, y - 1);
        Point leftPos = Map.getPosition(x, y - 1);
        Point rightPos = Map.getPosition(x + getWidth() - 1, y - 1);
        if (Map.isBlock(game.staticEntities[centerPos.x][centerPos.y])) {
            super.moveUp(game);
            return;
        }
        if (!Map.isBlock(game.staticEntities[centerPos.x][leftPos.y])
                && !Map.isBlock(game.staticEntities[centerPos.x][rightPos.y])) {
            super.moveUp(game);
            return;
        }
        if (Map.isBlock(game.staticEntities[centerPos.x][leftPos.y])) {
            int targetX = centerPos.y * Sprite.SIZE;
            if (x + getSpeed() <= targetX) super.moveRight(game);
            else {
                setX(targetX);
            }
        } else {
            int targetX = (centerPos.y + 1) * Sprite.SIZE - bomberSprites[6].getWidth();
            if (x - getSpeed() >= targetX) super.moveLeft(game);
            else {
                setX(targetX);
            }
        }
    }

    @Override
    public void moveDown(BombermanGame game) {
        Point center = getCenter();
        Point centerPos = Map.getPosition(center.x, y + getHeight());
        Point leftPos = Map.getPosition(x, y + getHeight());
        Point rightPos = Map.getPosition(x + getWidth() - 1, y + getHeight());
        if (Map.isBlock(game.staticEntities[centerPos.x][centerPos.y])) {
            super.moveDown(game);
            return;
        }
        if (!Map.isBlock(game.staticEntities[centerPos.x][leftPos.y])
                && !Map.isBlock(game.staticEntities[centerPos.x][rightPos.y])) {
            super.moveDown(game);
            return;
        }
        if (Map.isBlock(game.staticEntities[centerPos.x][leftPos.y])) {
            int targetX = centerPos.y * Sprite.SIZE;
            if (x + getSpeed() <= targetX) super.moveRight(game);
            else {
                setX(targetX);
            }
        } else {
            int targetX = (centerPos.y + 1) * Sprite.SIZE - bomberSprites[9].getWidth();
            if (x - getSpeed() >= targetX) super.moveLeft(game);
            else {
                setX(targetX);
            }
        }
    }

    public void moveRightUp(BombermanGame game) {
        Point rightBottom = new Point(x + getWidth(), y + getHeight() - 1);
        Point rightCenter = new Point( x + getWidth(), getCenter().y);
        if (!isFree(rightBottom, game) && isFree(rightCenter, game)) {
            moveRight(game);
            return;
        }

        Point topLeft = new Point(x, y - 1);
        Point topCenter = new Point( getCenter().x, y - 1);
        if (!isFree(topLeft, game) && isFree(topCenter, game)) {
            moveUp(game);
            return;
        }
        boolean isMove = false;
        Point rightTop = new Point(x + getWidth() - 1, y);
        Point rightPos = Map.getPosition(rightTop.x + 1, rightTop.y);
        if (!Map.isBlock(game.staticEntities[rightPos.x][rightPos.y])) {
            moveRight(game);
            isMove = true;
        }
        rightTop = new Point(x + getWidth() - 1, y);
        Point topPos = Map.getPosition(rightTop.x,rightTop.y - 1);
        if (!Map.isBlock(game.staticEntities[topPos.x][topPos.y])) {
            moveUp(game);
            isMove = true;
        }
        if (!isMove) moveRight(game);
    }

    public void moveRightDown(BombermanGame game) {
        Point rightTop = new Point(x + getWidth(), y);
        Point rightCenter = new Point( x + getWidth(), getCenter().y);
        if (!isFree(rightTop, game) && isFree(rightCenter, game)) {
            moveRight(game);
            return;
        }

        Point bottomLeft = new Point(x, y + getHeight());
        Point bottomCenter = new Point( getCenter().x, y + getHeight());
        if (!isFree(bottomLeft, game) && isFree(bottomCenter, game)) {
            moveDown(game);
            return;
        }

        boolean isMove = false;
        Point rightBottom = new Point(x + getWidth() - 1, y + getHeight() - 1);
        Point rightPos = Map.getPosition(rightBottom.x + 1, rightBottom.y);
        if (!Map.isBlock(game.staticEntities[rightPos.x][rightPos.y])) {
            moveRight(game);
            isMove = true;
        }

        rightBottom = new Point(x + getWidth() - 1, y + getHeight() - 1);
        Point bottomPos = Map.getPosition(rightBottom.x,rightBottom.y + 1);
        if (!Map.isBlock(game.staticEntities[bottomPos.x][bottomPos.y])) {
            moveDown(game);
            isMove = true;
        }
        if (!isMove) moveLeft(game);
    }

    public void moveLeftUp(BombermanGame game) {
        Point leftBottom = new Point(x - 1, y + getHeight() - 1);
        Point leftCenter = new Point( x - 1, getCenter().y);
        if (!isFree(leftBottom, game) && isFree(leftCenter, game)) {
            moveLeft(game);
            return;
        }

        Point topRight = new Point(x + getWidth() - 1, y - 1);
        Point topCenter = new Point( getCenter().x, y - 1);
        if (!isFree(topRight, game) && isFree(topCenter, game)) {
            moveUp(game);
            return;
        }

        Point leftTop = new Point(x, y);
        Point leftPos = Map.getPosition(leftTop.x - 1, leftTop.y);

        boolean isMove = false;
        if (!Map.isBlock(game.staticEntities[leftPos.x][leftPos.y])) {
            moveLeft(game);
            isMove = true;
        }

        leftTop = new Point(x, y);
        Point topPos = Map.getPosition(leftTop.x,leftTop.y - 1);
        if (!Map.isBlock(game.staticEntities[topPos.x][topPos.y])) {
            moveUp(game);
            isMove = true;
        }
        if (!isMove) moveLeft(game);
    }

    public void moveLeftDown(BombermanGame game) {
        Point leftTop = new Point(x - 1, y);
        Point leftCenter = new Point( x - 1, getCenter().y);
        if (!isFree(leftTop, game) && isFree(leftCenter, game)) {
            moveLeft(game);
            return;
        }

        Point bottomRight = new Point(x + getWidth() - 1, y + getHeight());
        Point bottomCenter = new Point( getCenter().x, y + getHeight());
        if (!isFree(bottomRight, game) && isFree(bottomCenter, game)) {
            moveDown(game);
            return;
        }

        boolean isMove = false;
        Point leftBottom = new Point(x, y + getHeight() - 1);
        Point leftPos = Map.getPosition(leftBottom.x - 1, leftBottom.y);
        if (!Map.isBlock(game.staticEntities[leftPos.x][leftPos.y])) {
            moveLeft(game);
            isMove = true;
        }
        leftBottom = new Point(x, y + getHeight() - 1);
        Point bottomPos = Map.getPosition(leftBottom.x,leftBottom.y + 1);
        if (!Map.isBlock(game.staticEntities[bottomPos.x][bottomPos.y])) {
            moveDown(game);
            isMove = true;
        }
        if (!isMove) moveLeft(game);
    }

    public void checkEnemyConflict(BombermanGame game) {
        for (Enemy enemy : game.enemies) {
            if (!enemy.isDying && Math.abs(game.bomber.x - enemy.getX()) <= Sprite.SIZE - 10
                && Math.abs(game.bomber.y - enemy.getY()) <= Sprite.SIZE - 10) {
                //game.bomber.die();
            }
        }
    }

    public void checkItemConflic(BombermanGame game) {
        Point pos = Map.getPosition(game.bomber.getCenter().x, game.bomber.getCenter().y);
        if (game.staticEntities[pos.x][pos.y] instanceof Item) {
            ((Item) game.staticEntities[pos.x][pos.y]).upgrade(game.bomber);
        }
    }

    @Override
    public void move(BombermanGame game) {
        if (isDying) {
            dying();
            return;
        }
        int sum = 0;
        if (left) sum++; if (right) sum++; if (up) sum++; if (down) sum++;
        if (sum == 2 && up && right) {
            moveRightUp(game);
            checkEnemyConflict(game);
            checkItemConflic(game);
            return;
        }
        if (sum == 2 && down && right) {
            moveRightDown(game);
            checkEnemyConflict(game);
            checkItemConflic(game);
            return;
        }
        if (sum == 2 && up && left) {
            moveLeftUp(game);
            checkEnemyConflict(game);
            checkItemConflic(game);
            return;
        }
        if (sum == 2 && down && left) {
            moveLeftDown(game);
            checkEnemyConflict(game);
            checkItemConflic(game);
            return;
        }
        if (left) moveLeft(game);
        if (right) moveRight(game);
        if (up) moveUp(game);
        if (down) moveDown(game);
        checkEnemyConflict(game);
        checkItemConflic(game);
    }

}
