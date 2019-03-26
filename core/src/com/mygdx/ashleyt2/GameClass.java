package com.mygdx.ashleyt2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.ashleyt2.ui.screens.MainMenuScreen;

public class GameClass extends Game {
	public SpriteBatch batch;

	public BitmapFont font;

	private FPSLogger fpsLogger;

	@Override
	public void create () {
		fpsLogger = new FPSLogger();
		batch = new SpriteBatch();

		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		//super.render();
		screen.render(Gdx.graphics.getDeltaTime());
		fpsLogger.log();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
