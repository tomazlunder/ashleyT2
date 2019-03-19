package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
    //PIXEL WORLD coordinates
    public Vector3 position;

    //Transformation settings
    public float width, height;
    public Vector2 scale;
    public float rotation;

    public TransformComponent(Vector3 position, float width, float height){
        this.position = position.cpy();

        this.width = width;
        this.height = height;

        this.scale = new Vector2(1f,1f);
        this.rotation = 0;
    }
}
