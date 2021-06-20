package com.rickrip.game0;

public class SaveData {

    int int_profileNumber = 0;
    int int_highScore = 0;
    int int_currentScore = 0;
    int int_comboTimesMultiplier = 0;
    int GAME_STATE = 0;
    int GAME_STATE_BF = 0;
    int GAME_STATE_BONUSTYPE = 0;
    int GAME_STATE_COLLIDE = 0;
    int GAME_STATE_PDF = 0;
    boolean bool_highScoreBeaten = false;
    boolean bool_ghostEnabled = true;
    String nextBrickState;
    String fallingBrickState;
    String mainFieldState;

    public SaveData(){int_profileNumber=0;}
}
