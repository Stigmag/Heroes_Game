package com.d43.tbs.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.d43.tbs.model.unit.Unit;

public class Animation {
	private ArrayList<TextureRegion> frames;
	private float maxFrameTime;
	private float currentFrameTime;
	private int frameCount;
	private int frame;
	
	private boolean looping;
	
	private Unit unit;
	
	private Vector2 size;
	private Vector2 position;
	
	private boolean toRight;

	public Animation(ArrayList<TextureRegion> frames, Unit unit, float cycleTime, boolean looping) {
		this.frames = frames;
		this.frameCount = frames.size();
		maxFrameTime = cycleTime / frameCount;
		frame = 0;
		
		this.looping = looping;
		
		this.unit = unit;
		
		if(unit.isEnemy())
			this.toRight = false;
		else this.toRight = true;
	}
	
	
	public void flip(boolean toRight) {
		boolean rotate;
		if(this.toRight == toRight)
			rotate = false;
		else rotate = true;
		this.toRight = toRight;
		
		for(int i = 0; i < this.frames.size(); i++)
			this.frames.get(i).flip(rotate, false);
	}
	
	public void setDeltaPosition(float x, float y) {
		this.position = new Vector2(x, y);
	}
	
	public Vector2 getDeltaPosition() {
		return this.position;
	}
	
	public void setSize(int width, int height) {
		this.size = new Vector2(width, height);
	}
	
	public Vector2 getSize() {
		return this.size;
	}

	public void refresh() {
		this.currentFrameTime = 0;
	}
	
	public void update(float dt) {
		currentFrameTime += dt;
		if (currentFrameTime > maxFrameTime) {
			frame++;
			currentFrameTime = 0;
		}
		if (frame >= frameCount) {
			if(!looping)
				this.unit.setIdle();
			frame = 0;
		}
	}

	public TextureRegion getFrame() {
		return frames.get(frame) ;
	}
}