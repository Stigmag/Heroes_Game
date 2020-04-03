package com.d43.tbs.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	
	private AssetManager manager;
	
	public Assets() {
		manager = new AssetManager();
		manager.load("core/assets/atlas.atlas", TextureAtlas.class);
		manager.finishLoading();
	}
	
	public AssetManager getManager() {
		return manager;
	}
	
	public void dispose() {
		manager.dispose();
	}
}
