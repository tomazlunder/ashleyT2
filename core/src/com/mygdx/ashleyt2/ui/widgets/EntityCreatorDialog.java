package com.mygdx.ashleyt2.ui.widgets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class EntityCreatorDialog extends Dialog {

    private Skin skin;

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public EntityCreatorDialog(String title, Skin skin) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;
    }

/*
    private void buildList() {
        ScrollPane pane = new ScrollPane(null, skin);
        Table table = new Table().top().left();
        table.defaults().left();
        ClickListener labelClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label target = (Label) event.getTarget();
                if (target.getName().equals("player")) {
                    setDirectory(directory.parent());
                } else {
                    FileHandle handle = directory.child(target.getName());
                    if (handle.isDirectory()) {
                        setDirectory(handle);
                    } else {
                        setFile(handle);
                    }
                }
            }
        };
        table.row();
        Label label = new Label("..", skin);
        label.setName("..");
        label.addListener(fileClickListener);
        table.add(label).expandX().fillX();
        for (FileHandle file : files) {
            table.row();
            label = new Label(file.name(), skin);
            label.setName(file.name());
            label.addListener(fileClickListener);
            table.add(label).expandX().fillX();
        }
        pane.setWidget(table);
        this.getContentTable().reset();
        this.getContentTable().add(pane).maxHeight(300).expand().fill();
    }
    */

}
