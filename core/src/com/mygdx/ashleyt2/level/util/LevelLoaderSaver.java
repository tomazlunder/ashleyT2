package com.mygdx.ashleyt2.level.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.ashleyt2.level.Level;
import com.mygdx.ashleyt2.level.entity_objects.FinishObject;
import com.mygdx.ashleyt2.level.entity_objects.PlatformObject;
import com.mygdx.ashleyt2.level.entity_objects.PlayerObject;
import com.mygdx.ashleyt2.level.entity_objects.SerializableObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LevelLoaderSaver {

    public static void saveLevelAsFile(String path, Level level){
        String savePath;
        if(path == null || path.equals("")){
            savePath = "level/gen/factoryOUT.lvl";
        } else {
            savePath = path;
        }
        FileHandle file = Gdx.files.local(savePath);
        file.writeString("",false);
        file.writeString(level.width+"\n",true);
        file.writeString(level.height+"\n",true);

        for(SerializableObject so : level.serializableObjects){
            file.writeString(so.toString(), true);
            file.writeString("\n", true);
        }
    }

    public static Level loadFileAsLevel(String path){
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            ArrayList<SerializableObject> sos = new ArrayList<SerializableObject>();
            String line;
            float width = 0;
            float height = 0;

            int i = 0;
            while((line = in.readLine()) != null){
                if(line.startsWith("player")){
                    sos.add(new PlayerObject(getArgsFromLine(line)));
                }
                else if(line.startsWith("platform")){
                    sos.add(new PlatformObject(getArgsFromLine(line)));

                }
                else if(line.startsWith("finish")) {
                    sos.add(new FinishObject(getArgsFromLine(line)));
                }
                else if(i == 0){
                    width = Float.parseFloat(line);
                } else if (i == 1){
                    height = Float.parseFloat(line);
                }
                i++;
            }

            in.close();

            return new Level(width,height,sos);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> getArgsFromLine(String line){
        String[] split = line.split(" ");

        ArrayList<String> args = new ArrayList( Arrays.asList( split ) );

        args.remove(0);
        return args;
    }
}
