package com.d43.tbs.model.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.d43.tbs.model.Arrow;
import com.d43.tbs.view.screen.GameScreen;

public abstract class RangeUnit extends Unit{
	
	private static final long serialVersionUID = 1411811067693051717L;
	private transient Arrow arrow;
	
	public RangeUnit(TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
	}
	
	@Override
	public void attack(Unit unit) {
		Vector2 from = new Vector2(this.getBounds().getX() + this.getObject().getWidth()/2, this.getBounds().getY() + this.getObject().getHeight()/2);
		Vector2 to = new Vector2(unit.getBounds().getX() + unit.getObject().getWidth()/2, unit.getBounds().getY() + unit.getObject().getHeight()/2);
		
		float width = 5f;
		float height = 35f/5f * width;
		
		arrow = new Arrow(GameScreen.textureAtlas.findRegion("arrow"), from, to, width, height, this.finalDelay + this.attackAnimDelay);
		arrow.setUnit(unit);
		if (this.attack.getDeltaPosition() != null)
			this.getBounds().setPosition(
					this.cell.getBounds().getX() + this.attack.getDeltaPosition().x + this.cell.getSize().width / 2
							- this.getSize().width / 2,
					this.cell.getBounds().getY() + this.attack.getDeltaPosition().y + this.cell.getSize().height / 2
							- this.cell.getSize().height / 4);
		this.current = this.attack;
		unit.damage(this.damage);
	}

	@Override
	public void draw(SpriteBatch batch, float delta) {
		super.draw(batch, delta);
		
		if(arrow != null) {
			this.arrow.update();
			arrow.draw(batch, delta);
		
		}
	}

}
