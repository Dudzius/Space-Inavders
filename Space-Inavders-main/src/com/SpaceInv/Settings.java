package com.SpaceInv;

public interface Settings {

    int BOARD_WIDTH = 458;
    int BOARD_HEIGHT = 400;
    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 5;

    int GROUND = 310;
    int BOMB_HEIGHT = 5;

    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int ALIEN_WITH_GAP = 20;
    int ALIEN_INIT_X = 150;
    int ALIEN_INIT_Y = 5;
    int ALIEN_ROWS = 3;
    int ALIEN_COL = 6;

    int GO_DOWN = 15;
    int NUMBER_OF_ALIENS_TO_DESTROY = ALIEN_ROWS * ALIEN_COL;
    int CHANCE = 5;
    int DELAY = 15;
    int RANDOM_GENERATE = 200;

    int PLAYER_SHOT_SPEED = 5;
    int PLAYER_SPEED = 2;
    int PLAYER_WIDTH = 15;
    int PLAYER_HEIGHT = 10;
}
