package com.mygdx.ashleyt2.ui.widgets;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.ashleyt2.components.SerializableComponent;

public class EntityEditorDialog extends Dialog {

    private Skin skin;
    private ComponentMapper<SerializableComponent> serializableMapper;

    public Entity entity;

    private ArrayList<TextField> textFields;


    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }

    public EntityEditorDialog(String title, Skin skin, Entity entity) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();

        this.button("Remove", "Remove");
        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;
        this.serializableMapper = ComponentMapper.getFor(SerializableComponent.class);

        this.entity = entity;
        this.buildDialog();
    }

    private void buildDialog() {
        Table table = new Table().top().left();
        table.defaults().left();

        SerializableComponent serializableComponent = serializableMapper.get(entity);

        table.row();
        Label label = new Label(serializableComponent.serializableObject.name, skin);
        table.add(label).expandX().fillX();

        textFields = new ArrayList<TextField>();
        TextField textField;
        for (String data : serializableComponent.serializableObject.getDataList()) {
            table.row();
            textField = new TextField(data, skin);
            textFields.add(textField);
            table.add(textField).expandX().fillX();
        }
        this.getContentTable().reset();
        this.getContentTable().add(table).maxHeight(300).expand().fill();
    }

    //Retruns success status
    public boolean changeSOwithArgs() {
        ArrayList<String> args = new ArrayList<String>();
        SerializableComponent serializableComponent = serializableMapper.get(entity);

        for (TextField textField : textFields) {
            args.add(textField.getText());
        }

        boolean success = serializableComponent.serializableObject.init(args);
        if(success){
            serializableComponent.changed = true;
        }
        return success;
    }

    //Retruns success status
    public void removeEntity() {
        SerializableComponent serializableComponent = serializableMapper.get(entity);

        serializableComponent.delete = true;
    }
}
