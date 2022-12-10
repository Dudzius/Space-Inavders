package com.SpaceInv.tests;

import com.SpaceInv.Settings;
import com.SpaceInv.ds.Alien;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class AlienMovementTest {
    private int direction = 1;

    @Test
    public void testAlienMovement() {
        int alienCordX = 100;
        int alienCordY = 200;
        Alien alien = new Alien(alienCordX,alienCordY);

        // set alien at right border
        alien.setCordX(Settings.BOARD_WIDTH - Settings.BORDER_RIGHT);

        moveAliens(alien, direction);

        // test if alien moved down and changed direction
        assertEquals(alienCordY + Settings.GO_DOWN, alien.getCordY());
        assertEquals(-1, direction);

        // set alien at left border
        alien.setCordX(Settings.BORDER_LEFT);

        moveAliens(alien, direction);

        // test if alien moved down and changed direction again
        assertEquals(alienCordY + (Settings.GO_DOWN * 2), alien.getCordY());
        assertEquals(1, direction);
    }
    private void moveAliens(Alien alien, int d){
        if (alien.getCordX() >= Settings.BOARD_WIDTH - Settings.BORDER_RIGHT && d != -1) {
            direction = -1;
            alien.setCordY(alien.getCordY() + Settings.GO_DOWN);
        }

        if (alien.getCordX() <= Settings.BORDER_LEFT && d != 1) {
            direction = 1;
            alien.setCordY(alien.getCordY() + Settings.GO_DOWN);
        }
    }
}