package com.mygdx.ashleyt2;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import com.mygdx.ashleyt2.screens.GameScreen;
import com.mygdx.ashleyt2.screens.MainMenuScreen;

public class B2dContactListener implements ContactListener {

    private GameClass game;
    private GameScreen gameScreen;

    public B2dContactListener(GameClass game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;
    }



    @Override
    public void beginContact(Contact contact) {
        // get fixtures
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        // check if both fixtures have a String object stored in the body's userData
        if(fa.getBody().getUserData() instanceof String){
            if(fb.getBody().getUserData() instanceof String) {
                entityCollision((String) fa.getBody().getUserData(),(String) fb.getBody().getUserData());
            }
        }

    }

    private void entityCollision(String fa, String fb) {
        if((fa.equals("player") && fb.equals("finish")) || (fa.equals("finish") && fb.equals("player"))){
            game.setScreen(new MainMenuScreen(game));
            gameScreen.dispose();
        }
    }

    @Override
    public void endContact(Contact contact) {
        //System.out.println("Contact end");
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}