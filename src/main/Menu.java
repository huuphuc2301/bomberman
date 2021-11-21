package main;

import graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu extends JPanel {
    private int indexOfItem = 0;
    private int numberOfItem = 2;
    private BufferedImage scene = new BufferedImage(BombermanGame.WIDTH, BombermanGame.HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int selectorLocationX = 235;
    private int selectorLocationY = 234;
    public boolean isExit = false;

    public int draw() {
        Graphics g = scene.getGraphics();
        while (!isExit) {
            g.drawImage(Sprite.menu.getImage(), -55, -15, null);
            g.drawImage(Sprite.menuSelector.getImage(), selectorLocationX, selectorLocationY + 81 * indexOfItem, null);
            this.getGraphics().drawImage(scene, 0, 0, null);
        }
        return indexOfItem;
    }

    public void addListener(BombermanGame game) {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    indexOfItem--;
                    if (indexOfItem < 0) indexOfItem = numberOfItem - 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    indexOfItem++;
                    if (indexOfItem >= numberOfItem) indexOfItem = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (indexOfItem == 0) {
                        isExit = true;
                        System.out.println(1);
                    }

                }
            }
        });
    }
}
