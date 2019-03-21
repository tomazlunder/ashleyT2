package com.mygdx.ashleyt2.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ashleyt2.input.states.AdminInputState;
import com.mygdx.ashleyt2.input.states.PlayerInputState;

public class InputHandler {

    public static AdminInputState adminInputState;
    public static PlayerInputState playerInputState;

    public static PlayerInputState prevPlayerInputState;

    public static float pixels_to_meters = 100;

    public static Vector2 getMousePosInGameWorld() {
        return new Vector2(Gdx.input.getX()*pixels_to_meters, (Gdx.graphics.getHeight()-Gdx.input.getY())*pixels_to_meters) ;
    }


    public static void updateStates(){
        if(playerInputState != null){
            prevPlayerInputState = playerInputState;
        } else {
            prevPlayerInputState = createPlayerInputState();
        }

        playerInputState = createPlayerInputState();
        adminInputState = createAdminInputState();
    }

    private static PlayerInputState createPlayerInputState(){
        PlayerInputState playerInputState = new PlayerInputState();

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            playerInputState.upPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            playerInputState.downPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            playerInputState.leftPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            playerInputState.rightPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            playerInputState.jumpPressed = true;
        }

        if(Gdx.input.isTouched()){
            playerInputState.shootPressed = true;
        }

        return playerInputState;
    }

    private static AdminInputState createAdminInputState(){
        AdminInputState adminInputState = new AdminInputState();

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            adminInputState.upPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            adminInputState.downPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            adminInputState.leftPressed = true;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            adminInputState.rightPressed = true;
        }

        if(Gdx.input.isTouched()){
            adminInputState.clicked = true;
        }

        return adminInputState;
    }
}
