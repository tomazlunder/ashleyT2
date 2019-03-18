package com.mygdx.ashleyt2.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.World;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParsedLevel implements LevelInterface {

    String path;
    private float WORLD_WIDTH;
    private float WORLD_HEIGHT;

    public ParsedLevel(String path){
        this.path = path;

        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            String line;
            line = in.readLine();
            this.WORLD_WIDTH = Float.parseFloat(line);

            line = in.readLine();
            this.WORLD_HEIGHT = Float.parseFloat(line);

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public float getWorldWidth() {
        return WORLD_WIDTH;
    }

    @Override
    public float getWorldHeight() {
        return WORLD_HEIGHT;
    }

    @Override
    public void addEntities(Engine e, World world, float pixels_per_meter) {
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(e, world, pixels_per_meter);

        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            String line;

            while((line = in.readLine()) != null){
                if(line.startsWith("player")){
                    line = line.substring(7);
                    String[] args = line.split(" ");
                    float x = Float.parseFloat(args[0]);
                    float y = Float.parseFloat(args[1]);

                    levelObjectFactory.createPlayer(x,y);
                }

                else if(line.startsWith("platform")){
                    line = line.substring(9);
                    String[] args = line.split(" ");
                    float x = Float.parseFloat(args[0]);
                    float y = Float.parseFloat(args[1]);
                    float w = Float.parseFloat(args[2]);
                    float h = Float.parseFloat(args[3]);

                    levelObjectFactory.createPlatform(x,y, w, h);

                }
                else if(line.startsWith("finish")){
                    line = line.substring(7);
                    String[] args = line.split(" ");
                    float x = Float.parseFloat(args[0]);
                    float y = Float.parseFloat(args[1]);
                    float w = Float.parseFloat(args[2]);
                    float h = Float.parseFloat(args[3]);

                    levelObjectFactory.createFinish(x,y, w, h);
                }
            }

            in.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
