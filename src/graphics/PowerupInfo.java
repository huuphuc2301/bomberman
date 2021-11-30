package graphics;

import java.awt.*;

public class PowerupInfo {
    private int maxBomb;
    private int bombSize;
    private int speed;
    private int x = 647;
    private int y = 443;

    public PowerupInfo(int maxBomb, int bombSize, int speed) {
        this.maxBomb = maxBomb;
        this.bombSize = bombSize;
        this.speed = speed;
    }

    public void draw(Graphics g) {
        g.setFont(new Font("Calibri", Font.BOLD,16));
        g.setColor(new Color(116, 253, 226));
        g.drawString(String.valueOf(maxBomb), x, y);
        g.drawString(String.valueOf(bombSize), x + 37, y);
        g.drawString(String.valueOf(speed), x + 74, y);
    }
}
