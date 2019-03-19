package com.mygdx.ashleyt2.levels;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParsedLevel implements LevelInterface {

    String path;
    private float WORLD_WIDTH;
    private float WORLD_HEIGHT;

    ArrayList<String> commands;

    public ParsedLevel(String path){
        this.path = path;
        this.commands = new ArrayList<String>();
        this.parseAsCommands();
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
    public void addEntities(Engine engine, World world, float pixels_per_meter) {
        executeCommands(engine,world,pixels_per_meter);
    }

    private void parseAsCommands(){
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));

            String line;

            int i = 0;
            while((line = in.readLine()) != null){
                if(line.startsWith("player") || line.startsWith("platform") || line.startsWith("finish")){
                    commands.add(line);
                }
                else if(i == 0){
                    WORLD_WIDTH = Float.parseFloat(line);
                } else if (i == 1){
                    WORLD_HEIGHT = Float.parseFloat(line);
                }
                i++;
            }

            in.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void executeCommands(Engine engine, World world, float pixels_per_meter){
        LevelObjectFactory levelObjectFactory = new LevelObjectFactory(engine, world, pixels_per_meter);

        for(String command : commands){
            if(command.startsWith("player")){
                command = command.substring(7);
                String[] args = command.split(" ");
                float x = Float.parseFloat(args[0]);
                float y = Float.parseFloat(args[1]);

                levelObjectFactory.createPlayer(x,y);
            }

            else if(command.startsWith("platform")){
                command = command.substring(9);
                String[] args = command.split(" ");
                float x = Float.parseFloat(args[0]);
                float y = Float.parseFloat(args[1]);
                float w = Float.parseFloat(args[2]);
                float h = Float.parseFloat(args[3]);

                levelObjectFactory.createPlatform(x,y, w, h);

            }
            else if(command.startsWith("finish")){
                command = command.substring(7);
                String[] args = command.split(" ");
                float x = Float.parseFloat(args[0]);
                float y = Float.parseFloat(args[1]);
                float w = Float.parseFloat(args[2]);
                float h = Float.parseFloat(args[3]);

                levelObjectFactory.createFinish(x,y, w, h);
            }
        }
    }
    
}
