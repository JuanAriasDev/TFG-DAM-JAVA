package com.jarias.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jarias.Juanman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Juanman";
		configuration.width = 1024;
		configuration.height = 512;
		new LwjglApplication(new Juanman(), configuration);
	}
}
