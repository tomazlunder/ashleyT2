package com.mygdx.ashleyt2.ui.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.B2dContactListener;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.input.InputHandler;
import com.mygdx.ashleyt2.level.Constants;
import com.mygdx.ashleyt2.level.Level;
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

    private OrthographicCamera camera;

    public boolean toMenu;

    public GameScreen(final GameClass game, Level level) {
        this.game = game;

        //Create basic components
        this.pixels_per_meter = (int) (Gdx.graphics.getWidth()/level.width);
        this.pixels_to_meters = 1.0f/(float) pixels_per_meter;

        this.engine = new Engine();
        world = new World(Constants.gravity, true);
        world.setContactListener(new B2dContactListener(this));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Add systems
        //engine.addSystem(new B2dRenderSystem(world,camera, pixels_per_meter));
        engine.addSystem(new B2dPhysicsSystem(world));
        engine.addSystem(new RenderingSystem(game.batch, pixels_per_meter));
        engine.addSystem(new PlayerControlSystem());

        //Load entities
        level.loadSerializedObjects(engine,world);

        toMenu = false;
        InputHandler.pixels_to_meters = pixels_to_meters;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        InputHandler.updateStates();

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);

        //NORMAL:
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //ANTI ALIASING:
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game screen ", 100, Gdx.graphics.getHeight());
        engine.update(delta);
        game.batch.end();

        if(toMenu || Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

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
        world.dispose();
    }

}
