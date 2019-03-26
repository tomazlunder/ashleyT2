package com.mygdx.ashleyt2.level.serializable_objects;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class BallSpawnerObject extends SerializableObject {

    public float x;
    public float y;
    public float initialRotation;
    public float roatationSpeed;
    public float spawnInterval;

    public int argLen = 5;

    public BallSpawnerObject(ArrayList<String> args) {
        super(args);
    }

    @Override
    public boolean init(ArrayList<String> args) {
        return false;
    }

    @Override
    public Entity addToEngine(Engine e, World w) {
        return null;
    }

    @Override
    public void updateFromEntiy(Entity e) {

    }

    @Override
    public String toString() {
        return "ballSpawner " + " " + x + " " + y + " " + initialRotation + " " + roatationSpeed + " " + spawnInterval;
    }

    @Override
    public ArrayList<String> getDataList() {
        return null;
    }

    @Override
    public void updateEntity(Entity e) {

    }
}
