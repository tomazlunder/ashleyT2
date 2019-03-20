package com.mygdx.ashleyt2.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.components.*;

import java.util.ArrayList;

public class EntityFactory {

    private Engine engine;
    private World world;
    private float pixels_per_meter;

    public ArrayList<String> savedCommands;

    public TextureAtlas textureAtlas;

    public EntityFactory(Engine engine, World world, float pixels_per_meter){
        this.engine = engine;
        this.world = world;
        this.pixels_per_meter = pixels_per_meter;

        this.savedCommands = new ArrayList<String>();
        this.textureAtlas = new TextureAtlas("atlas/game.atlas");
    }

    public void saveCommandsAsFile(String path){
        FileHandle file = Gdx.files.local("level/gen/factoryOUT.lvl");
        file.writeString("",false);
        for(String command : savedCommands){
            file.writeString(command, true);
            file.writeString("\n", true);
        }
    }

    public void createPlayer(float x, float y){
        savedCommands.add("player "+x+" "+y);

        //create entity
        Entity entity = new Entity();

        //Serializable component
        SerializableComponent serializableComponent = new SerializableComponent("player");
        serializableComponent.data.add(""+x);
        serializableComponent.data.add(""+y);
        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);
        TransformComponent transformComponent = new TransformComponent(position,Constants.playerRadius*2*pixels_per_meter, Constants.playerRadius*2*pixels_per_meter);

        //Box2dBody component
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);
        body.setUserData("player");
        body.setFixedRotation(true);

        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.playerRadius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);

        B2dBodyComponent bodyComponent = new B2dBodyComponent(body);

        //Player component
        PlayerComponent playerComponent = new PlayerComponent();

        //Texture component
        TextureRegion textureRegion = textureAtlas.findRegion(Constants.playerAtlasRegionKey);
        TextureComponent textureComponent = new TextureComponent(textureRegion);

        //Add all components to entity
        entity.add(serializableComponent)
                .add(transformComponent)
                .add(bodyComponent)
                .add(playerComponent)
                .add(textureComponent);

        //Add entity to engine
        engine.addEntity(entity);
    }

    public void createPlatform(float x, float y, float w, float h){
        savedCommands.add("platform "+x+" "+y+" "+w+" "+h);

        //create Entity
        Entity entity = new Entity();

        //Serializable component
        SerializableComponent serializableComponent = new SerializableComponent("platform");
        serializableComponent.data.add(Float.toString(x));
        serializableComponent.data.add(Float.toString(y));
        serializableComponent.data.add(Float.toString(w));
        serializableComponent.data.add(Float.toString(h));

        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);
        TransformComponent transformComponent = new TransformComponent(position,w*pixels_per_meter, h*pixels_per_meter);

        //Box2dBody component
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
        B2dBodyComponent bodyComponent = new B2dBodyComponent(body);

        //Texture component
        //TextureRegion textureRegion = textureAtlas.findRegion("platform");
        //TextureComponent textureComponent = new TextureComponent(textureRegion);
        Texture blackTexture = new Texture("colors/black.png");
        TextureRegion tr = new TextureRegion(blackTexture);
        TextureComponent textureComponent = new TextureComponent(tr);

        //Add all components to entity
        entity.add(serializableComponent)
                .add(transformComponent)
                .add(bodyComponent)
                .add(textureComponent);

        //Add entity to engine
        engine.addEntity(entity);
    }

    public void createFinish(float x, float y, float w, float h){
        savedCommands.add("finish "+x+" "+y+" "+w+" "+h);

        //create Entity
        Entity entity = new Entity();

        //Serializable component
        SerializableComponent serializableComponent = new SerializableComponent("finish");
        serializableComponent.data.add(Float.toString(x));
        serializableComponent.data.add(Float.toString(y));
        serializableComponent.data.add(Float.toString(w));
        serializableComponent.data.add(Float.toString(h));

        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);
        TransformComponent transformComponent = new TransformComponent(position,w*pixels_per_meter, h*pixels_per_meter);


        //Box2dBody component
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
        B2dBodyComponent bodyComponent = new B2dBodyComponent(body);

        //Add all components to entity
        entity.add(serializableComponent)
                .add(transformComponent)
                .add(bodyComponent);

        //Add entity to engine
        engine.addEntity(entity);
    }

    public void parseEntityFromString(String command) {
        String fullCommand = command;
        try {
            if (command.startsWith("player")) {
                command = command.substring(7);
                String[] args = command.split(" ");

                float x = Float.parseFloat(args[0]);
                float y = Float.parseFloat(args[1]);

                createPlayer(x, y);
            } else if (command.startsWith("platform")) {
                command = command.substring(9);
                String[] args = command.split(" ");


                float x = Float.parseFloat(args[0]);
                float y = Float.parseFloat(args[1]);
                float w = Float.parseFloat(args[2]);
                float h = Float.parseFloat(args[3]);

                createPlatform(x, y, w, h);

            } else if (command.startsWith("finish")) {
                command = command.substring(7);
                String[] args = command.split(" ");
                float x = Float.parseFloat(args[0]);
                float y = Float.parseFloat(args[1]);
                float w = Float.parseFloat(args[2]);
                float h = Float.parseFloat(args[3]);

                createFinish(x, y, w, h);
            }
        } catch (NumberFormatException nfe){
            System.out.println("[ENTITY FACTORY] Failed to parse command into entity. Command: " + fullCommand);
        }
    }
}
