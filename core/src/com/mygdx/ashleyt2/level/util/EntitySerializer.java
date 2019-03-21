package com.mygdx.ashleyt2.level.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.ashleyt2.level.entity_objects.SerializableObject;
import com.mygdx.ashleyt2.components.SerializableComponent;

import java.util.ArrayList;

public class EntitySerializer {
    private static ComponentMapper<SerializableComponent> serializableMapper;

    //TODO: REMOVE OR MOVE THIS FUNCTION TO... LEVEL?

    public static ArrayList<SerializableObject> serializeWorld(Engine engine){
        ArrayList<SerializableObject> list = new ArrayList<SerializableObject>();

        serializableMapper = ComponentMapper.getFor(SerializableComponent.class);

        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(SerializableComponent.class).get());

        SerializableComponent serializableComponent;

        for(Entity entity : entities){
            serializableComponent = serializableMapper.get(entity);
            serializableComponent.serializableObject.updateFromEntiy(entity);
            list.add(serializableComponent.serializableObject);
        }

        return list;
    }
}
