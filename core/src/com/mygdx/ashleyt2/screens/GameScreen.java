package com.mygdx.ashleyt2.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.TransformComponent;
import com.mygdx.ashleyt2.systems.B2dRenderSystem;

public class GameScreen implements Screen {
    final GameClass game;

    Engine engine;
    World world;

    Vector2 gravity = new Vector2(0f,-9.8f);

    private final int PIXELS_PER_METER = 100;
    private float PIXELS_TO_METERS = 1.0f/(float) PIXELS_PER_METER;

    Box2DDebugRenderer debugRenderer;

    OrthographicCamera camera;

    public GameScreen(final GameClass game) {
        this.game = game;

        this.engine = new Engine();
        world = new World(gravity, true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        engine.addSystem(new B2dRenderSystem(world,camera));

        createPlayer(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game screen ", 100, Gdx.graphics.getHeight());

        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            Vector3 mousePos = getMousePosInGameWorld();

            createPlayer(mousePos.x,mousePos.y);
            System.out.printf("(Screen) [%f,%f] (Gdx.input.get()) - (Camera world) [%f,%f] - (Box world) [%f,%f]\n",x, y, mousePos.x, mousePos.y, x*PIXELS_TO_METERS, y*PIXELS_TO_METERS);
        }


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

    public void createPlayer(float x, float y){
        Entity entity = new Entity();
        //Transform component
        Vector3 position = new Vector3(x,y,1);
        entity.add(new TransformComponent(position));
        //Box2d component
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1000*PIXELS_TO_METERS,1000*PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef);

        body.createFixture(shape,0);

        entity.add(new B2dBodyComponent(body));

        engine.addEntity(entity);
    }

    Vector3 getMousePosInGameWorld() {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

}
