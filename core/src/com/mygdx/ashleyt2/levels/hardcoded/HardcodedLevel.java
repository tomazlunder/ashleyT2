package com.mygdx.ashleyt2.levels.hardcoded;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.ashleyt2.levels.LevelInterface;
import com.mygdx.ashleyt2.levels.LevelObjectFactory;

public class HardcodedLevel implements LevelInterface {

    public float WORLD_WIDTH = 32f;
    public float WORLD_HEIGHT = 18f;

    public HardcodedLevel(){

    }

    @Override
    public float getWorldWidth() {
        return WORLD_WIDTH;
    }

    @Override
    public float getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public void addEntities(Engine e, World world, float pixels_per_meter){
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(e,world,pixels_per_meter);

        float edgeWidth = 0.5f;
        //top edge
        levelObjectFactory.createPlatform(WORLD_WIDTH/2, WORLD_HEIGHT-(edgeWidth/2),WORLD_WIDTH,edgeWidth);

        //bottom edge
        levelObjectFactory.createPlatform(WORLD_WIDTH/2, edgeWidth/2, WORLD_WIDTH, edgeWidth);

        //left edge
        levelObjectFactory.createPlatform(edgeWidth/2, WORLD_HEIGHT/2, edgeWidth, WORLD_HEIGHT-(edgeWidth*2));

        //right edge
        levelObjectFactory.createPlatform(WORLD_WIDTH-(edgeWidth/2), WORLD_HEIGHT/2, edgeWidth, WORLD_HEIGHT-(edgeWidth*2));

        float platformW1 = 2;
        float platfowmH1 = 1;


        //finish platform

        float finishWH = 0.3f;
        levelObjectFactory.createFinish(WORLD_WIDTH/5, WORLD_HEIGHT/2 + (finishWH/2) + platfowmH1/2, finishWH, finishWH);

        //random platform
        levelObjectFactory.createPlatform((WORLD_WIDTH/5) * 0, WORLD_HEIGHT/2, platformW1, platfowmH1);
        levelObjectFactory.createPlatform((WORLD_WIDTH/5) * 1, WORLD_HEIGHT/2, platformW1, platfowmH1);
        levelObjectFactory.createPlatform((WORLD_WIDTH/5) * 2, WORLD_HEIGHT/2, platformW1, platfowmH1);
        levelObjectFactory.createPlatform((WORLD_WIDTH/5) * 3, WORLD_HEIGHT/2, platformW1, platfowmH1);
        levelObjectFactory.createPlatform((WORLD_WIDTH/5) * 4, WORLD_HEIGHT/2, platformW1, platfowmH1);
        levelObjectFactory.createPlatform((WORLD_WIDTH/5) * 5, WORLD_HEIGHT/2, platformW1, platfowmH1);


        //"player"
        levelObjectFactory.createPlayer(WORLD_WIDTH/2, WORLD_HEIGHT/2);

        //save as out.
        levelObjectFactory.saveCommandsAsFile("ni path");
    }
}
