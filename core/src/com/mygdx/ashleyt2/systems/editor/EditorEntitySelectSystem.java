package com.mygdx.ashleyt2.systems.editor;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.SerializableComponent;
import com.mygdx.ashleyt2.input.InputHandler;
import com.mygdx.ashleyt2.ui.screens.LevelEditorScreen;
import com.mygdx.ashleyt2.ui.widgets.EntityEditorDialog;

public class EditorEntitySelectSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<SerializableComponent> serializableMapper = ComponentMapper.getFor(SerializableComponent.class);
    private ComponentMapper<B2dBodyComponent> bodyMapper = ComponentMapper.getFor(B2dBodyComponent.class);

    LevelEditorScreen levelEditorScreen;

    Stage stage;
    Skin skin;

    public EditorEntitySelectSystem(Stage stage, Skin skin, LevelEditorScreen levelEditorScreen) {
        this.levelEditorScreen = levelEditorScreen;
        this.stage = stage;
        this.skin = skin;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SerializableComponent.class, B2dBodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        B2dBodyComponent bodyComponent;

        if(Gdx.input.justTouched() && !levelEditorScreen.dialogOpen && levelEditorScreen.editingMode == LevelEditorScreen.EditingMode.SELECT) {
            for (final Entity e : entities) {
                bodyComponent = bodyMapper.get(e);

                for (Fixture f : bodyComponent.body.getFixtureList()) {
                    if ( f.testPoint(InputHandler.getMousePosInGameWorld())){
                        System.out.println("ENTITY SELECTED");

                        Gdx.input.setInputProcessor(stage);
                        levelEditorScreen.dialogOpen = true;
                        //Dialog to change settings or remove the selectec component
                        EntityEditorDialog edit = new EntityEditorDialog("Edit window", skin, e) {
                            @Override
                            protected void result(Object object) {
                                if (object.equals("OK")) {
                                    changeSOwithArgs();
                                }
                                else if(object.equals("Remove")){
                                    removeEntity();
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
}
