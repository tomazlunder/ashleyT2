package com.mygdx.ashleyt2.ui.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.level.editor.EditorSelectSystem;
import com.mygdx.ashleyt2.level.editor.WorldSerializer;
import com.mygdx.ashleyt2.input.InputHandler;
import com.mygdx.ashleyt2.level.LevelInterface;
import com.mygdx.ashleyt2.systems.RenderingSystem;

import java.util.ArrayList;

public class LevelEditorScreen implements Screen {
    //NEEDED FOR EVERY (BOX2D) GAME
    private final GameClass game;


    private int pixels_per_meter;
    private float pixels_to_meters;

    private Engine engine;
    private World world;

    private Vector2 gravity = new Vector2(0f,-17.8f);

    private OrthographicCamera camera;

    private Body selector;

    public ArrayList<Entity> toRemove;

    private Viewport viewport;

    //Stage sta



    public LevelEditorScreen(final GameClass game, LevelInterface levelInterface) {
        this.game = game;

        //Create basic components
        this.pixels_per_meter = (int) (Gdx.graphics.getWidth()/levelInterface.getWorldWidth());
        this.pixels_to_meters = 1.0f/(float) pixels_per_meter;

        this.engine = new Engine();
        world = new World(gravity, true);

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera = new OrthographicCamera();
        //camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        //Add systems
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new EditorSelectSystem(pixels_per_meter, this));

        //Load entities
        levelInterface.addEntities(engine,world,pixels_per_meter);

        //Selector
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-500,-500);

        selector = world.createBody(bodyDef);
        selector.setUserData("select");

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        selector.createFixture(fixtureDef);

        toRemove = new ArrayList<Entity>();
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

        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            saveTest(WorldSerializer.serializeWorld(engine));
            Gdx.app.exit();
        }

        if(Gdx.input.justTouched()){
            selector.setTransform(getMousePosInGameWorld(),0);
        }

        if(toRemove.size() > 0){
            for(Entity e : toRemove){
                engine.removeEntity(e);
            }
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

    }

    Vector2 getMousePosInGameWorld() {
        return new Vector2(Gdx.input.getX()*pixels_to_meters, (Gdx.graphics.getHeight()-Gdx.input.getY())*pixels_to_meters) ;
    }

    private void saveTest(ArrayList<String> savedCommands){
            FileHandle file = Gdx.files.local("level/gen/editorOUT.lvl");
            file.writeString("",false);
            for(String command : savedCommands){
                file.writeString(command, true);
                file.writeString("\n", true);
            }
    }


}
