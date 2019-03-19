package com.mygdx.ashleyt2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.ashleyt2.GameClass;
import com.mygdx.ashleyt2.PagedScrollPane;

public class LevelSelectScreen implements Screen {
    final GameClass game;
    OrthographicCamera camera;


    private Viewport viewport;

    private Skin skin;
    private Stage stage;
    private Table container;
    private TextureAtlas atlas;


    public LevelSelectScreen(final GameClass game){
        this.game = game;

        camera = new OrthographicCamera();
        //camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();


        atlas = new TextureAtlas("ui/skins/default/uiskin.atlas");
        stage = new Stage(viewport, game.batch);
        skin = new Skin(Gdx.files.internal("ui/skins/default/skin.json"), atlas);
        skin.add("top", skin.newDrawable("default-round", Color.RED), Drawable.class);
        skin.add("star-filled", skin.newDrawable("white", Color.YELLOW), Drawable.class);
        skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY), Drawable.class);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        container.debug();

        PagedScrollPane scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(25);
        int c = 1;
        for (int l = 0; l < 10; l++) {
            Table levels = new Table().pad(50);
            levels.defaults().pad(20, 40, 20, 40);
            for (int y = 0; y < 3; y++) {
                levels.row();
                for (int x = 0; x < 4; x++) {
                    levels.add(getLevelButton(c++)).expand().fill();
                }
            }
            scroll.addPage(levels);
        }
        container.add(scroll).expand().fill();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
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

    }

    /**
     * Creates a button to represent the level
     *
     * @param level
     * @return The button to use for the level
     */
    public Button getLevelButton(int level) {
        Button button = new Button(skin);
        Button.ButtonStyle style = button.getStyle();
        style.up = 	style.down = null;

        // Create the label to show the level number
        Label label = new Label(Integer.toString(level), skin);
        label.setFontScale(2f);
        label.setAlignment(Align.center);

        // Stack the image and the label at the top of our button
        button.stack(new Image(skin.getDrawable("top")), label).expand().fill();

        // Randomize the number of stars earned for demonstration purposes
        int stars = MathUtils.random(-1, +3);
        Table starTable = new Table();
        starTable.defaults().pad(5);
        if (stars >= 0) {
            for (int star = 0; star < 3; star++) {
                if (stars > star) {
                    starTable.add(new Image(skin.getDrawable("star-filled"))).width(20).height(20);
                } else {
                    starTable.add(new Image(skin.getDrawable("star-unfilled"))).width(20).height(20);
                }
            }
        }

        button.row();
        button.add(starTable).height(30);

        button.setName("Level" + Integer.toString(level));
        button.addListener(levelClickListener);
        return button;
    }

    /**
     * Handle the click - in real life, we'd go to the level
     */
    public ClickListener levelClickListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
            System.out.println("Click: " + event.getListenerActor().getName());
        }
    };
}
