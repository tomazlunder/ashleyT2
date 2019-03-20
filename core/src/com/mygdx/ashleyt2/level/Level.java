package com.mygdx.ashleyt2.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.ashleyt2.components.SerializableComponent;
import com.mygdx.ashleyt2.level.util.EntitySerializer;

import java.util.ArrayList;

public class Level {
    private static ComponentMapper<SerializableComponent> serializableMapper;

    public float width;
    public float height;
    public ArrayList<String> serializedObjects;

    public Level(float width, float height, ArrayList<String> serializedObjects){
        this.width = width;
        this.height = height;
        this.serializedObjects = new ArrayList<String>(serializedObjects);
    }

    public void loadSerializedObjects(Engine e, World world, float pixels_per_meter){
        EntityFactory entityFactory = new EntityFactory(e, world, pixels_per_meter);

        for(String command : serializedObjects) {
            entityFactory.parseEntityFromString(command);
        }
    }

    public void updateLevelFromEngine(Engine engine){
        this.serializedObjects = new ArrayList<String>(EntitySerializer.serializeWorld(engine));
    }







}
