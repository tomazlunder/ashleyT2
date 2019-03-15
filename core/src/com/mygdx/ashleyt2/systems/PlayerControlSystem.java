package com.mygdx.ashleyt2.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.PlayerComponent;
import com.mygdx.ashleyt2.input.InputHandler;

import javax.swing.text.html.parser.Entity;

public class PlayerControlSystem extends EntitySystem {

    private ImmutableArray<com.badlogic.ashley.core.Entity> entities;

    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<B2dBodyComponent> vm = ComponentMapper.getFor(B2dBodyComponent.class);

    //Constants
    private boolean wasEvaluatedGroundedLastTick = false;

    float groundAcceleration = 0.046875f;
    float groundDecceleration = 0.5f;


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, B2dBodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for(com.badlogic.ashley.core.Entity e : entities){
            B2dBodyComponent bodyComponent = vm.get(e);
            PlayerComponent playerComponent = pm.get(e);



            //Update player state
            if(bodyComponent.body.getLinearVelocity().y == 0){
                playerComponent.playerState = PlayerComponent.PlayerState.GROUNDED;
            }
            else if(playerComponent.playerState != PlayerComponent.PlayerState.BULLET){
                playerComponent.playerState = PlayerComponent.PlayerState.AIR;
            }

            //Handle reactions to inputs based on player state
            if(playerComponent.playerState == PlayerComponent.PlayerState.GROUNDED) groundedHandler(bodyComponent);
            if(playerComponent.playerState == PlayerComponent.PlayerState.AIR) airHandler();

        }
    }

    //
    private void groundedHandler(B2dBodyComponent bodyComponent){
        if(InputHandler.playerInputState.leftPressed){
        }
    }
    private void airHandler(){

    }
}
