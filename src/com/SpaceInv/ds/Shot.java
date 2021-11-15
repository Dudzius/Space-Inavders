package com.SpaceInv.ds;

import javax.swing.ImageIcon;

public class Shot extends Sprite {
    public Shot() {
    }

    public Shot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        var ii = new ImageIcon("images/shot.png");
        setImage(ii.getImage());

        setX(x+6);
        setY(y+1);
    }
}
