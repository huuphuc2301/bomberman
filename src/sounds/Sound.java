package sounds;

import javazoom.jl.player.Player;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;

public class Sound {
    public static void play(String path) {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }
    public static String explosion_path = "sounds/explosion.wav";
    public static String drop_bomb_path = "sounds/drop_bomb.wav";
    public static String item_path = "sounds/item.wav";
    public static String enemy_die_path = "sounds/enemy_die.wav";
    public static String bomber_die_path = "sounds/bomber_die.wav";
}
