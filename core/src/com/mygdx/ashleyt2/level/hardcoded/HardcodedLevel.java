package com.mygdx.ashleyt2.level.hardcoded;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.ashleyt2.level.EntityFactory;
import com.mygdx.ashleyt2.level.Level;

import java.util.ArrayList;

public class HardcodedLevel extends Level {

    private static float WORLD_WIDTH = 32;
    private static float WORLD_HEIGHT = 18;


    public HardcodedLevel() {
        super(WORLD_WIDTH, WORLD_HEIGHT, new ArrayList<String>());

    }

    @Override
    public void loadSerializedObjects(Engine e, World world, float pixels_per_meter) {
        EntityFactory entityFactory = new EntityFactory(e, world, pixels_per_meter);

        float edgeWidth = 0.5f;
        //top edge
        entityFactory.createPlatform(WORLD_WIDTH / 2, WORLD_HEIGHT - (edgeWidth / 2), WORLD_WIDTH, edgeWidth);

        //bottom edge
        entityFactory.createPlatform(WORLD_WIDTH / 2, edgeWidth / 2, WORLD_WIDTH, edgeWidth);

        //left edge
        entityFactory.createPlatform(edgeWidth / 2, WORLD_HEIGHT / 2, edgeWidth, WORLD_HEIGHT - (edgeWidth * 2));

        //right edge
        entityFactory.createPlatform(WORLD_WIDTH - (edgeWidth / 2), WORLD_HEIGHT / 2, edgeWidth, WORLD_HEIGHT - (edgeWidth * 2));

        float platformW1 = 2;
        float platfowmH1 = 1;


        //finish platform

        float finishWH = 0.3f;
        entityFactory.createFinish(WORLD_WIDTH / 5, WORLD_HEIGHT / 2 + (finishWH / 2) + platfowmH1 / 2, finishWH, finishWH);

        //random platform
        entityFactory.createPlatform((WORLD_WIDTH / 5) * 0, WORLD_HEIGHT / 2, platformW1, platfowmH1);
        entityFactory.createPlatform((WORLD_WIDTH / 5) * 1, WORLD_HEIGHT / 2, platformW1, platfowmH1);
        entityFactory.createPlatform((WORLD_WIDTH / 5) * 2, WORLD_HEIGHT / 2, platformW1, platfowmH1);
        entityFactory.createPlatform((WORLD_WIDTH / 5) * 3, WORLD_HEIGHT / 2, platformW1, platfowmH1);
        entityFactory.createPlatform((WORLD_WIDTH / 5) * 4, WORLD_HEIGHT / 2, platformW1, platfowmH1);
        entityFactory.createPlatform((WORLD_WIDTH / 5) * 5, WORLD_HEIGHT / 2, platformW1, platfowmH1);


        //"player"
        entityFactory.createPlayer(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

        //save as out.
    }
}
