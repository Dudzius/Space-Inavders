package com.SpaceInv.tests;
import com.SpaceInv.Settings;
import com.SpaceInv.ds.Alien;
import com.SpaceInv.ds.Shot;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class CollisionTest {
    private int deaths = 0;

    @Test
    public void testCollision() {
        int cordX = 100;
        int cordY = 200;
        Alien alien = new Alien(cordX,cordY);
        Shot shot = new Shot(cordX,cordY);

        checkCollisions(alien, shot);

        // test if alien was destroyed
        assertFalse(alien.isVisible());
        assertTrue(alien.isExploding());
        assertEquals(1, deaths);

        // test if shot disappeared
        assertFalse(shot.isVisible());
    }

    private void checkCollisions(Alien alien, Shot shot) {
        if ((alien.isVisible() && shot.isVisible())
                && (shot.getCordX() >= (alien.getCordX())
                && shot.getCordX() <= (alien.getCordX() + Settings.ALIEN_WIDTH)
                && shot.getCordY() >= (alien.getCordY())
                && shot.getCordY() <= (alien.getCordY() + Settings.ALIEN_HEIGHT))) {

            alien.setExploding(true);
            deaths++;
            shot.disappear();
            alien.disappear();
        }
    }
}