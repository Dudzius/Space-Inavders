package com.SpaceInv.ds;

import java.awt.Image;

public class Sprite {
    private boolean visible;
    private Image image;
    private boolean exploding;

    int cordX;
    int cordY;
    int DirX;

    public Sprite() {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void disappear() {
        visible = false;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    public void setCordY(int cordY) {
        this.cordY = cordY;
    }

    public int getCordY() {
        return cordY;
    }

    public int getCordX() {
        return cordX;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isExploding() {
        return this.exploding;
    }
}
