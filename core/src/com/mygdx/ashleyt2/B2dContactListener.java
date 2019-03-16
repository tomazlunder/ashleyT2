package com.mygdx.ashleyt2;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.ashleyt2.components.B2dBodyComponent;

public class B2dContactListener implements ContactListener {

    private ComponentMapper<B2dBodyComponent> vm = ComponentMapper.getFor(B2dBodyComponent.class);


    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        // get fixtures
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());
        // check if either fixture has an Entity object stored in the body's userData
        if(fa.getBody().getUserData() instanceof Entity){
            Entity ent = (Entity) fa.getBody().getUserData();
            entityCollision(ent,fb);
            return;
        }else if(fb.getBody().getUserData() instanceof Entity){
            Entity ent = (Entity) fb.getBody().getUserData();
            entityCollision(ent,fa);
            return;
        }
    }

    private void entityCollision(Entity ent, Fixture fb) {
        // check the collided Entity is also an Entity
        if(fb.getBody().getUserData() instanceof Entity){
            Entity colEnt = (Entity) fb.getBody().getUserData();
            // get the components for this entity
            //CollisionComponent col = ent.getComponent(CollisionComponent.class);
            //CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);

            // set the CollisionEntity of the component
            /*
            if(col != null){
                col.collisionEntity = colEnt;
            }else if(colb != null){
                colb.collisionEntity = ent;
            }
            */
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Contact end");
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}