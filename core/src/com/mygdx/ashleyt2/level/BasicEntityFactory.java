package com.mygdx.ashleyt2.level;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ashleyt2.components.B2dBodyComponent;
import com.mygdx.ashleyt2.components.SerializableComponent;
import com.mygdx.ashleyt2.components.TextureComponent;
import com.mygdx.ashleyt2.components.TransformComponent;

public class BasicEntityFactory {
    public void spawnDamageBall(Engine engine, World world,
                                Vector2 position, Vector2 direction){
        /*//create Entity
        Entity entity = new Entity();

        //Transform component (transform positions into world coordinates)
        Vector3 position = new Vector3(x*pixels_per_meter,y*pixels_per_meter,1);
        TransformComponent transformComponent = new TransformComponent(position,w*pixels_per_meter, h*pixels_per_meter);

        //Box2dBody component
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/2,h/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);
        B2dBodyComponent bodyComponent = new B2dBodyComponent(body);

        //Texture component
        //TextureRegion textureRegion = textureAtlas.findRegion("platform");
        //TextureComponent textureComponent = new TextureComponent(textureRegion);
        Texture blackTexture = new Texture("colors/black.png");
        TextureRegion tr = new TextureRegion(blackTexture);
        TextureComponent textureComponent = new TextureComponent(tr);

        //Add all components to entity
        entity.add(transformComponent)
                .add(bodyComponent)
                .add(textureComponent);

        //Add entity to engine
        engine.addEntity(entity);*/
    }
}
