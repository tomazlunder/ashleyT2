package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
    public Vector3 position;
    public Vector3 oldPosition;

    public Vector2 scale;
    public float rotation;

    public TransformComponent(Vector3 position, Vector2 scale, float rotation){
        this.position = position.cpy();
        this.oldPosition = position.cpy();

        this.scale = scale.cpy();
        this.rotation = rotation;
    }

    public TransformComponent(Vector3 position){
        new TransformComponent(position, new Vector2(1,1), 0f);
    }


}
