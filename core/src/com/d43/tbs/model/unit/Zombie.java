package com.d43.tbs.model.unit;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d43.tbs.utils.Animation;

public class Zombie extends MeleeUnit {

	public Zombie(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(3);
		this.setRangeAttack(1);
		this.setHp(16);
		this.setDamage(24);
		this.setIsEnemy(true);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	public Unit clone() {
		return new Zombie(this.getTextureRegion(), this.getBounds().getX(), this.getBounds().getY(), this.getBounds().getBoundingRectangle().width, this.getBounds().getBoundingRectangle().height);
	}

	@Override
	public void initAnimations(TextureAtlas atlas) {
		ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();
		regions.add(atlas.findRegion("zombie_idle", 1));
		regions.add(atlas.findRegion("zombie_idle", 2));
		regions.add(atlas.findRegion("zombie_idle", 3));
		regions.add(atlas.findRegion("zombie_idle", 4));
		regions.add(atlas.findRegion("zombie_idle", 5));
		regions.add(atlas.findRegion("zombie_idle", 6));

		this.idle = new Animation(regions, this, 1f, true);
		this.idle.setSize(41, 68);
		this.current = idle;
		
		ArrayList<TextureRegion> regionsAttack = new ArrayList<TextureRegion>();
		regionsAttack.add(atlas.findRegion("zombie_attack", 1));
		regionsAttack.add(atlas.findRegion("zombie_attack", 2));
		regionsAttack.add(atlas.findRegion("zombie_attack", 3));
		regionsAttack.add(atlas.findRegion("zombie_attack", 4));
		regionsAttack.add(atlas.findRegion("zombie_attack", 5));
		this.attack = new Animation(regionsAttack, this, 0.5f, false);
		this.attack.setSize(64, 72);
		this.attack.setDeltaPosition(-10, 0);
	}
}
