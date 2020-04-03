package com.d43.tbs.model.unit;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d43.tbs.utils.Animation;

public class Orc extends RangeUnit {

	private static final long serialVersionUID = 4863104976572816411L;

	public Orc(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.setRangeMovement(2);
		this.setRangeAttack(4);
		this.setHp(42);
		this.setDamage(19);
		this.setIsEnemy(true);
		
		this.attackAnimDelay = 0.75f;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	public Unit clone() {
		return new Orc(this.getTextureRegion(), this.getBounds().getX(), this.getBounds().getY(), this.getBounds().getBoundingRectangle().width, this.getBounds().getBoundingRectangle().height);
	}

	@Override
	public void initAnimations(TextureAtlas atlas) {
		ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();
		regions.add(atlas.findRegion("orc_idle", 1));
		regions.add(atlas.findRegion("orc_idle", 2));
		regions.add(atlas.findRegion("orc_idle", 3));
		regions.add(atlas.findRegion("orc_idle", 4));

		this.idle = new Animation(regions, this, 1.3f, true);
		this.idle.setSize(38, 67);
		this.current = idle;
		
		ArrayList<TextureRegion> regionsAttack = new ArrayList<TextureRegion>();
		regionsAttack.add(atlas.findRegion("orc_attack", 7));
		regionsAttack.add(atlas.findRegion("orc_attack", 6));
		regionsAttack.add(atlas.findRegion("orc_attack", 5));
		regionsAttack.add(atlas.findRegion("orc_attack", 4));
		regionsAttack.add(atlas.findRegion("orc_attack", 3));
		regionsAttack.add(atlas.findRegion("orc_attack", 2));
		regionsAttack.add(atlas.findRegion("orc_attack", 1));
		this.attack = new Animation(regionsAttack, this, 0.8f, false);
		this.attack.setSize(40, 67);
		this.attack.setDeltaPosition(-5, 0);
	}
}
