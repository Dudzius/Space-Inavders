package com.SpaceInv.ds;

import javax.swing.ImageIcon;

public class Alien extends Sprite {
    private Bomb bomb;


    public Alien(int cordX, int cordY) {
        initAlien(cordX, cordY);
    }

    private void initAlien(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;

        bomb = new Bomb(cordX, cordY);

        ImageIcon ii = new ImageIcon("Space-Inavders-main/src/images/alien.png");
        setImage(ii.getImage());

    }

    public void act(int direction) {
        this.cordX += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public class Bomb extends Sprite {
        private boolean destroyed;

        public Bomb(int cordX, int cordY) {
            initBomb(cordX, cordY);
        }

        private void initBomb(int cordX, int cordY) {
            setDestroyed(true);

            this.cordX = cordX;
            this.cordY = cordY;

            ImageIcon imageIcon = new ImageIcon("Space-Inavders-main/src/images/bomb.png");
            setImage(imageIcon.getImage());
        }

        public void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {
            return destroyed;
        }

    }
}
