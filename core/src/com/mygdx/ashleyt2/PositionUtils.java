package com.mygdx.ashleyt2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class PositionUtils {
    Vector3 getMousePosInGameWorld(OrthographicCamera camera) {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
}
