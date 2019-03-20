package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PlayerComponent implements Component {
    private static int playerIDcounter = 0;

    public int playerID;

    public enum PlayerState{GROUNDED,AIR,BULLET};
    public PlayerState playerState;
    public PlayerState prevPlayerState;
    public PlayerState prevFramePlayerState;
    public float timeInState;

    //Player movement stuff...
    //GROUNDED
    //Velocity when grounded
    public Vector2 velocity;



    //Used for determening state
    public Vector2 prevB2dVelocity;

    //AIR

    //BULLET
    public float prevRestitution, prevFriction;
    public boolean bounced;

    public PlayerComponent(){
        this.playerID = playerIDcounter;
        playerIDcounter++;

        this.playerState = PlayerState.AIR;
        this.prevPlayerState = PlayerState.AIR;
        this.prevFramePlayerState = PlayerState.AIR;
        this.timeInState = 0;

        //Movement

        velocity = new Vector2(0,0);
        //velocity.limit(maxHorizontalSpeed);

        prevB2dVelocity = new Vector2(0,0);

        this.bounced = false;
    };
}
