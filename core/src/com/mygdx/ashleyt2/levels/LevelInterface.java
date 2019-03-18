package com.mygdx.ashleyt2.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;

public interface LevelInterface {

    float getWorldWidth();
    float getWorldHeight();

    void addEntities(Engine e, World world, float pixels_per_meter);
}
