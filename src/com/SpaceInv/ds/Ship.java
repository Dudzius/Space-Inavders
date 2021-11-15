package com.SpaceInv.ds;

import com.SpaceInv.Settings;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class Ship extends Sprite {

    public Ship() {
        initPlayer();
    }

    private void initPlayer() {

        ImageIcon ii = new ImageIcon("images/player.png");
        setImage(ii.getImage());

        setX(Settings.BOARD_WIDTH/2);
        setY(Settings.BOARD_HEIGHT-100);
    }

    public void act() {

        x += DirX;

        if (x <= 1) {
            x = 1;
        }
        if (x >= Settings.BOARD_WIDTH - 30) {
            x = Settings.BOARD_WIDTH - 30;
        }
    }

    public void keyPressed(KeyEvent key) {

        int move = key.getKeyCode();

        if (move == KeyEvent.VK_LEFT) {
            DirX = -Settings.PLAYER_SPEED;
        }

        if (move == KeyEvent.VK_RIGHT) {

            DirX = Settings.PLAYER_SPEED;
        }
    }

    public void keyReleased(KeyEvent key) {

        int move = key.getKeyCode();

        if (move == KeyEvent.VK_LEFT) {
            DirX = 0;
        }

        if (move == KeyEvent.VK_RIGHT) {
            DirX = 0;
        }
    }
}
