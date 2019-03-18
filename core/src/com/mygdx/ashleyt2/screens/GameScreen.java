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
import com.mygdx.ashleyt2.B2dContactListener;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.components.*;
import com.mygdx.ashleyt2.input.InputHandler;
import com.mygdx.ashleyt2.systems.B2dPhysicsSystem;
import com.mygdx.ashleyt2.systems.B2dRenderSystem;
import com.mygdx.ashleyt2.systems.PlayerControlSystem;

public class GameScreen implements Screen {
    //NEEDED FOR EVERY (BOX2D) GAME
    final GameClass game;

    Engine engine;
    World world;

    //Vector2 gravity = new Vector2(0f,-9.8f);
    Vector2 gravity = new Vector2(0f,-17.8f);


    private int pixels_per_meter;
    private float pixels_to_meters;

    Box2DDebugRenderer debugRenderer;

    OrthographicCamera camera;

    //GAME SPECIFIC
    private final float WORLD_WIDTH = 32.0f;
    private final float WORLD_HEIGHT = 18.0f;


    public GameScreen(final GameClass game) {
        this.game = game;

        this.pixels_per_meter = (int) (Gdx.graphics.getWidth()/WORLD_WIDTH);
        this.pixels_to_meters = 1.0f/(float) pixels_per_meter;


        this.engine = new Engine();
        world = new World(gravity, true);
        world.setContactListener(new B2dContactListener(game,this));
        //world.setContinuousPhysics(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        engine.addSystem(new B2dRenderSystem(world,camera, pixels_per_meter));
        engine.addSystem(new B2dPhysicsSystem(world, pixels_per_meter));
        engine.addSystem(new PlayerControlSystem());

        createLevel2();
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
        game.batch.end();

        /*
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            Vector2 mousePos = getMousePosInGameWorld();

            createBall(mousePos.x, mousePos.y);
            System.out.printf("(Screen) [%f,%f] (Gdx.input.get()) - (Camera world) [%f,%f] - (Box world) [%f,%f]\n",x, y, mousePos.x, mousePos.y, x*pixels_to_meters, y*pixels_to_meters);
        }
        */

        InputHandler.updateStates();
        engine.update(delta);

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

    public void createLevel1(){
        float edgeWidth = 0.5f;
        //top edge
        createPlatform(WORLD_WIDTH/2, WORLD_HEIGHT-(edgeWidth/2),WORLD_WIDTH,edgeWidth);

        //bottom edge
        createPlatform(WORLD_WIDTH/2, edgeWidth/2, WORLD_WIDTH, edgeWidth);

        //left edge
        createPlatform(edgeWidth/2, WORLD_HEIGHT/2, edgeWidth, WORLD_HEIGHT-(edgeWidth*2));

        //right edge
        createPlatform(WORLD_WIDTH-(edgeWidth/2), WORLD_HEIGHT/2, edgeWidth, WORLD_HEIGHT-(edgeWidth*2));

        float platformW1 = 2;
        float platfowmH1 = 1;

        //random platform 1
        createPlatform(WORLD_WIDTH/5, WORLD_HEIGHT/2, platformW1, platfowmH1);

        float finishWH = 0.3f;
        createFinish(WORLD_WIDTH/5, WORLD_HEIGHT/2 + (finishWH/2) + platfowmH1/2, finishWH, finishWH);

        //"player"
        createPlayer(WORLD_WIDTH/2, WORLD_HEIGHT/2);
    }

    public void createLevel2(){
        float edgeWidth = 0.5f;
        //top edge
        createPlatform(WORLD_WIDTH/2, WORLD_HEIGHT-(edgeWidth/2),WORLD_WIDTH,edgeWidth);

        //bottom edge
        createPlatform(WORLD_WIDTH/2, edgeWidth/2, WORLD_WIDTH, edgeWidth);

        //left edge
        createPlatform(edgeWidth/2, WORLD_HEIGHT/2, edgeWidth, WORLD_HEIGHT-(edgeWidth*2));

        //right edge
        createPlatform(WORLD_WIDTH-(edgeWidth/2), WORLD_HEIGHT/2, edgeWidth, WORLD_HEIGHT-(edgeWidth*2));

        float platformW1 = 2;
        float platfowmH1 = 1;


        //finish platform

        float finishWH = 0.3f;
        createFinish(WORLD_WIDTH/5, WORLD_HEIGHT/2 + (finishWH/2) + platfowmH1/2, finishWH, finishWH);

        //random platform
        createPlatform((WORLD_WIDTH/5) * 0, WORLD_HEIGHT/2, platformW1, platfowmH1);
        createPlatform((WORLD_WIDTH/5) * 1, WORLD_HEIGHT/2, platformW1, platfowmH1);
        createPlatform((WORLD_WIDTH/5) * 2, WORLD_HEIGHT/2, platformW1, platfowmH1);
        createPlatform((WORLD_WIDTH/5) * 3, WORLD_HEIGHT/2, platformW1, platfowmH1);
        createPlatform((WORLD_WIDTH/5) * 4, WORLD_HEIGHT/2, platformW1, platfowmH1);
        createPlatform((WORLD_WIDTH/5) * 5, WORLD_HEIGHT/2, platformW1, platfowmH1);





        //"player"
        createPlayer(WORLD_WIDTH/2, WORLD_HEIGHT/2);
    }

    public void createPlayer(float x, float y){
        Entity entity = new Entity();
        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);
        //Box2d component
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);



        Body body = world.createBody(bodyDef);
        body.setUserData("player");
        body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);
        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox(0.5f,0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);

        body.createFixture(shape,0);

        entity.add(new TransformComponent(position))
                .add(new B2dBodyComponent(body))
                .add(new NameComponent("ball"))
                .add(new PlayerComponent(0.5f, 1f, 5, 9f, 0.01f, 25f));


        //Add to engine
        engine.addEntity(entity);
    }

    public void createPlatform(float x, float y, float w, float h){
        Entity entity = new Entity();

        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);

        //Box2d component
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/2,h/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.5f;
        //fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);

        //body.createFixture(shape,0);

        entity.add(new TransformComponent(position))
                .add(new B2dBodyComponent(body))
                .add(new NameComponent("platform"));
        //Add to engine
        engine.addEntity(entity);
    }

    public void createFinish(float x, float y, float w, float h){
        Entity entity = new Entity();

        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);

        //Box2d component
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);
        body.setUserData("finish");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/2,h/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);

        entity.add(new TransformComponent(position))
                .add(new B2dBodyComponent(body))
                .add(new NameComponent("platform"));
        //Add to engine
        engine.addEntity(entity);
    }


    Vector2 getMousePosInGameWorld() {
        return new Vector2(Gdx.input.getX()*pixels_to_meters, (Gdx.graphics.getHeight()-Gdx.input.getY())*pixels_to_meters) ;
    }

    public void toMaimMenu(){
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

}
