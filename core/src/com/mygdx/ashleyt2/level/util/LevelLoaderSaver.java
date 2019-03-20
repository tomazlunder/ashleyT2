package com.mygdx.ashleyt2.level.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.ashleyt2.level.Level;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

        for(String command : level.serializedObjects){
            file.writeString(command, true);
            file.writeString("\n", true);
        }
    }

    public static Level loadFileAsLevel(String path){
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            ArrayList<String> serializedObjects = new ArrayList<String>();
            String line;
            float width = 0;
            float height = 0;

            int i = 0;
            while((line = in.readLine()) != null){
                if(line.startsWith("player") || line.startsWith("platform") || line.startsWith("finish")){
                    serializedObjects.add(line);
                }
                else if(i == 0){
                    width = Float.parseFloat(line);
                } else if (i == 1){
                    height = Float.parseFloat(line);
                }
                i++;
            }

            in.close();

            return new Level(width,height,serializedObjects);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
