package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class TextureComponent implements Component {
    TextureRegion textureRegion;

    public TextureComponent(TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }
}
