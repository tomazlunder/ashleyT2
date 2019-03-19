package com.mygdx.ashleyt2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.ashleyt2.GameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";
		config.height = 720;
		config.width = 1280;

		/*
		config.fullscreen = true;
		config.width = 1920;
		config.height = 1080;
		*/

		new LwjglApplication(new GameClass(), config);
	}
}
