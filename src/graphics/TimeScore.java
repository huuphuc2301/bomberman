package graphics;

import java.awt.*;

public class TimeScore {
    private int x;
    private int y = 462;
    private int value;

    public TimeScore(int value) {
        this.value = value;
        int temp = value;
        int count = 0;
        while (temp > 0) {
            count++;
            temp /= 10;
        }
        count = Math.max(count, 1);
        x = 922 - count * 14;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void draw(Graphics g) {
        g.setFont(new Font("Calibri", Font.BOLD,30));
        g.setColor(Color.white);
        g.drawString(String.valueOf(value), x, y);
    }
}
