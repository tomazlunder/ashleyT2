package com.mygdx.ashleyt2.ui.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.input.InputHandler;
import com.mygdx.ashleyt2.level.Constants;
import com.mygdx.ashleyt2.level.Level;
import com.mygdx.ashleyt2.level.serializable_objects.FinishSO;
import com.mygdx.ashleyt2.level.serializable_objects.PlatformSO;
import com.mygdx.ashleyt2.level.serializable_objects.PlayerSO;
import com.mygdx.ashleyt2.systems.editor.EditorEntityUpdaterSystem;
import com.mygdx.ashleyt2.systems.editor.EditorEntitySelectSystem;
import com.mygdx.ashleyt2.level.serializable_objects.SerializableObject;
import com.mygdx.ashleyt2.level.util.LevelLoaderSaver;
import com.mygdx.ashleyt2.systems.RenderingSystem;
import com.mygdx.ashleyt2.ui.widgets.EntityCreatorDialog;

import java.util.ArrayList;

public class LevelEditorScreen implements Screen {
    //NEEDED FOR EVERY (BOX2D) GAME
    private final GameClass game;

    private int pixels_per_meter;
    private float pixels_to_meters;

    private Engine engine;
    private World world;

    private OrthographicCamera camera;


    /**
     * LEVEL EDITOR SPECIFICS
     */
    private Level level;

    //Objects to be added/removed
    public ArrayList<Entity> toRemove;
    public ArrayList<SerializableObject> toAdd;

    //For editing UI
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;

    public boolean dialogOpen;



    public LevelEditorScreen(final GameClass game, Level level) {
        this.game = game;
        this.level = level;



        //Create basic components
        this.pixels_per_meter = (int) (Gdx.graphics.getWidth()/level.width);
        this.pixels_to_meters = 1.0f/(float) pixels_per_meter;

        this.engine = new Engine();
        world = new World(Constants.gravity, true);

        camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        atlas = new TextureAtlas("ui/skins/default/uiskin.atlas");
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("ui/skins/default/skin.json"), atlas);

        //Add systems
        engine.addSystem(new EditorEntityUpdaterSystem(this));
        engine.addSystem(new RenderingSystem(game.batch, pixels_per_meter));
        engine.addSystem(new EditorEntitySelectSystem(stage,skin, this));

        //Load entities
        level.loadSerializedObjects(engine,world);

        toRemove = new ArrayList<Entity>();
        toAdd = new ArrayList<SerializableObject>();
        dialogOpen = false;

        this.level = level;

        InputHandler.pixels_to_meters = pixels_to_meters;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game screen ", 100, Gdx.graphics.getHeight());
        engine.update(delta);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            //saveTest(EntitySerializer.serializeWorld(engine));
            //Gdx.app.exit();

            level.updateLevelFromEngine(engine);
            LevelLoaderSaver.saveLevelAsFile("level/gen/editorOUT.lvl", level);

            this.game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        if(toRemove.size() > 0){
            for(Entity e : toRemove){
                engine.removeEntity(e);
            }
        }

        toRemove = new ArrayList<Entity>();

        stage.act();
        stage.draw();

        if(toAdd.size() > 0){
            for(SerializableObject so : toAdd){
                //entityFactory.parseEntityFromString(s);
                so.addToEngine(engine,world);
            }
        }
        toAdd = new ArrayList<SerializableObject>();


        if(!dialogOpen&& Gdx.input.justTouched()){
            dialogOpen = true;
            Gdx.input.setInputProcessor(stage);
            EntityCreatorDialog edit = new EntityCreatorDialog("Create window", skin, getMousePosInGameWorld(), this) {
                @Override
                protected void result(Object object) {
                    if (object.equals("OK")) {
                        if(whatToCreate.equals("player")){
                            toAdd.add(new PlayerSO(clickPos.x, clickPos.y));
                        } else if (whatToCreate.equals("platform")) {
                            toAdd.add(new PlatformSO(clickPos.x, clickPos.y, 2, 2));
                        }
                        else if (whatToCreate.equals("finish")){
                            toAdd.add(new FinishSO(clickPos.x, clickPos.y,2,2 ));
                        }
                    }
                    remove();
                    dialogOpen = false;
                }
            };
            edit.show(stage);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        skin.dispose();
        atlas.dispose();
    }

    Vector2 getMousePosInGameWorld() {
        return new Vector2(Gdx.input.getX()*pixels_to_meters, (Gdx.graphics.getHeight()-Gdx.input.getY())*pixels_to_meters) ;
    }

    private void saveTest(ArrayList<String> savedCommands){
            FileHandle file = Gdx.files.local("level/gen/editorOUT.lvl");
            file.writeString("",false);
            for(String command : savedCommands){
                file.writeString(command, true);
                file.writeString("\n", true);
            }
    }


}
