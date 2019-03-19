package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

public class B2dBodyComponent implements Component {
    public Body body;

    public B2dBodyComponent(Body body){
        this.body = body;
    }
}