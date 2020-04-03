package com.d43.tbs.model.unit;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d43.tbs.utils.Animation;

public class Archer extends RangeUnit {

	private static final long serialVersionUID = 7977309942227513876L;

	public Archer(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(2);
		this.setRangeAttack(4);
		this.setHp(33);
		this.setDamage(11);
		
		this.attackAnimDelay = 0.5f;
	}
	
	@Override
	public void initAnimations(TextureAtlas atlas) {
		ArrayList<TextureRegion> regionsIdle = new ArrayList<TextureRegion>();
//		for(int i = 1; i <= 3; i++)
//			regions.add(atlas.findRegion("archer_idle_" + Integer.toString(i)));
		regionsIdle.add(atlas.findRegion("archer_idle", 1));
		regionsIdle.add(atlas.findRegion("archer_idle", 2));
		regionsIdle.add(atlas.findRegion("archer_idle", 3));
//		regions.add(atlas.findRegion("knight"));
//		regions.add(atlas.findRegion("zombie"));

		this.idle = new Animation(regionsIdle, this, 2f, true);
		this.idle.setSize(32, 64);
		this.current = idle;
		
		ArrayList<TextureRegion> regionsAttack = new ArrayList<TextureRegion>();
		regionsAttack.add(atlas.findRegion("archer_attack", 1));
		regionsAttack.add(atlas.findRegion("archer_attack", 2));
		regionsAttack.add(atlas.findRegion("archer_attack", 3));
		regionsAttack.add(atlas.findRegion("archer_attack", 4));
		regionsAttack.add(atlas.findRegion("archer_attack", 5));
		regionsAttack.add(atlas.findRegion("archer_attack", 6));
		regionsAttack.add(atlas.findRegion("archer_attack", 7));
		regionsAttack.add(atlas.findRegion("archer_attack", 8));
		regionsAttack.add(atlas.findRegion("archer_attack", 5));
		this.attack = new Animation(regionsAttack, this, 0.6f, false);
		this.attack.setSize(48, 72);
	}

	@Override
	public Unit clone() {
		return new Archer(this.getTextureRegion(), this.getBounds().getX(), this.getBounds().getY(), this.getBounds().getBoundingRectangle().width, this.getBounds().getBoundingRectangle().height);
	}
}
