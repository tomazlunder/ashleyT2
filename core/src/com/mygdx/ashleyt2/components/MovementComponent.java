package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {
    public float horizontalAcceleration;
    public float horizontalDeceleration;
    public float maxHorizontalSpeed;

    public Vector2 velocity;

    public float jump_speed;

    //Used for determening state
    public Vector2 prevB2dVelocity;

    public float inAirHorizontalDeceleration;

    //Bullet
    public boolean hasBounced;

    public MovementComponent(float hAcc, float hDecc, float maxHorizontalSpeed, float jump_speed, float inAirHorizontalDeceleration){
        this.horizontalAcceleration = hAcc;
        this.horizontalDeceleration = hDecc;
        this.maxHorizontalSpeed = maxHorizontalSpeed;

        velocity = new Vector2(0,0);
        velocity.limit(maxHorizontalSpeed);

        prevB2dVelocity = new Vector2(0,0);

        this.jump_speed = jump_speed;
        this.inAirHorizontalDeceleration = inAirHorizontalDeceleration;
        this.hasBounced = false;
    }
}
