package com.mygdx.ashleyt2.ui.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.B2dContactListener;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.input.InputHandler;
import com.mygdx.ashleyt2.levels.LevelInterface;
import com.mygdx.ashleyt2.systems.B2dPhysicsSystem;
import com.mygdx.ashleyt2.systems.PlayerControlSystem;
import com.mygdx.ashleyt2.systems.RenderingSystem;

public class GameScreen implements Screen {
    //NEEDED FOR EVERY (BOX2D) GAME
    private final GameClass game;


    private int pixels_per_meter;
    private float pixels_to_meters;

    private Engine engine;
    private World world;

    private Vector2 gravity = new Vector2(0f,-17.8f);

    private OrthographicCamera camera;


    public GameScreen(final GameClass game, LevelInterface levelInterface) {
        this.game = game;

        //Create basic components
        this.pixels_per_meter = (int) (Gdx.graphics.getWidth()/levelInterface.getWorldWidth());
        this.pixels_to_meters = 1.0f/(float) pixels_per_meter;

        this.engine = new Engine();
        world = new World(gravity, true);
        world.setContactListener(new B2dContactListener(game));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Add systems
        //engine.addSystem(new B2dRenderSystem(world,camera, pixels_per_meter));
        engine.addSystem(new B2dPhysicsSystem(world, pixels_per_meter));
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new PlayerControlSystem(pixels_per_meter));

        //Load entities
        levelInterface.addEntities(engine,world,pixels_per_meter);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game screen ", 100, Gdx.graphics.getHeight());
        game.batch.end();


        InputHandler.updateStates();
        game.batch.begin();
        engine.update(delta);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    Vector2 getMousePosInGameWorld() {
        return new Vector2(Gdx.input.getX()*pixels_to_meters, (Gdx.graphics.getHeight()-Gdx.input.getY())*pixels_to_meters) ;
    }

    public void toMaimMenu(){
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

}
