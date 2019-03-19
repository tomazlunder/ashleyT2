package com.mygdx.ashleyt2.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.ashleyt2.ZComparator;
import com.mygdx.ashleyt2.components.TextureComponent;
import com.mygdx.ashleyt2.components.TransformComponent;

/**
 * Renders all entites with a TextureComponent.
 */
public class RenderingSystem extends SortedIteratingSystem {
    //Component mappers
    private ComponentMapper<TextureComponent> texureMap = ComponentMapper.getFor(TextureComponent.class);
    private ComponentMapper<TransformComponent> transformMap = ComponentMapper.getFor(TransformComponent.class);

    //Sprite batch
    private SpriteBatch batch;


    @SuppressWarnings("unchecked")
    public RenderingSystem(SpriteBatch batch){
        super(Family.all(TransformComponent.class,TextureComponent.class).get(), new ZComparator());
        this.batch = batch;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent textureComponent = texureMap.get(entity);
        TransformComponent transformComponent = transformMap.get(entity);

        batch.draw(textureComponent.textureRegion,
                transformComponent.position.x - transformComponent.width/2,
                transformComponent.position.y-transformComponent.height/2,
                textureComponent.textureRegion.getRegionWidth()/2,
                textureComponent.textureRegion.getRegionHeight()/2,
                transformComponent.width,
                transformComponent.height,
                transformComponent.scale.x,
                transformComponent.scale.y,
                transformComponent.rotation);
    }

}

