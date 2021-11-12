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
        this.width = width * SCALE;
        this.height = height * SCALE;
        try {
            image = ImageIO.read(new File(path));
            image.getScaledInstance(SIZE, SIZE, image.SCALE_SMOOTH);

        } catch (IOException e) {
            System.out.println("Error read image " + path);
        }
    }

    /*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
    public static Sprite grass = new Sprite("images\\grass.png", 16, 16);
    public static Sprite brick = new Sprite("images\\brick.png", 16, 16);
    public static Sprite wall = new Sprite("images\\wall.png", 16, 16);
    public static Sprite portal = new Sprite("images\\portal.png", 16, 16);

    /*
	|--------------------------------------------------------------------------
	| Bomber Sprites
	|--------------------------------------------------------------------------
	 */

    public static Sprite player_up = new Sprite("images\\player_up.png", 12, 16);
    public static Sprite player_down = new Sprite("images\\player_down.png", 12, 16);
    public static Sprite player_left = new Sprite("images\\player_left.png", 12, 16);
    public static Sprite player_right = new Sprite("images\\player_right.png", 12, 16);

    public static Sprite player_up_1 = new Sprite("images\\player_up_1.png", 12, 16);
    public static Sprite player_up_2 = new Sprite("images\\player_up_2.png", 12, 16);

    public static Sprite player_down_1 = new Sprite("images\\player_down_1.png", 12, 16);
    public static Sprite player_down_2 = new Sprite("images\\player_down_2.png", 12, 16);

    public static Sprite player_left_1 = new Sprite("images\\player_left_1.png", 12, 16);
    public static Sprite player_left_2 = new Sprite("images\\player_left_2.png", 12, 16);

    public static Sprite player_right_1 = new Sprite("images\\player_right_1.png", 12, 16);
    public static Sprite player_right_2 = new Sprite("images\\player_right_2.png", 12, 16);

    public static Sprite player_dead1 = new Sprite("images\\player_dead1.png", 14, 16);
    public static Sprite player_dead2 = new Sprite("images\\player_dead2.png", 13, 16);
    public static Sprite player_dead3 = new Sprite("images\\player_dead3.png", 16, 16);

    /*
   |--------------------------------------------------------------------------
   | Enemies
   |--------------------------------------------------------------------------
    */
    //ALL
    public static Sprite mob_dead1 = new Sprite("images\\mob_dead1.png", 16, 16);
    public static Sprite mob_dead2 = new Sprite("images\\mob_dead2.png", 16, 16);
    public static Sprite mob_dead3 = new Sprite("images\\mob_dead3.png", 16, 16);

    //BALLOM
    public static Sprite balloom_left1 = new Sprite("images\\balloom_left1.png", 16, 16);
    public static Sprite balloom_left2 = new Sprite("images\\balloom_left2.png", 16, 16);
    public static Sprite balloom_left3 = new Sprite("images\\balloom_left3.png", 16, 16);

    public static Sprite balloom_right1 = new Sprite("images\\balloom_right1.png", 16, 16);
    public static Sprite balloom_right2 = new Sprite("images\\balloom_right2.png", 16, 16);
    public static Sprite balloom_right3 = new Sprite("images\\balloom_right3.png", 16, 16);

    public static Sprite balloom_dead = new Sprite("images\\balloom_dead.png", 16, 16);

    //ONEAL
    public static Sprite oneal_left1 = new Sprite("images\\oneal_left1.png", 16, 16);
    public static Sprite oneal_left2 = new Sprite("images\\oneal_left2.png", 16, 16);
    public static Sprite oneal_left3 = new Sprite("images\\oneal_left3.png", 16, 16);

    public static Sprite oneal_right1 = new Sprite("images\\oneal_right1.png", 16, 16);
    public static Sprite oneal_right2 = new Sprite("images\\oneal_right2.png", 16, 16);
    public static Sprite oneal_right3 = new Sprite("images\\oneal_right3.png", 16, 16);

    public static Sprite oneal_dead = new Sprite("images\\oneal_dead.png", 16, 16);

    //Doll
    public static Sprite doll_left1 = new Sprite("images\\doll_left1.png", 16, 16);
    public static Sprite doll_left2 = new Sprite("images\\doll_left2.png", 16, 16);
    public static Sprite doll_left3 = new Sprite("images\\doll_left3.png", 16, 16);

    public static Sprite doll_right1 = new Sprite("images\\doll_right1.png", 16, 16);
    public static Sprite doll_right2 = new Sprite("images\\doll_right2.png", 16, 16);
    public static Sprite doll_right3 = new Sprite("images\\doll_right3.png", 16, 16);

    public static Sprite doll_dead = new Sprite("images\\doll_dead.png", 16, 16);

    //Minvo
    public static Sprite minvo_left1 = new Sprite("images\\minvo_left1.png", 16, 16);
    public static Sprite minvo_left2 = new Sprite("images\\minvo_left2.png", 16, 16);
    public static Sprite minvo_left3 = new Sprite("images\\minvo_left3.png", 16, 16);

    public static Sprite minvo_right1 = new Sprite("images\\minvo_right1.png", 16, 16);
    public static Sprite minvo_right2 = new Sprite("images\\minvo_right2.png", 16, 16);
    public static Sprite minvo_right3 = new Sprite("images\\minvo_right3.png", 16, 16);

    public static Sprite minvo_dead = new Sprite("images\\minvo_dead.png", 16, 16);

    //Kondoria
    public static Sprite kondoria_left1 = new Sprite("images\\kondoria_left1.png", 16, 16);
    public static Sprite kondoria_left2 = new Sprite("images\\kondoria_left2.png", 16, 16);
    public static Sprite kondoria_left3 = new Sprite("images\\kondoria_left3.png", 16, 16);

    public static Sprite kondoria_right1 = new Sprite("images\\kondoria_right1.png", 16, 16);
    public static Sprite kondoria_right2 = new Sprite("images\\kondoria_right2.png", 16, 16);
    public static Sprite kondoria_right3 = new Sprite("images\\kondoria_right3.png", 16, 16);

    public static Sprite kondoria_dead = new Sprite("images\\kondoria_dead.png", 16, 16);
    /*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
    public static Sprite bomb = new Sprite("images\\bomb.png", 16, 16);;
    public static Sprite bomb_1 = new Sprite("images\\bomb_1.png", 16, 16);;
    public static Sprite bomb_2 = new Sprite("images\\bomb_2.png", 16, 16);;


    /*
	|--------------------------------------------------------------------------
	| FlameSegment Sprites
	|--------------------------------------------------------------------------
	 */
    public static Sprite bomb_exploded = new Sprite("images\\bomb_exploded.png", 16, 16);
    public static Sprite bomb_exploded1 = new Sprite("images\\bomb_exploded1.png", 16, 16);
    public static Sprite bomb_exploded2 = new Sprite("images\\bomb_exploded2.png", 16, 16);

    public static Sprite explosion_vertical = new Sprite("images\\explosion_vertical.png", 16, 16);
    public static Sprite explosion_vertical1 = new Sprite("images\\explosion_vertical1.png", 16, 16);
    public static Sprite explosion_vertical2 = new Sprite("images\\explosion_vertical2.png", 16, 16);

    public static Sprite explosion_horizontal = new Sprite("images\\explosion_horizontal.png", 16, 16);
    public static Sprite explosion_horizontal1 = new Sprite("images\\explosion_horizontal1.png", 16, 16);
    public static Sprite explosion_horizontal2 = new Sprite("images\\explosion_horizontal2.png", 16, 16);

    public static Sprite explosion_horizontal_left_last = new Sprite("images\\explosion_horizontal_left_last.png", 16, 16);
    public static Sprite explosion_horizontal_left_last1 = new Sprite("images\\explosion_horizontal_left_last1.png", 16, 16);
    public static Sprite explosion_horizontal_left_last2 = new Sprite("images\\explosion_horizontal_left_last2.png", 16, 16);

    public static Sprite explosion_horizontal_right_last = new Sprite("images\\explosion_horizontal_right_last.png", 16, 16);
    public static Sprite explosion_horizontal_right_last1 = new Sprite("images\\explosion_horizontal_right_last1.png", 16, 16);
    public static Sprite explosion_horizontal_right_last2 = new Sprite("images\\explosion_horizontal_right_last2.png", 16, 16);

    public static Sprite explosion_vertical_top_last = new Sprite("images\\explosion_vertical_top_last.png", 16, 16);
    public static Sprite explosion_vertical_top_last1 = new Sprite("images\\explosion_vertical_top_last1.png", 16, 16);
    public static Sprite explosion_vertical_top_last2 = new Sprite("images\\explosion_vertical_top_last2.png", 16, 16);

    public static Sprite explosion_vertical_down_last = new Sprite("images\\explosion_vertical_down_last.png", 16, 16);
    public static Sprite explosion_vertical_down_last1 = new Sprite("images\\explosion_vertical_down_last1.png", 16, 16);
    public static Sprite explosion_vertical_down_last2 = new Sprite("images\\explosion_vertical_down_last2.png", 16, 16);

    /*
	|--------------------------------------------------------------------------
	| Brick FlameSegment
	|--------------------------------------------------------------------------
	 */
    public static Sprite brick_exploded = new Sprite("images\\brick_exploded.png", 16, 16);
    public static Sprite brick_exploded1 = new Sprite("images\\brick_exploded1.png", 16, 16);
    public static Sprite brick_exploded2 = new Sprite("images\\brick_exploded2.png", 16, 16);

    /*
    |--------------------------------------------------------------------------
    | Powerups
    |--------------------------------------------------------------------------
     */
    public static Sprite powerup_bombs = new Sprite("images\\powerup_bombs.png", 16, 16);
    public static Sprite powerup_flames = new Sprite("images\\powerup_flames.png", 16, 16);
    public static Sprite powerup_speed = new Sprite("images\\powerup_speed.png", 16, 16);
    public static Sprite powerup_wallpass = new Sprite("images\\powerup_wallpass.png", 16, 16);
    public static Sprite powerup_detonator = new Sprite("images\\powerup_detonator.png", 16, 16);
    public static Sprite powerup_bombpass = new Sprite("images\\powerup_bombpass.png", 16, 16);
    public static Sprite powerup_flamepass = new Sprite("images\\powerup_flamepass.png", 16, 16);
}
