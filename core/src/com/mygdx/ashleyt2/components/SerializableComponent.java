package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.ashleyt2.level.serializable_objects.SerializableObject;

public class SerializableComponent implements Component {
    public SerializableObject serializableObject;
    public boolean changed;
    public boolean delete;


    public SerializableComponent() {
        changed = false;
        delete = false;
    }
}
