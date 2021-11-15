package com.SpaceInv.ds;

import java.awt.Image;

public class Sprite {
    private boolean visible;
    private Image image;
    private boolean exploding;

    int x;
    int y;
    int DirX;

    public Sprite() {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void die() {
        visible = false;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isExploding() {
        return this.exploding;
    }
}
