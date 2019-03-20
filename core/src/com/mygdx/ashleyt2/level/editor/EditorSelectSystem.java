package com.mygdx.ashleyt2.level.editor;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.SerializableComponent;
import com.mygdx.ashleyt2.ui.screens.LevelEditorScreen;
import com.mygdx.ashleyt2.ui.widgets.EntityEditorDialog;

public class EditorSelectSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<SerializableComponent> serializableMapper = ComponentMapper.getFor(SerializableComponent.class);
    private ComponentMapper<B2dBodyComponent> bodyMapper = ComponentMapper.getFor(B2dBodyComponent.class);

    private float pixels_per_meter;
    private float pixels_to_meters;

    Engine engine;
    LevelEditorScreen levelEditorScreen;

    Stage stage;
    Skin skin;

    public EditorSelectSystem(Stage stage, Skin skin, float pixels_per_meter, LevelEditorScreen levelEditorScreen) {
        this.pixels_per_meter = pixels_per_meter;
        this.pixels_to_meters = 1.0f / (float) pixels_per_meter;

        this.levelEditorScreen = levelEditorScreen;
        this.stage = stage;
        this.skin = skin;
    }


    @Override
    public void addedToEngine(Engine engine) {
        engine = engine;
        entities = engine.getEntitiesFor(Family.all(SerializableComponent.class, B2dBodyComponent.class).get());

    }

    @Override
    public void update(float deltaTime) {
        B2dBodyComponent bodyComponent;
        //levelEditorScreen.toRemove = new ArrayList<Entity>();

        if(Gdx.input.justTouched() && !levelEditorScreen.dialogOpen) {
            for (Entity e : entities) {
                bodyComponent = bodyMapper.get(e);

                for (Fixture f : bodyComponent.body.getFixtureList()) {
                    if ( f.testPoint(getMousePosInGameWorld())){
                        System.out.println("ENTITY SELECTED");
                        //levelEditorScreen.toRemove.add(e);

                        Gdx.input.setInputProcessor(stage);
                        levelEditorScreen.dialogOpen = true;
                        //Dialog to change settings or remove the selectec component
                        EntityEditorDialog edit = new EntityEditorDialog("Edit window", skin, e) {
                            @Override
                            protected void result(Object object) {
                                if (object.equals("OK")) {
                                    levelEditorScreen.toRemove.add(this.entity);
                                    levelEditorScreen.toExecute.add(this.generateCommand());
                                }
                                remove();
                                levelEditorScreen.dialogOpen = false;
                            }
                        };
                        edit.show(stage);

                        return;
                    }
                }
            }
        }
    }

    Vector2 getMousePosInGameWorld() {
        return new Vector2(Gdx.input.getX()*pixels_to_meters, (Gdx.graphics.getHeight()-Gdx.input.getY())*pixels_to_meters) ;
    }
}
