package com.d43.tbs;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.Assets;
import com.d43.tbs.view.screen.ChooseScreen;
import com.d43.tbs.view.screen.GameScreen;
import com.d43.tbs.view.screen.MenuScreen;

//	cool

public class TurnBasedStrategy extends Game {

	private Screen menuScreen, chooseScreen, gameScreen;
	private Assets assets;
	
	@Override
	public void create() {
		assets = new Assets();
		
		menuScreen = new MenuScreen();
		((MenuScreen)menuScreen).setTextureAtlas(assets.getManager().get("core/assets/atlas.atlas", TextureAtlas.class));
		((MenuScreen)menuScreen).setGame(this);
		
		chooseScreen = new ChooseScreen();
		((ChooseScreen)chooseScreen).setTextureAtlas(assets.getManager().get("core/assets/atlas.atlas", TextureAtlas.class));
		((ChooseScreen)chooseScreen).setGame(this);
		
		this.setScreen(menuScreen);
	}
	
//	public void endGame(String[] result) {
//		((ResultScreen)resultScreen).setResult(result);
//		this.setScreen(resultScreen);
//	}
	
	public void backToMenu() {
		menuScreen = new MenuScreen();
		((MenuScreen)menuScreen).setTextureAtlas(assets.getManager().get("core/assets/atlas.atlas", TextureAtlas.class));
		((MenuScreen)menuScreen).setGame(this);
		
		this.setScreen(menuScreen);
	}
	
	public void startNewGame() {
		this.setScreen(chooseScreen);
	}
	
	public void play(boolean fromFile, ArrayList<Unit> units) {
		gameScreen = new GameScreen();
		((GameScreen)gameScreen).setTextureAtlas(assets.getManager().get("core/assets/atlas.atlas", TextureAtlas.class));
		this.setScreen(gameScreen);
		((GameScreen)this.gameScreen).setFromFile(fromFile);
		((GameScreen)gameScreen).setUnits(units);
		((GameScreen)gameScreen).setGame(this);
	}
	
	public void dispose() {
		super.dispose();
		this.getScreen().dispose();
		assets.dispose();
	}
	
//	SpriteBatch batch;
//	Texture img;
//	
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}
//
//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
//	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
