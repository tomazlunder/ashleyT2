package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
    public TextureRegion textureRegion;

    public TextureComponent(TextureRegion textureRegion){
        this.textureRegion = textureRegion;
    }
}
