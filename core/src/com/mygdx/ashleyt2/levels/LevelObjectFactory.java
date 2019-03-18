package com.mygdx.ashleyt2.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.NameComponent;
import com.mygdx.ashleyt2.components.PlayerComponent;
import com.mygdx.ashleyt2.components.TransformComponent;

public class LevelObjectFactory {

    private Engine engine;
    private World world;
    private float pixels_per_meter;

    public LevelObjectFactory(Engine engine, World world, float pixels_per_meter){
        this.engine = engine;
        this.world = world;
        this.pixels_per_meter = pixels_per_meter;
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

}
