package com.mygdx.ashleyt2.level.entity_objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.components.*;
import com.mygdx.ashleyt2.level.Constants;
import com.mygdx.ashleyt2.components.SerializableComponent;

import java.util.ArrayList;

public class PlayerObject extends SerializableObject {
    public float x;
    public float y;

    public int argLen = 2;

    public PlayerObject(ArrayList<String> args) {
        super(args);
        this.name = "player";
        this.type = SerializableObjectType.PLAYER;
    }

    public PlayerObject(float x, float y){
        super(new ArrayList<String>());
        this.type = SerializableObjectType.PLAYER;

        this.x = x;
        this.y = y;
    }

    public boolean init(ArrayList<String> args){
        try{
            x = Float.parseFloat(args.get(0));
            y = Float.parseFloat(args.get(1));

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void addToEngine(Engine engine, World world, float pixels_per_meter){
        //create entity
        Entity entity = new Entity();

        //Serializable component
        SerializableComponent serializableComponent = new SerializableComponent();
        serializableComponent.serializableObject = this;

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
        fixtureDef.density = Constants.playerDensity;
        fixtureDef.friction = Constants.playerFriction;
        fixtureDef.restitution = Constants.playerRestitution;

        body.createFixture(fixtureDef);

        B2dBodyComponent bodyComponent = new B2dBodyComponent(body);

        //Player component
        PlayerComponent playerComponent = new PlayerComponent();

        //Texture component
        //TODO: ASSET MANAGER
        TextureAtlas textureAtlas;
        textureAtlas = new TextureAtlas("atlas/game.atlas");

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

    @Override
    public void updateFromEntiy(Entity e) {
        //TODO:
        B2dBodyComponent bodyComponent = e.getComponent(B2dBodyComponent.class);
        this.x = bodyComponent.body.getPosition().x;
        this.y = bodyComponent.body.getPosition().y;
    }

    @Override
    public String toString() {
        return "player " + x + " " + y;
    }

    @Override
    public ArrayList<String> getDataList() {
        ArrayList<String> data = new ArrayList<String>();
        data.add(Float.toString(x));
        data.add(Float.toString(y));

        return data;
    }

    @Override
    public void updateEntity(Entity e) {
        B2dBodyComponent bodyComponent = e.getComponent(B2dBodyComponent.class);
        bodyComponent.body.setTransform(x, y, 0);
    }

}
