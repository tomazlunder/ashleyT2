package com.mygdx.ashleyt2.level.serializable_objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public abstract class SerializableObject {
    public SerializableObjectType type;
    public String name;
    protected int argLen;

    //INIT WITH A LIST OF STRING ARGS... COMMON FOR ALL SERIALIZED OBJECT
    //Each SO can also have its own constructor
    public SerializableObject(ArrayList<String> args){
        if(args.size() != 0) this.init(args);
    }
    public abstract boolean init(ArrayList<String> args);

    //Creates and entity and adds it to given engine
    public abstract Entity addToEngine(Engine e, World w);

    public abstract void updateFromEntiy(Entity e);

    public abstract String toString();

    public abstract ArrayList<String> getDataList();

    public abstract void updateEntity(Entity e);
}
