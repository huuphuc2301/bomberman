package entities;

import graphics.Sprite;

import java.awt.*;

public class Bomber extends MovingEntity {
    public static final int SPEED = 3;
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


}
