package com.d43.tbs.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.d43.tbs.TurnBasedStrategy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1366;
		config.height = 768;
		
		config.fullscreen = true;
		
		config.foregroundFPS = 60;
		config.vSyncEnabled = false;
		
		new LwjglApplication(new TurnBasedStrategy(), config);
	}
}
