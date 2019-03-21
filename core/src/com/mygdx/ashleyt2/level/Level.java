package com.mygdx.ashleyt2.level;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.ashleyt2.components.SerializableComponent;
import com.mygdx.ashleyt2.level.entity_objects.SerializableObject;

import java.util.ArrayList;

public class Level {
    private static ComponentMapper<SerializableComponent> serializableMapper;

    public float width;
    public float height;
    public ArrayList<SerializableObject> serializableObjects;

    public Level(float width, float height, ArrayList<SerializableObject> serializableObjects){
        this.width = width;
        this.height = height;
        this.serializableObjects = new ArrayList<SerializableObject>(serializableObjects);
    }

    public void loadSerializedObjects(Engine engine, World world, float pixels_per_meter){
        for(SerializableObject so : serializableObjects) {
            so.addToEngine(engine,world,pixels_per_meter);
        }
    }

    public void updateLevelFromEngine(Engine engine){
        this.serializableObjects = new ArrayList<SerializableObject>();

        ImmutableArray<Entity> sos = engine.getEntitiesFor(Family.all(SerializableComponent.class).get());
        serializableMapper = ComponentMapper.getFor(SerializableComponent.class);

        SerializableComponent serializableComponent;
        for(Entity entity : sos){
            serializableComponent = serializableMapper.get(entity);
            serializableObjects.add(serializableComponent.serializableObject);
        }
    }
}
