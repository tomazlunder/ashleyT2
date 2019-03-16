package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PlayerComponent implements Component {
    private static int playerIDcounter = 0;

    public int playerID;

    public enum PlayerState{GROUNDED,AIR,BULLET};
    public PlayerState playerState;
    public PlayerState prevPlayerState;

    //Player movement stuff...
    //GROUNDED
    //Velocity when grounded
    public Vector2 velocity;

    public float horizontalAcceleration;
    public float horizontalDeceleration;
    public float maxHorizontalSpeed;

    public float jump_speed;

    //Used for determening state
    public Vector2 prevB2dVelocity;

    //AIR
    public float inAirHorizontalDeceleration;

    //BULLET
    public float prevRestitution, prevFriction;

    public PlayerComponent(float hAcc, float hDecc, float maxHorizontalSpeed, float jump_speed, float inAirHorizontalDeceleration){
        this.playerID = playerIDcounter;
        this.playerIDcounter++;

        this.playerState = PlayerState.AIR;
        this.prevPlayerState = PlayerState.AIR;

        //Movement
        this.horizontalAcceleration = hAcc;
        this.horizontalDeceleration = hDecc;
        this.maxHorizontalSpeed = maxHorizontalSpeed;

        velocity = new Vector2(0,0);
        velocity.limit(maxHorizontalSpeed);

        prevB2dVelocity = new Vector2(0,0);

        this.jump_speed = jump_speed;
        this.inAirHorizontalDeceleration = inAirHorizontalDeceleration;
    };
}
