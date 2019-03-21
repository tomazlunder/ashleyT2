package com.mygdx.ashleyt2.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.ashleyt2.level.Level;
import com.mygdx.ashleyt2.level.util.LevelLoaderSaver;
import com.mygdx.ashleyt2.ui.widgets.FileChooserDialog;
import com.mygdx.ashleyt2.GameClass;

public class AdminMenuScreen implements Screen {
    final GameClass game;

    OrthographicCamera camera;

    //Scene2d
    private Viewport viewport;

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;

    public AdminMenuScreen(final GameClass game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();

        stage = new Stage(viewport, game.batch);
        atlas = new TextureAtlas("ui/skins/default/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/skins/default/skin.json"), atlas);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        //mainTable.left().top();

        //Create buttons
        TextButton editorButton = new TextButton("Level editor", skin);
        TextButton backButon = new TextButton("Back", skin);

        editorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(new InputMultiplexer());
                game.setScreen(new LevelEditorScreen(game, LevelLoaderSaver.loadFileAsLevel("level/gen/default.lvl")));
                dispose();
            }
        });

        backButon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });


        Label titleLabel = new Label("Game title", skin);
        Label emptyLabel = new Label("",skin);

        mainTable.columnDefaults(6);
        //mainTable.debug();
/*
        mainTable.add().colspan(1).expandX();
        mainTable.add(titleLabel).colspan(4).center().bottom().expandX().expandY();
        mainTable.add().colspan(1).expandX();

        mainTable.row().fillX();
       */
        mainTable.add(emptyLabel).colspan(6).expandX().expandY();

        mainTable.row().fillX();
        mainTable.add(emptyLabel).colspan(6).expandX().expandY();

        mainTable.row().fillX().fillY();
        mainTable.add().colspan(2).expandX();
        mainTable.add(editorButton).colspan(2).center().expandX().expandY();
        mainTable.add().colspan(2).expandX();

        mainTable.row().fillX();
        mainTable.add(emptyLabel).colspan(6).expandX().expandY();

        mainTable.row().fillX().fillY();
        mainTable.add().colspan(2).expandX();
        mainTable.add(backButon).colspan(2).center().expandX().expandY();
        mainTable.add().colspan(2).expandX();

        mainTable.row().fillX();
        mainTable.add(emptyLabel).colspan(6).expandX().expandY();


        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Admin menu screen", 100, Gdx.graphics.getHeight());

        game.batch.end();

        stage.act();
        stage.draw();
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
        atlas.dispose();
        skin.dispose();
    }
}
