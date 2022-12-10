package com.SpaceInv;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        add(new GameBoard());

        setTitle("Space Invaders");
        setSize(Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            var ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}
