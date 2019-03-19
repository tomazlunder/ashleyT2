package com.mygdx.ashleyt2.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.components.*;

import java.util.ArrayList;

public class LevelObjectFactory {

    private Engine engine;
    private World world;
    private float pixels_per_meter;

    public ArrayList<String> savedCommands;

    public TextureAtlas textureAtlas;

    public LevelObjectFactory(Engine engine, World world, float pixels_per_meter){
        this.engine = engine;
        this.world = world;
        this.pixels_per_meter = pixels_per_meter;

        this.savedCommands = new ArrayList<String>();
        this.textureAtlas = new TextureAtlas("atlas/game.atlas");
    }

    public void saveCommandsAsFile(String path){
        FileHandle file = Gdx.files.local("levels/gen/factoryOUT.lvl");
        file.writeString("",false);
        for(String command : savedCommands){
            file.writeString(command, true);
            file.writeString("\n", true);
        }
    }


    public void createPlayer(float x, float y){
        SerializableComponent serializableComponent = new SerializableComponent("player");

        savedCommands.add("player "+x+" "+y);

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

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);

        body.createFixture(shape,0);

        //Texture
        TextureRegion textureRegion = textureAtlas.findRegion("player");

        entity.add(new TransformComponent(position,0.5f*2*pixels_per_meter, 0.5f*2*pixels_per_meter))
                .add(new B2dBodyComponent(body))
                .add(serializableComponent)
                .add(new PlayerComponent(0.5f, 1f, 5, 12f, 0.01f, 20f))
                .add(new TextureComponent(textureRegion));

        //Add to engine
        engine.addEntity(entity);
    }

    public void createPlatform(float x, float y, float w, float h){
        SerializableComponent serializableComponent = new SerializableComponent("platform");
        serializableComponent.data.add(Float.toString(w));
        serializableComponent.data.add(Float.toString(h));

        savedCommands.add("platform "+x+" "+y+" "+w+" "+h);

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

        body.createFixture(fixtureDef);

        //Texture
        TextureRegion textureRegion = textureAtlas.findRegion("platform");

        entity.add(new TransformComponent(position,w*pixels_per_meter, h*pixels_per_meter))
                .add(new B2dBodyComponent(body))
                .add(serializableComponent)
                .add(new TextureComponent(textureRegion));

        //Add to engine
        engine.addEntity(entity);
    }

    public void createFinish(float x, float y, float w, float h){
        SerializableComponent serializableComponent = new SerializableComponent("finish");
        serializableComponent.data.add(Float.toString(w));
        serializableComponent.data.add(Float.toString(h));

        savedCommands.add("finish "+x+" "+y+" "+w+" "+h);

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

        entity.add(new TransformComponent(position, w, h))
                .add(new B2dBodyComponent(body))
                .add(serializableComponent);

        //Add to engine
        engine.addEntity(entity);
    }

}
