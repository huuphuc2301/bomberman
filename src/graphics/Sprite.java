package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class Sprite {
    public static final int SCALE = 2;
    public static final int SIZE = 16 * SCALE;
    private int width;
    private int height;
    private BufferedImage image;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Sprite(String path, int width, int height) {
        this.width = width;
        this.height = height;
        try {
            image = ImageIO.read(new File(path));
            image.getScaledInstance(SIZE, SIZE, image.SCALE_SMOOTH);

        } catch (IOException e) {
            System.out.println("Error read image");
        }
    }

    /*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
    public static Sprite grass = new Sprite("src\\images\\grass.png", 16 * SCALE, 16 * SCALE);
    public static Sprite brick = new Sprite("src\\images\\brick.png", 16 * SCALE, 16 * SCALE);
    public static Sprite wall = new Sprite("src\\images\\wall.png", 16 * SCALE, 16 * SCALE);
    public static Sprite portal = new Sprite("src\\images\\portal.png", 16 * SCALE, 16 * SCALE);

    /*
	|--------------------------------------------------------------------------
	| Bomber Sprites
	|--------------------------------------------------------------------------
	 */

    public static Sprite player_up = new Sprite("src\\images\\player_up.png", 12 * SCALE, 16 * SCALE);
    public static Sprite player_down = new Sprite("src\\images\\player_down.png", 12 * SCALE, 15 * SCALE);
    public static Sprite player_left = new Sprite("src\\images\\player_left.png", 10 * SCALE, 15 * SCALE);
    public static Sprite player_right = new Sprite("src\\images\\player_right.png", 10 * SCALE, 16 * SCALE);

    public static Sprite player_up_1 = new Sprite("src\\images\\player_up_1.png", 12 * SCALE, 16 * SCALE);
    public static Sprite player_up_2 = new Sprite("src\\images\\player_up_2.png", 12 * SCALE, 16 * SCALE);

    public static Sprite player_down_1 = new Sprite("src\\images\\player_down_1.png", 12 * SCALE, 15 * SCALE);
    public static Sprite player_down_2 = new Sprite("src\\images\\player_down_2.png", 12 * SCALE, 16 * SCALE);

    public static Sprite player_left_1 = new Sprite("src\\images\\player_left_1.png", 11 * SCALE, 16 * SCALE);
    public static Sprite player_left_2 = new Sprite("src\\images\\player_left_2.png", 12 * SCALE, 16 * SCALE);

    public static Sprite player_right_1 = new Sprite("src\\images\\player_right_1.png", 11 * SCALE, 16 * SCALE);
    public static Sprite player_right_2 = new Sprite("src\\images\\player_right_2.png", 12 * SCALE, 16 * SCALE);

    public static Sprite player_dead1 = new Sprite("src\\images\\player_dead1.png", 14 * SCALE, 16 * SCALE);
    public static Sprite player_dead2 = new Sprite("src\\images\\player_dead2.png", 13 * SCALE, 16 * SCALE);
    public static Sprite player_dead3 = new Sprite("src\\images\\player_dead3.png", 16 * SCALE, 16 * SCALE);
}
