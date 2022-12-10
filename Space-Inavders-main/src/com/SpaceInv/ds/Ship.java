package com.SpaceInv.ds;

import com.SpaceInv.Settings;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class Ship extends Sprite {

    public Ship() {
        initPlayer();
    }

    private void initPlayer() {

        ImageIcon imageIcon = new ImageIcon("Space-Inavders-main/src/images/player.png");
        setImage(imageIcon.getImage());

        setCordX(Settings.BOARD_WIDTH/2);
        setCordY(Settings.BOARD_HEIGHT-100);
    }

    public void act() {

        cordX += DirX;

        if (cordX <= 1) {
            cordX = 1;
        }
        if (cordX >= Settings.BOARD_WIDTH - 30) {
            cordX = Settings.BOARD_WIDTH - 30;
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
