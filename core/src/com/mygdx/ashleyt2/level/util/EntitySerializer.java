package com.mygdx.ashleyt2.level.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.SerializableComponent;

import java.util.ArrayList;

public class EntitySerializer {
    private static ComponentMapper<SerializableComponent> serializableMapper;
    private static ComponentMapper<B2dBodyComponent> bodyMapper;


    public static ArrayList<String> serializeWorld(Engine engine){
        ArrayList<String> list = new ArrayList<String>();

        serializableMapper = ComponentMapper.getFor(SerializableComponent.class);
        bodyMapper = ComponentMapper.getFor(B2dBodyComponent.class);

        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(SerializableComponent.class).get());

        SerializableComponent serializableComponent;
        B2dBodyComponent bodyComponent;
        String serialized;

        for(Entity entity : entities){
            serialized = "";

            serializableComponent = serializableMapper.get(entity);
            if(serializableComponent.name.equals("player")){
                bodyComponent = bodyMapper.get(entity);
                Vector2 position = bodyComponent.body.getPosition();

                serialized = "player " + position.x + " " + position.y;
            }
            else if(serializableComponent.name.equals("platform")){
                bodyComponent = bodyMapper.get(entity);
                Vector2 position = bodyComponent.body.getPosition();

                serialized = "platform " + position.x + " " + position.y + " " + serializableComponent.data.get(2) + " " + serializableComponent.data.get(3);
            }
            if(serializableComponent.name.equals("finish")){
                bodyComponent = bodyMapper.get(entity);
                Vector2 position = bodyComponent.body.getPosition();

                serialized = "finish " + position.x + " " + position.y + " " + serializableComponent.data.get(2) + " " + serializableComponent.data.get(3);
            }

            if(!serialized.equals("")){
                list.add(serialized);
            }
        }

        return list;
    }
}
