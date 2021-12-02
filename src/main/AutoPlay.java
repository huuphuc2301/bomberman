package main;

import entities.bomber.Bomb;
import entities.bomber.Bomber;
import entities.enemy.Enemy;
import graphics.Sprite;
import graphics.TimeScore;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class AutoPlay extends GameStage {
    public int targetRow = 3;
    public int targetCol = 2;
    int[][] enemiesDis;
    int[][] bomberDis;
    int[][] targetDis;

    public AutoPlay(int indexOfStage, Bomber bomber, long createTime, int timeScoreValue) {
        super(indexOfStage,bomber,createTime,timeScoreValue);
    }

    public int[][] distancesFromBomber() {
        int[] X = {0, 0, 1, -1};
        int[] Y = {1, -1, 0, 0};
        int[][] dis = new int[numRows][numColumns];
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++)
                dis[i][j] = 10000;
        Queue<Point> queue = new LinkedList<>();
        int bomberRow = bomber.getCenter().y/Sprite.SIZE;
        int bomberCol = bomber.getCenter().x/Sprite.SIZE;
        queue.add(new Point(bomberRow,bomberCol));
        dis[bomberRow][bomberCol] = 0;
        while (!queue.isEmpty()) {
            int row = queue.element().x;
            int col = queue.element().y;
            queue.remove();
            for (int i = 0; i < 4; i++) {
                int newrow = row + Y[i];
                int newcol = col + X[i];
                if (newcol < 0 || newrow < 0 || newrow >= numRows || newcol >= numColumns)
                    continue;
                if (!Map.isBlock(staticEntities[newrow][newcol]) && dis[newrow][newcol] == 10000) {
                    dis[newrow][newcol] = dis[row][col] + 1;
                    queue.add(new Point(newrow, newcol));
                    //////System.out.println("zzz "+newrow+" "+newcol+" "+dis[row][col]);
                }
            }
        }

        return dis;
    }

    public int[][] distancesFromEnemies() {
        int[] X = {0, 0, 1, -1};
        int[] Y = {1, -1, 0, 0};
        int[][] dis = new int[numRows][numColumns];
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++)
                dis[i][j] = 10000;
        Queue<Point> queue = new LinkedList<>();
        for (Enemy enemy: enemies) {
            int row = enemy.getCenter().y/Sprite.SIZE;
            int col = enemy.getCenter().x/Sprite.SIZE;
            dis[row][col]=0;
            queue.add(new Point(row, col));
        }
        while (!queue.isEmpty()) {
            int row = queue.element().x;
            int col = queue.element().y;
            queue.remove();
            for (int i = 0; i < 4; i++) {
                int newrow = row + Y[i];
                int newcol = col + X[i];
                if (newcol < 0 || newrow < 0 || newrow >= numRows || newcol >= numColumns)
                    continue;
                if (!Map.isBlock(staticEntities[newrow][newcol]) && dis[newrow][newcol] == 10000) {
                    dis[newrow][newcol] = dis[row][col] + 1;
                    queue.add(new Point(newrow, newcol));
                }
            }
        }
        return dis;
    }

    public boolean checkNotFlame(int bombRow, int bombCol, int row, int col) {
        if (row!=bombRow && col!=bombCol) return true;
        int range = 0;
        while (bombRow < row) {
            if (Map.isBlock(staticEntities[bombRow + 1][bombCol])) break;
            bombRow ++;
            range ++;
        }
        while (bombRow > row) {
            if (Map.isBlock(staticEntities[bombRow - 1][bombCol])) break;
            bombRow --;
            range ++;
        }
        while (bombCol < col) {
            if (Map.isBlock(staticEntities[bombRow][bombCol + 1])) break;
            bombCol ++;
            range ++;
        }
        while (bombCol > col) {
            if (Map.isBlock(staticEntities[bombRow][bombCol - 1])) break;
            bombCol --;
            range ++;
        }
        if (range > bomber.getBombSize()) return false;
        if (bombRow != row || bombCol != col) return false;
        return true;
    }
    public boolean checkPlaceBomb() {
        Point pos = Map.getPosition(bomber.getCenter().x, bomber.getCenter().y);
        for (int row = 0;row < numRows; row++)
            for (int col = 0;col < numColumns; col++) {
                if (bomberDis[row][col] <= 4 * bomber.getSpeed() && enemiesDis[row][col] - bomberDis[row][col] >=2) {
                    if (checkNotFlame(pos.x,pos.y,row,col)) return true;
                }
            }
        return false;
    }
    public void setTargetBomber() {
        Point pos = Map.getPosition(bomber.getCenter().x, bomber.getCenter().y);
        enemiesDis = distancesFromEnemies();
        bomberDis = distancesFromBomber();
        if (bombs.size() < 1 && checkPlaceBomb()) {
            Bomb newBomb = new Bomb(pos.y * Sprite.SIZE, pos.x * Sprite.SIZE, bomber.getBombSize(), this);
            bombs.add(newBomb);
            staticEntities[pos.x][pos.y] = newBomb;
        }
        for (int row = 0;row < numRows; row++)
            for (int col = 0;col < numColumns; col++) {
                if (bomberDis[row][col] <= 4 * bomber.getSpeed() && enemiesDis[row][col] - bomberDis[row][col] >=2) {
                    boolean ok = true;
                    ////System.out.println(row+" "+col);
                    for (Bomb bomb : bombs) {
                        Point bombPos = Map.getPosition(bomb.getCenter().x, bomb.getCenter().y);
                        ////System.out.println(checkNotFlame(bombPos.x, bombPos.y, row, col));
                        if (!checkNotFlame(bombPos.x, bombPos.y, row, col)) {
                            ok = false;break;
                        }
                    }
                    if (ok) {
                        targetRow = row;
                        targetCol = col;
                        ////System.out.println(targetRow+" "+targetCol);
                        return;
                    }
                }
            }
    }
    public String getDirection() {
        int bomberRow = bomber.getCenter().y/Sprite.SIZE;
        int bomberCol = bomber.getCenter().x/Sprite.SIZE;
        if (bomberRow == targetRow && bomberCol == targetCol) setTargetBomber();
        int[] X = {0, 0, 1, -1};
        int[] Y = {1, -1, 0, 0};
        int[][] dis = new int[numRows][numColumns];
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++)
                dis[i][j] = 10000;
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(targetRow, targetCol));
        dis[targetRow][targetCol] = 0;
        while (!queue.isEmpty()) {
            int row = queue.element().x;
            int col = queue.element().y;
            queue.remove();
            for (int i = 0; i < 4; i++) {
                int newrow = row + Y[i];
                int newcol = col + X[i];
                if (newcol < 0 || newrow < 0 || newrow >= numRows || newcol >= numColumns)
                    continue;
                if (newrow == bomberRow && newcol == bomberCol && dis[newrow][newcol] == 10000) {
                    dis[newrow][newcol] = dis[row][col] + 1;
                    queue.add(new Point(newrow, newcol));
                    continue;
                }
                if (!Map.isBlock(staticEntities[newrow][newcol]) && dis[newrow][newcol] == 10000) {
                    dis[newrow][newcol] = dis[row][col] + 1;
                    queue.add(new Point(newrow, newcol));
                }
                //////System.out.println(String.valueOf(newrow)+" "+String.valueOf(newcol)+" "+String.valueOf(dis[newrow][newcol]));
            }
        }
        //////System.out.println(bomberRow+" "+bomberCol+" "+targetRow+" "+targetCol+" "+dis[bomberRow][bomberCol]+" "+dis[bomberRow][bomberCol-1]);
        if (dis[bomberRow-1][bomberCol] + 1 == dis[bomberRow][bomberCol]) return "up";
        if (dis[bomberRow+1][bomberCol] + 1 == dis[bomberRow][bomberCol]) return "down";
        if (dis[bomberRow][bomberCol-1] + 1 == dis[bomberRow][bomberCol]) return "left";
        return "right";
    }
    public void autoMoveBomber() {
        bomber.up = bomber.down = bomber.left = bomber.right = false;
        int bomberRow = bomber.getCenter().x;
        int bomberCol = bomber.getCenter().y;
        if (bomberRow == targetRow && bomberCol == targetCol) setTargetBomber();
        String direction = getDirection();
        ////System.out.println(targetRow+" "+targetCol+" "+direction);
        //////System.out.println(dire
        ////System.out.println(bomber.up+" "+bomber.down+" "+bomber.left+" "+bomber.right);
        if (direction.equals("up"))  bomber.up = true;
        if (direction.equals("down"))  bomber.down = true;
        if (direction.equals("left"))  bomber.left = true;
        if (direction.equals("right"))  bomber.right = true;
        ////System.out.println(bomber.up+" "+bomber.down+" "+bomber.left+" "+bomber.right);
    }
    public boolean start() {
        Graphics g = mainScene.getGraphics();
        loadMap(mapPath);
        gameScene = new BufferedImage(numColumns * Sprite.SIZE,  numRows * Sprite.SIZE, BufferedImage.TYPE_INT_RGB);
        setTargetEnemies();
        runAndDraw();
        g.drawImage(Sprite.bottomOfStage.getImage(), 0, 416, null);
        timeScore = new TimeScore((timeScoreValue + (int) (System.currentTimeMillis() - createTime)) / 1000);
        timeScore.draw(g);


        long time1 = System.currentTimeMillis();
        g.setFont(new Font("Calibri", Font.BOLD, 50));
        g.setColor(new Color(35, 29, 116));
        g.drawString("STAGE " + (indexOfStage + 1), 400, 200);
        while (System.currentTimeMillis() - time1 < 1000) {
            isPaused = true;
            this.getGraphics().drawImage(mainScene, 0, 0, null);
        }
        isPaused = false;

        long secondTime = System.currentTimeMillis();
        int count = 0;
        int sleepTime = 0;
        long startTime = System.currentTimeMillis();
        while (!isWin && !bomber.isDead) {
            if (this.isPaused) {
                g.drawImage(Sprite.pause.getImage(), 345, 100, null);
                this.getGraphics().drawImage(mainScene, 0, 0, null);
                continue;
            }
            if (System.currentTimeMillis() - secondTime > 1000) {
                ////System.out.println("FPS " + count);
                count = 0;
                secondTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - startTime > 16 && sleepTime > 0) sleepTime--;
            else if (System.currentTimeMillis() - startTime < 13) sleepTime++;
            startTime = System.currentTimeMillis();
            count++;

            runAndDraw();
            autoMoveBomber();


            g.drawImage(Sprite.bottomOfStage.getImage(), 0, 416, null);
            timeScore = new TimeScore((timeScoreValue + (int) (System.currentTimeMillis() - createTime)) / 1000);
            timeScore.draw(g);
            this.getGraphics().drawImage(mainScene, 0, 0, null);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (bomber.isDead) {
            g.drawImage(Sprite.lose.getImage(), 340, 100, null);
            long time2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - time2 < 2000) {
                this.getGraphics().drawImage(mainScene, 0, 0, null);
            }
            return false;
        }
        if (indexOfStage == Map.MAP_PATHS.length - 1) {
            g.drawImage(Sprite.win.getImage(), 340, 90, null);
            g.setColor(new Color(35, 29, 116));
            g.setFont(new Font("Calibri", Font.BOLD, 40));
            g.drawString("YOUR TIME: " + timeScore.getValue(), 370, 255);
            long time2 = System.currentTimeMillis();
            while (System.currentTimeMillis() - time2 < 3000) {
                this.getGraphics().drawImage(mainScene, 0, 0, null);
            }
        }
        return true;
    }
}
