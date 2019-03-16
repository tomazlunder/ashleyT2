package com.mygdx.ashleyt2.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.MovementComponent;
import com.mygdx.ashleyt2.components.PlayerComponent;
import com.mygdx.ashleyt2.input.InputHandler;

public class PlayerControlSystem extends EntitySystem {

    private ImmutableArray<com.badlogic.ashley.core.Entity> entities;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<B2dBodyComponent> vm = ComponentMapper.getFor(B2dBodyComponent.class);
    private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);

    private float bulletSpeed = 30f;


    //Constants
    private boolean wasEvaluatedGroundedLastTick = false;

    float groundAcceleration = 0.046875f;
    float groundDecceleration = 0.5f;


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, B2dBodyComponent.class, MovementComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for(com.badlogic.ashley.core.Entity e : entities){


            PlayerComponent playerComponent = pm.get(e);
            B2dBodyComponent bodyComponent = vm.get(e);
            MovementComponent movementComponent = mm.get(e);


            if(playerComponent.playerState == PlayerComponent.PlayerState.BULLET) bulletHandler(deltaTime, playerComponent,bodyComponent,movementComponent);


            if(playerComponent.playerState == PlayerComponent.PlayerState.AIR){
                if(movementComponent.prevB2dVelocity.y < 0 && bodyComponent.body.getLinearVelocity().y > 0){
                    playerComponent.playerState = PlayerComponent.PlayerState.GROUNDED;
                    bodyComponent.body.setLinearVelocity(0,0);
                }

                else if(bodyComponent.body.getLinearVelocity().y == 0){
                    playerComponent.playerState = PlayerComponent.PlayerState.GROUNDED;
                }

                //INTO BULLET MODE YARR
                else if(!InputHandler.prevPlayerInputState.jumpPressed && InputHandler.playerInputState.jumpPressed){
                    playerComponent.playerState = PlayerComponent.PlayerState.BULLET;
                    bodyComponent.body.setGravityScale(0);

                    bodyComponent.body.getFixtureList().get(0).setRestitution(10);

                    Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());
                    velocity.setLength(bulletSpeed);

                    bodyComponent.body.setLinearVelocity(velocity);

                    //movementComponent.hasBounced = true; //TODO: Fix this
                }
            }


            //Handle reactions to inputs based on player state
            if(playerComponent.playerState == PlayerComponent.PlayerState.GROUNDED) groundedHandler(deltaTime, playerComponent,bodyComponent,movementComponent);
            if(playerComponent.playerState == PlayerComponent.PlayerState.AIR) airHandler(deltaTime, playerComponent,bodyComponent,movementComponent);


            movementComponent.prevB2dVelocity = bodyComponent.body.getLinearVelocity();
        }
    }

    //
    private void groundedHandler(float deltaTime, PlayerComponent playerComponent, B2dBodyComponent bodyComponent, MovementComponent movementComponent){



        Vector2 velocity = new Vector2(movementComponent.velocity);
        velocity.limit(movementComponent.maxHorizontalSpeed);

        if(InputHandler.playerInputState.leftPressed && !InputHandler.playerInputState.rightPressed){
            movementComponent.velocity.x -= movementComponent.horizontalAcceleration;

            if(velocity.x <= 0){
                velocity.x -= movementComponent.horizontalAcceleration;
            }
            else {
                velocity.x += movementComponent.horizontalDeceleration;
                velocity.x = Math.min(velocity.x, 0);
            }
        }
        else if(!InputHandler.playerInputState.leftPressed && InputHandler.playerInputState.rightPressed){
            movementComponent.velocity.x -= movementComponent.horizontalAcceleration;

            if(velocity.x >= 0){
                velocity.x += movementComponent.horizontalAcceleration;
            }
            else {
                velocity.x -= movementComponent.horizontalDeceleration;
                velocity.x = Math.max(velocity.x, 0);
            }
        }
        //If none of movement keys is pressed (or both)
        else {
            if(velocity.x > 0){
                velocity.x = Math.max(velocity.x - movementComponent.horizontalDeceleration, 0);
            }
            else if (velocity.x < 0){
                velocity.x = Math.min(velocity.x + movementComponent.horizontalDeceleration, 0);
            }
        }

        if(InputHandler.playerInputState.jumpPressed){
            velocity.y = movementComponent.jump_speed;
            playerComponent.playerState = PlayerComponent.PlayerState.AIR;
            bodyComponent.body.setLinearVelocity(velocity);
            movementComponent.velocity.set(0,0);
            return;
        }

        bodyComponent.body.setLinearVelocity(velocity);
        movementComponent.velocity = new Vector2(velocity);
    }

    private void airHandler(float deltaTime, PlayerComponent playerComponent, B2dBodyComponent bodyComponent, MovementComponent movementComponent){
        Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());
        if(velocity.x > 0){
            velocity.x = Math.max(0, velocity.x - movementComponent.inAirHorizontalDeceleration);
        }
        if(velocity.x < 0){
            velocity.x = Math.min(0, velocity.x + movementComponent.inAirHorizontalDeceleration);
        }

        bodyComponent.body.setLinearVelocity(velocity);
    }

    private void bulletHandler(float deltaTime, PlayerComponent playerComponent, B2dBodyComponent bodyComponent, MovementComponent movementComponent){
        if(InputHandler.playerInputState.jumpPressed && !InputHandler.prevPlayerInputState.jumpPressed){
            playerComponent.playerState = PlayerComponent.PlayerState.AIR;
            bodyComponent.body.setGravityScale(1);
            bodyComponent.body.getFixtureList().get(0).setRestitution(0);

            Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());
            velocity.setLength(movementComponent.maxHorizontalSpeed);
            bodyComponent.body.setLinearVelocity(velocity);
        }

    }
}
