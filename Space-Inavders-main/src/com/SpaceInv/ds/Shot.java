package com.SpaceInv.ds;

import javax.swing.ImageIcon;

public class Shot extends Sprite {
    public Shot() {
    }

    public Shot(int cordX, int cordY) {
        initShot(cordX, cordY);
    }

    private void initShot(int cordX, int cordY) {
        var ii = new ImageIcon("Space-Inavders-main/src/images/shot.png");
        setImage(ii.getImage());

        setCordX(cordX+6);
        setCordY(cordY+1);
    }
}
