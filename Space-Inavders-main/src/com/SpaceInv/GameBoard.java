package com.SpaceInv;

import com.SpaceInv.ds.Alien;
import com.SpaceInv.ds.Ship;
import com.SpaceInv.ds.Shot;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {
    private List<Alien> aliens;
    private Ship ship;
    private Shot shot;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    private Timer timer;


    public GameBoard() {
        initBoard();
        gameInit();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        timer = new Timer(Settings.DELAY, new GameCycle());
        timer.start();
    }


    private void gameInit() {
        aliens = new ArrayList<>();

        for (int i = 0; i < Settings.ALIEN_ROWS; i++) {
            for (int j = 0; j < Settings.ALIEN_COL; j++) {
                Alien alien = new Alien(Settings.ALIEN_INIT_X + Settings.ALIEN_WITH_GAP * j, Settings.ALIEN_INIT_Y + Settings.ALIEN_WITH_GAP * i);
                aliens.add(alien);
            }
        }
        ship = new Ship();
        shot = new Shot();
    }

    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getCordX(), alien.getCordY(), this);
            }

            if (alien.isExploding()) {alien.disappear();}
        }
    }

    private void drawPlayer(Graphics g) {
        if (ship.isVisible()) {
            g.drawImage(ship.getImage(), ship.getCordX(), ship.getCordY(), this);
        }

        if (ship.isExploding()) {
            ship.disappear();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getCordX(), shot.getCordY(), this);
        }
    }

    private void drawBombing(Graphics g) {

        for (Alien alien : aliens) {

            Alien.Bomb alienBomb = alien.getBomb();

            if (!alienBomb.isDestroyed()) {
                g.drawImage(alienBomb.getImage(), alienBomb.getCordX(), alienBomb.getCordY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DrawingElements(g);
    }

    private void DrawingElements(Graphics g) {
        g.setColor(Color.green);
        g.drawLine(0, Settings.GROUND,Settings.BOARD_WIDTH, Settings.GROUND);

        if (inGame) {
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);

        } else {
            timer.stop();
            gameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT);

        g.setColor(new Color(0, 58, 108));
        g.fillRect(50, Settings.BOARD_WIDTH / 2 - 30, Settings.BOARD_WIDTH - 100, 50);

        g.setColor(Color.white);
        g.drawRect(50, Settings.BOARD_WIDTH / 2 - 30, Settings.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (Settings.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2, Settings.BOARD_WIDTH / 2);
    }

    private void update() {

        if (deaths == Settings.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        ship.act();

        // shooting ------------------------------
        int shotCordX = shot.getCordX();
        int shotCordY = shot.getCordY();

        for (Alien alien : aliens) {
            int alienCordX = alien.getCordX();
            int alienCordY = alien.getCordY();

            if ((alien.isVisible() && shot.isVisible())
                    && (shotCordX >= (alienCordX)
                    && shotCordX <= (alienCordX + Settings.ALIEN_WIDTH)
                    && shotCordY >= (alienCordY)
                    && shotCordY <= (alienCordY + Settings.ALIEN_HEIGHT))) {

                ImageIcon imageIcon = new ImageIcon("Space-Inavders-main/src/images/boom.png");
                alien.setImage(imageIcon.getImage());
                alien.setExploding(true);
                deaths++;
                shot.disappear();
            }
        }
        int y = shot.getCordY();
        y -= Settings.PLAYER_SHOT_SPEED;

        if (y < 0) {
            shot.disappear();
        } else {
            shot.setCordY(y);
        }

        // aliens ------------------------------

        for (Alien alien : aliens) {
            int cordX = alien.getCordX();

            if (cordX >= Settings.BOARD_WIDTH - Settings.BORDER_RIGHT && direction != -1) {  //right wall
                direction = -1;
                Iterator<Alien> alienIterator1 = aliens.iterator();

                while (alienIterator1.hasNext()) {
                    Alien alien1 = alienIterator1.next();
                    alien1.setCordY(alien1.getCordY() + Settings.GO_DOWN);
                }
            }

            if (cordX <= Settings.BORDER_LEFT && direction != 1) {              //left wall
                direction = 1;
                Iterator<Alien> alienIterator2 = aliens.iterator();

                while (alienIterator2.hasNext()) {
                    Alien alien2 = alienIterator2.next();
                    alien2.setCordY(alien2.getCordY() + Settings.GO_DOWN);
                }
            }
        }

        Iterator<Alien> alienIterator = aliens.iterator();

        while (alienIterator.hasNext()) {
            Alien alien = alienIterator.next();

            int cordY = alien.getCordY();

            if (cordY > Settings.GROUND - Settings.ALIEN_HEIGHT) {
                inGame = false;
                message = "Invasion!";
            }

            alien.act(direction);
        }

        // alien bombs ------------------------------
        Random generator = new Random();

        for (Alien alien : aliens) {

            int shootBomb = generator.nextInt(Settings.RANDOM_GENERATE);
            Alien.Bomb bomb = alien.getBomb();

            if (shootBomb == Settings.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setCordX(alien.getCordX());
                bomb.setCordY(alien.getCordY());
            }

            int bombX = bomb.getCordX();
            int bombY = bomb.getCordY();
            int playerX = ship.getCordX();
            int playerY = ship.getCordY();

            if (!bomb.isDestroyed()
                    && (bombX >= (playerX)
                    && bombX <= (playerX + Settings.PLAYER_WIDTH)
                    && bombY >= (playerY)
                    && bombY <= (playerY + Settings.PLAYER_HEIGHT))) {

                var ii = new ImageIcon("Space-Inavders-main/src/images/boom.png");
                ship.setImage(ii.getImage());
                ship.setExploding(true);
                bomb.setDestroyed(true);
            }

            bomb.setCordY(bomb.getCordY() + 1);

            if (bomb.getCordY() >= Settings.GROUND - Settings.BOMB_HEIGHT) {
                bomb.setDestroyed(true);
            }
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent key) {
            ship.keyReleased(key);
        }

        @Override
        public void keyPressed(KeyEvent key) {
            ship.keyPressed(key);

            int x = ship.getCordX();
            int y = ship.getCordY();

            int shoot = key.getKeyCode();

            if (shoot == KeyEvent.VK_SPACE && !shot.isVisible()) {
                shot = new Shot(x, y);
            }
        }
    }
}
