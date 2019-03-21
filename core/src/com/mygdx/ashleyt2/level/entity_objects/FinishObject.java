package com.mygdx.ashleyt2.level.entity_objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.components.*;
import com.mygdx.ashleyt2.components.SerializableComponent;

import java.util.ArrayList;

public class FinishObject extends SerializableObject {
    public float x;
    public float y;

    public float w;
    public float h;

    public int argLen = 4;

    public FinishObject(ArrayList<String> args) {
        super(args);
        this.name = "finish";
        this.type = SerializableObjectType.FINISH;
    }

    public FinishObject(float x, float y, float w, float h){
        super(new ArrayList<String>());
        this.type = SerializableObjectType.PLAYER;

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public boolean init(ArrayList<String> args){
        try{
            x = Float.parseFloat(args.get(0));
            y = Float.parseFloat(args.get(1));
            w = Float.parseFloat(args.get(2));
            h = Float.parseFloat(args.get(3));


            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addToEngine(Engine engine, World world, float pixels_per_meter){
        //create Entity
        Entity entity = new Entity();

        //Serializable component
        SerializableComponent serializableComponent = new SerializableComponent();
        serializableComponent.serializableObject = this;

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

    @Override
    public void updateFromEntiy(Entity e) {
        //TODO:
        B2dBodyComponent bodyComponent = e.getComponent(B2dBodyComponent.class);
        this.x = bodyComponent.body.getPosition().x;
        this.y = bodyComponent.body.getPosition().y;
    }

    @Override
    public String toString() {
        return "finish " + x + " " + y + " " + w + " " + h;
    }

    @Override
    public ArrayList<String> getDataList() {
        ArrayList<String> data = new ArrayList<String>();
        data.add(Float.toString(x));
        data.add(Float.toString(y));
        data.add(Float.toString(w));
        data.add(Float.toString(h));

        return data;
    }

    @Override
    public void updateEntity(Entity e) {

    }

}
