package com.mygdx.ashleyt2.systems.editor;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.ashleyt2.components.SerializableComponent;
import com.mygdx.ashleyt2.ui.screens.LevelEditorScreen;


public class EditorEntityUpdaterSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<SerializableComponent> serializableMapper = ComponentMapper.getFor(SerializableComponent.class);

    LevelEditorScreen levelEditorScreen;

    public EditorEntityUpdaterSystem(LevelEditorScreen levelEditorScreen) {
        this.levelEditorScreen = levelEditorScreen;
    }


    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SerializableComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (final Entity e : entities) {
            SerializableComponent serializableComponent = serializableMapper.get(e);
            if(serializableComponent.delete){
                levelEditorScreen.toRemove.add(e);
            }
            else if(serializableComponent.changed){
                levelEditorScreen.toRemove.add(e);
                levelEditorScreen.toAdd.add(serializableComponent.serializableObject);
            }
        }
    }

}


