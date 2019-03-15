package com.mygdx.ashleyt2.input.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerInputState {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;

    public boolean shootPressed;

    public PlayerInputState() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;

        shootPressed = false;
    }
}

