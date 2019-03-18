package com.mygdx.ashleyt2.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.PlayerComponent;
import com.mygdx.ashleyt2.input.InputHandler;

public class PlayerControlSystem extends EntitySystem {

    private ImmutableArray<com.badlogic.ashley.core.Entity> entities;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<B2dBodyComponent> vm = ComponentMapper.getFor(B2dBodyComponent.class);


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, B2dBodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for(com.badlogic.ashley.core.Entity e : entities){
            PlayerComponent playerComponent = pm.get(e);
            B2dBodyComponent bodyComponent = vm.get(e);


            //AIR TO ...
            if(playerComponent.playerState == PlayerComponent.PlayerState.AIR){
                //air to GROUNDED
                if((playerComponent.prevB2dVelocity.y < 0 && bodyComponent.body.getLinearVelocity().y > 0)
                        || bodyComponent.body.getLinearVelocity().y == 0)
                {
                    playerComponent.prevPlayerState = playerComponent.playerState;
                    playerComponent.playerState = PlayerComponent.PlayerState.GROUNDED;
                    playerComponent.velocity = new Vector2(bodyComponent.body.getLinearVelocity().x,0);
                    bodyComponent.body.setLinearVelocity(bodyComponent.body.getLinearVelocity().x,0);
                }

                //air to BULLET
                else if(playerComponent.prevPlayerState != PlayerComponent.PlayerState.BULLET && !InputHandler.prevPlayerInputState.jumpPressed && InputHandler.playerInputState.jumpPressed){
                    airToBullet(playerComponent,bodyComponent);
                }
            }

            //GROUNDED TO AIR
            else if(playerComponent.playerState == PlayerComponent.PlayerState.GROUNDED && bodyComponent.body.getLinearVelocity().y != 0){
                playerComponent.prevPlayerState = playerComponent.playerState;
                playerComponent.playerState = PlayerComponent.PlayerState.AIR;
            }

            //Handle reactions to inputs based on player state


            if(playerComponent.playerState == PlayerComponent.PlayerState.BULLET) bulletHandler(deltaTime, playerComponent,bodyComponent);
            if(playerComponent.playerState == PlayerComponent.PlayerState.GROUNDED) groundedHandler(deltaTime, playerComponent,bodyComponent);
            if(playerComponent.playerState == PlayerComponent.PlayerState.AIR) airHandler(deltaTime, playerComponent,bodyComponent);

            playerComponent.prevB2dVelocity = new Vector2(bodyComponent.body.getLinearVelocity());
        }
    }

    //STATE HANDLERS
    private void groundedHandler(float deltaTime, PlayerComponent playerComponent, B2dBodyComponent bodyComponent){
        Vector2 velocity = new Vector2(playerComponent.velocity);
        velocity.limit(playerComponent.maxHorizontalSpeed);

        if(InputHandler.playerInputState.leftPressed && !InputHandler.playerInputState.rightPressed){
            playerComponent.velocity.x -= playerComponent.horizontalAcceleration;

            if(velocity.x <= 0){
                velocity.x -= playerComponent.horizontalAcceleration;
            }
            else {
                velocity.x += playerComponent.horizontalDeceleration;
                velocity.x = Math.min(velocity.x, 0);
            }
        }
        else if(!InputHandler.playerInputState.leftPressed && InputHandler.playerInputState.rightPressed){
            playerComponent.velocity.x -= playerComponent.horizontalAcceleration;

            if(velocity.x >= 0){
                velocity.x += playerComponent.horizontalAcceleration;
            }
            else {
                velocity.x -= playerComponent.horizontalDeceleration;
                velocity.x = Math.max(velocity.x, 0);
            }
        }
        //If none of movement keys is pressed (or both)
        else {
            if(velocity.x > 0){
                velocity.x = Math.max(velocity.x - playerComponent.horizontalDeceleration, 0);
            }
            else if (velocity.x < 0){
                velocity.x = Math.min(velocity.x + playerComponent.horizontalDeceleration, 0);
            }
        }

        if(InputHandler.playerInputState.jumpPressed){
            velocity.y = playerComponent.jump_speed;
            //playerComponent.playerState = PlayerComponent.PlayerState.AIR;
            bodyComponent.body.setLinearVelocity(velocity);
            playerComponent.velocity.set(0,0);
            return;
        }

        bodyComponent.body.setLinearVelocity(velocity);
        playerComponent.velocity = new Vector2(velocity);
    }

    private void airHandler(float deltaTime, PlayerComponent playerComponent, B2dBodyComponent bodyComponent){
        Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());
        if(velocity.x > 0){
            velocity.x = Math.max(0, velocity.x - playerComponent.inAirHorizontalDeceleration);
        }
        if(velocity.x < 0){
            velocity.x = Math.min(0, velocity.x + playerComponent.inAirHorizontalDeceleration);
        }

        bodyComponent.body.setLinearVelocity(velocity);
    }

    private void bulletHandler(float deltaTime, PlayerComponent playerComponent, B2dBodyComponent bodyComponent){
        //InputHandler.playerInputState.downPressed &&
        if( playerComponent.bounced){
            bulletToAir(playerComponent,bodyComponent);
        }

        Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());

        if(playerComponent.prevB2dVelocity.len() > (playerComponent.bulletSpeed-1) &&
                !playerComponent.prevB2dVelocity.equals(velocity)){
            //System.out.println("Bounced");
            playerComponent.bounced = true;
        }
    }


    private void airToBullet(PlayerComponent playerComponent, B2dBodyComponent bodyComponent){
        playerComponent.prevPlayerState = playerComponent.playerState;
        playerComponent.playerState = PlayerComponent.PlayerState.BULLET;

        Fixture f = bodyComponent.body.getFixtureList().get(0);

        playerComponent.prevRestitution = f.getRestitution();
        playerComponent.prevFriction = f.getFriction();

        f.setRestitution(1f);
        f.setFriction(0f);
        //f.getShape().setRadius(0.1f);

        bodyComponent.body.setGravityScale(0);
        bodyComponent.body.setBullet(true);

        Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());
        velocity.setLength(playerComponent.bulletSpeed);

        bodyComponent.body.setLinearVelocity(velocity);

        playerComponent.bounced = false;
    }

    private void bulletToAir(PlayerComponent playerComponent, B2dBodyComponent bodyComponent){
        playerComponent.prevPlayerState = playerComponent.playerState;
        playerComponent.playerState = PlayerComponent.PlayerState.AIR;

        Fixture f = bodyComponent.body.getFixtureList().get(0);

        f.setRestitution(playerComponent.prevRestitution);
        f.setFriction(playerComponent.prevFriction);

        //bodyComponent.body.getFixtureList().set(0, f);


        bodyComponent.body.setGravityScale(1);
        bodyComponent.body.setBullet(false);

        Vector2 velocity = new Vector2(bodyComponent.body.getLinearVelocity());
        velocity.setLength(playerComponent.maxHorizontalSpeed*3);
        bodyComponent.body.setLinearVelocity(velocity);
    }
}
