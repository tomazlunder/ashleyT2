package com.mygdx.ashleyt2.level.hardcoded;

import com.mygdx.ashleyt2.level.Level;
import com.mygdx.ashleyt2.level.entity_objects.FinishObject;
import com.mygdx.ashleyt2.level.entity_objects.PlatformObject;
import com.mygdx.ashleyt2.level.entity_objects.PlayerObject;
import com.mygdx.ashleyt2.level.entity_objects.SerializableObject;

import java.util.ArrayList;

public class HardcodedLevel extends Level {

    private static float WORLD_WIDTH = 32;
    private static float WORLD_HEIGHT = 18;


    public HardcodedLevel() {
        //super(WORLD_WIDTH, WORLD_HEIGHT, new ArrayList<String>());
        super(WORLD_WIDTH, WORLD_HEIGHT, new ArrayList<SerializableObject>());

        float edgeWidth = 0.5f;

        serializableObjects.add(new PlatformObject(WORLD_WIDTH / 2, WORLD_HEIGHT - (edgeWidth / 2), WORLD_WIDTH, edgeWidth));
        serializableObjects.add(new PlatformObject(WORLD_WIDTH / 2, edgeWidth / 2, WORLD_WIDTH, edgeWidth));
        serializableObjects.add(new PlatformObject(edgeWidth / 2, WORLD_HEIGHT / 2, edgeWidth, WORLD_HEIGHT - (edgeWidth * 2)));
        serializableObjects.add(new PlatformObject(WORLD_WIDTH - (edgeWidth / 2), WORLD_HEIGHT / 2, edgeWidth, WORLD_HEIGHT - (edgeWidth * 2)));


        float platformW1 = 2;
        float platfowmH1 = 1;

        float finishWH = 0.3f;
        serializableObjects.add(new FinishObject(WORLD_WIDTH / 5, WORLD_HEIGHT / 2 + (finishWH / 2) + platfowmH1 / 2, finishWH, finishWH));

        serializableObjects.add(new PlatformObject((WORLD_WIDTH / 5) * 0, WORLD_HEIGHT / 2, platformW1, platfowmH1));
        serializableObjects.add(new PlatformObject((WORLD_WIDTH / 5) * 1, WORLD_HEIGHT / 2, platformW1, platfowmH1));
        serializableObjects.add(new PlatformObject((WORLD_WIDTH / 5) * 2, WORLD_HEIGHT / 2, platformW1, platfowmH1));
        serializableObjects.add(new PlatformObject((WORLD_WIDTH / 5) * 3, WORLD_HEIGHT / 2, platformW1, platfowmH1));
        serializableObjects.add(new PlatformObject((WORLD_WIDTH / 5) * 4, WORLD_HEIGHT / 2, platformW1, platfowmH1));
        serializableObjects.add(new PlatformObject((WORLD_WIDTH / 5) * 5, WORLD_HEIGHT / 2, platformW1, platfowmH1));

        serializableObjects.add(new PlayerObject(WORLD_WIDTH / 2, WORLD_HEIGHT / 2));

    }


}
