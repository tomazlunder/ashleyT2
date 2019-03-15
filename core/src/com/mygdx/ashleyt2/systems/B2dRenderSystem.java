package com.mygdx.ashleyt2.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class B2dRenderSystem extends IteratingSystem {

    private Box2DDebugRenderer debugRenderer;
    private World world;
    private OrthographicCamera camera;

    private Matrix4 matrix4;


    public B2dRenderSystem(World world, OrthographicCamera camera, float pixels_per_meter){
        super(Family.all().get());
        debugRenderer = new Box2DDebugRenderer();
        this.world = world;
        this.camera = camera;

        this.matrix4 = new Matrix4(camera.combined);
        matrix4.scale(pixels_per_meter, pixels_per_meter, 1f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world, matrix4);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
