package com.d43.tbs.view.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.d43.tbs.TurnBasedStrategy;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.FileManager;
import com.d43.tbs.view.ui.MenuUI;

public class MenuScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TextureAtlas textureAtlas;
	public static float delta;
	private MenuUI ui;
	
	private TurnBasedStrategy game;
	
	private FileManager fileManager;

	@Override
	public void show() {
		batch = new SpriteBatch();
		this.fileManager = new FileManager();
		
		this.ui = new MenuUI(this.textureAtlas, this);
	}
	
	public void play(ArrayList<Unit> units) {
		this.game.play(true, units);
	}
	
	public void startNewGame() {
		this.game.startNewGame();
	}
	
	public ArrayList<Unit> getUnits(String name) {
		return fileManager.read(name);
	}
	
	public ArrayList<String> getFileNames() {
		return fileManager.getSaves();
	}
	
	public void deleteFile(String name) {
		this.fileManager.deleteFile(name);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch.begin();
			batch.draw(this.textureAtlas.findRegion("dirt"), 0,0);
		batch.end();
		
		ui.draw();
		
		batch.begin();
			batch.draw(this.textureAtlas.findRegion("menuBack"), 0, 0);
		batch.end();
	}
	

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(1366f, 768f);

		camera.update();
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
		batch.dispose();
//		ui.dispose();
	}

	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}
	
	public void setGame(TurnBasedStrategy game) {
		this.game = game;
	}
}
