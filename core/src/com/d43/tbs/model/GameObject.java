package com.d43.tbs.model;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 907915995355817502L;
	
	protected transient Polygon bounds;
	protected transient Sprite object;

	
	public GameObject(TextureRegion textureRegion, float x, float y, float width, float height) {
		this.object = new Sprite(textureRegion);
		this.object.setSize(width, height);
		this.object.setOrigin(width / 2f, height / 2f);
		this.object.setPosition(x, y);

		this.bounds = new Polygon(new float[] { 0f, 0f, width, 0f, width, height, 0f, height });
		this.bounds.setPosition(x, y);
		this.bounds.setOrigin(width / 2f, height / 2f);
	}
	
	public void reInit(TextureRegion textureRegion, float x, float y, float width, float height) {
		this.object = new Sprite(textureRegion);
		this.object.setSize(width, height);
		this.object.setOrigin(width / 2f, height / 2f);
		this.object.setPosition(x, y);

		this.bounds = new Polygon(new float[] { 0f, 0f, width, 0f, width, height, 0f, height });
		this.bounds.setPosition(x, y);
		this.bounds.setOrigin(width / 2f, height / 2f);
	}

	public void draw(SpriteBatch batch) {
		this.object.setPosition(this.bounds.getX(), this.bounds.getY());
		this.object.setRotation(this.bounds.getRotation());
		this.object.draw(batch);
	}

	public Polygon getBounds() {
		return bounds;
	}
	
	public Sprite getObject() {
		return this.object;
	}
	
	public void changeTextureRegion(TextureRegion textureRegion) {
		this.object.setRegion(textureRegion);
	}
	
//	public Texture getTextureRegion() {
//		return object.getTexture();
//	}
}
