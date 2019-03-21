package com.mygdx.ashleyt2.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.ashleyt2.ui.screens.LevelEditorScreen;

import java.util.ArrayList;

public class EntityCreatorDialog extends Dialog {

    private Skin skin;

    public LevelEditorScreen levelEditorScreen;

    public Vector2 clickPos;

    public String whatToCreate;

    ArrayList<Label> labels;

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public EntityCreatorDialog(String title, Skin skin, Vector2 clickPos, LevelEditorScreen levelEditorScreen) {
        super(title, skin);
        levelEditorScreen.dialogOpen = true;
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;

        this.levelEditorScreen = levelEditorScreen;
        this.clickPos = new Vector2(clickPos);

        this.labels = new ArrayList<Label>();

        buildList();
    }


    private void buildList() {
        ScrollPane pane = new ScrollPane(null, skin);
        final Table table = new Table().top().left();
        table.defaults().left();
        ClickListener labelClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for(Label l : labels){
                    l.setColor(Color.WHITE);
                }

                Label target = (Label) event.getTarget();
                target.setColor(Color.RED);

                whatToCreate = target.getName();
            }
        };
        table.row();
        Label label = new Label("player", skin);
        label.setName("player");
        label.addListener(labelClickListener);
        labels.add(label);
        table.add(label).expandX().fillX();

        table.row();
        label = new Label("platform", skin);
        label.setName("platform");
        label.addListener(labelClickListener);
        labels.add(label);
        table.add(label).expandX().fillX();

        table.row();
        label = new Label("finish", skin);
        label.setName("finish");
        label.addListener(labelClickListener);
        labels.add(label);
        table.add(label).expandX().fillX();

        pane.setWidget(table);
        this.getContentTable().reset();
        this.getContentTable().add(pane).maxHeight(300).expand().fill();
    }


}
