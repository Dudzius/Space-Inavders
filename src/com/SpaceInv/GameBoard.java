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
    private Dimension d;
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
        d = new Dimension(Settings.BOARD_WIDTH, Settings.BOARD_HEIGHT);
        setBackground(Color.black);

        timer = new Timer(Settings.DELAY, new GameCycle());
        timer.start();
    }


    private void gameInit() {
        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                var alien = new Alien(Settings.ALIEN_INIT_X + 20 * j,
                        Settings.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }

        ship = new Ship();
        shot = new Shot();
    }

    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isExploding()) {
                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {
        if (ship.isVisible()) {
            g.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
        }

        if (ship.isExploding()) {

            ship.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Alien.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
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
        g.drawLine(0, Settings.GROUND,
                Settings.BOARD_WIDTH, Settings.GROUND);

        if (inGame) {
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);

        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
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
        g.drawString(message, (Settings.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                Settings.BOARD_WIDTH / 2);
    }

    private void update() {

        if (deaths == Settings.NUMBER_OF_ALIENS_TO_DESTROY) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        ship.act();

        // shoting
        if (shot.isVisible()) {
            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Settings.ALIEN_WIDTH)     // X - row
                            && shotY >= (alienY)                           // Y - col
                            && shotY <= (alienY + Settings.ALIEN_HEIGHT))
                    {

                        ImageIcon ii = new ImageIcon("images/boom.png");
                        alien.setImage(ii.getImage());
                        alien.setExploding(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= Settings.PLAYER_SHOT_SPEED;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (Alien alien : aliens) {
            int x = alien.getX();

            if (x >= Settings.BOARD_WIDTH - Settings.BORDER_RIGHT && direction != -1) {  //right wall

                direction = -1;

                Iterator<Alien> i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = i1.next();
                    a2.setY(a2.getY() + Settings.GO_DOWN);
                }
            }

            if (x <= Settings.BORDER_LEFT && direction != 1) {              //left wall
                direction = 1;

                Iterator<Alien> i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = i2.next();
                    a.setY(a.getY() + Settings.GO_DOWN);
                }
            }
        }

        Iterator<Alien> it = aliens.iterator();

        while (it.hasNext()) {

            Alien alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Settings.GROUND - Settings.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);                       // moving
            }
        }

        // bombs
        var generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(85);  //random num up to 85
            Alien.Bomb bomb = alien.getBomb();

            if (shot == Settings.CHANCE && alien.isVisible() && bomb.isDestroyed()) {  // if rand num == CHANCE -> shoot

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = ship.getX();
            int playerY = ship.getY();

            if (ship.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Settings.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Settings.PLAYER_HEIGHT)) {

                    var ii = new ImageIcon("images/boom.png");
                    ship.setImage(ii.getImage());
                    ship.setExploding(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= Settings.GROUND - Settings.BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
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

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent key) {
            ship.keyReleased(key);
        }

        @Override
        public void keyPressed(KeyEvent key) {
            ship.keyPressed(key);

            int x = ship.getX();
            int y = ship.getY();

            int shoot = key.getKeyCode();

            if (shoot == KeyEvent.VK_SPACE) {
                if (inGame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}
