package com.SpaceInv.tests;

import com.SpaceInv.ds.Alien;
import org.junit.Test;
import static org.junit.Assert.*;

public class BombPlacementTest {

    @Test
    public void testBombPosition() {
        int cordX = 100;
        int cordY = 200;
        Alien alien = new Alien(cordX, cordY);
        Alien.Bomb bomb = alien.getBomb();

        dropBomb(alien, bomb);

        // test if bomb in correct position
        assertFalse(bomb.isDestroyed());
        assertEquals(cordX, bomb.getCordX());
        assertEquals(cordY, bomb.getCordY());
    }

    private void dropBomb(Alien alien, Alien.Bomb bomb) {
        if (alien.isVisible() && bomb.isDestroyed()) {
            bomb.setDestroyed(false);
            bomb.setCordX(alien.getCordX());
            bomb.setCordY(alien.getCordY());
        }
    }
}