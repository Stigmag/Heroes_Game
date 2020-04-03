package com.d43.tbs.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BadLogic extends GameObject{

//	private BadLogicController badLogicController;
	
	public BadLogic(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
//		badLogicController = new BadLogicController(bounds);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
//		badLogicController.handle();
	}
//
//	public static Animation animation;
//	public static TextureRegion[] frames;
//	public static TextureRegion currentFrame;
//	public void createAnimation() {
//		Texture sheet = new Texture(Gdx.files.internal("atlas.png"));
//		TextureRegion[][] temp = TextureRegion.split(sheet, 512, 256);
//		frames = new TextureRegion[8];
//		for(int i = 0, ind = 0; i < 2; i++) 
//			for(int j = 0; j < 4; j++)
//				frames[ind++] = temp[i][j];
//		
//		animation = new Animation(0.2f, frames);
//	}
//	
//	public float getSpeed() {
//		return this.badLogicController.getSpeed();
//	}
}
