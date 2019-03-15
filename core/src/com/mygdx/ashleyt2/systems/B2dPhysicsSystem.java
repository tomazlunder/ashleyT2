package com.mygdx.ashleyt2.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.TransformComponent;


public class B2dPhysicsSystem extends IteratingSystem {
    // create variables to stabilize speed
    private static final float MAX_STEP_TIME = 1 / 45f;
    private static float accumulator = 0f;
    // variable for our box2d world and bodies
    private World world;
    private Array<Entity> bodiesQueue;
    // component mappers
    private ComponentMapper<B2dBodyComponent> bm = ComponentMapper.getFor(B2dBodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    float pixels_per_meter;
    float pixels_to_meters;

    @SuppressWarnings("unchecked")
    public B2dPhysicsSystem(World world, float pixels_per_meter) {
        // System for all Entities that have B2dBodyComponent and TransformComponent
        super(Family.all(B2dBodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<Entity>();

        this.pixels_per_meter = pixels_per_meter;
        this.pixels_to_meters = 1.0f / pixels_per_meter;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if (accumulator >= MAX_STEP_TIME) {
            world.step(MAX_STEP_TIME, 6, 2);
            accumulator -= MAX_STEP_TIME;

            //Loop through all Entities and update our components
            for (Entity entity : bodiesQueue) {
                // get components
                TransformComponent tfm = tm.get(entity);
                B2dBodyComponent bodyComp = bm.get(entity);
                // get position from body
                Vector2 position = bodyComp.body.getPosition().cpy();
                // update our transform to match body position

                position.scl(pixels_per_meter);
                tfm.position = new Vector3(position.x, position.y, tfm.position.z);
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
            }
        }
        // empty queue
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // add Items to queue
        bodiesQueue.add(entity);
    }
}
