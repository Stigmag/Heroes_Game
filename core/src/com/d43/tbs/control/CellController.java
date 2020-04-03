package com.d43.tbs.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.view.screen.ChooseScreen;

public class CellController {

	private Polygon bounds;
	private Unit unit;
	private Cell cell;

	private MapHandler mapHandler;
	
	private ChooseScreen chooseScreen;

	
	public CellController(Polygon bounds) {
		this.bounds = bounds;

		this.unit = null;
	}

	public void setMapHandler(MapHandler mapHandler) {
		this.mapHandler = mapHandler;
	}

	public void setChooseScreen(ChooseScreen chooseScreen) {
		this.chooseScreen = chooseScreen;
	}

	public void handle() {
		float x = Gdx.input.getX() - Gdx.graphics.getWidth() / 2, y = -Gdx.input.getY() + Gdx.graphics.getHeight() / 2;
		if (this.bounds.contains(x, y)) {
//			Gdx.app.log("tag", this.unit.toString());
			this.cell.changeTextureRegion(this.cell.getRegions().get(1));
			if (this.unit != null && !this.unit.isReplaceable() && !this.unit.isEnemy())
				this.cell.changeTextureRegion(this.cell.getRegions().get(2));

//			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				if (!this.cell.isForDefeated()) {
					if (this.unit != null)
						if (!this.unit.isEnemy())
							mapHandler.pickUnit(this.unit);
						else
							mapHandler.unitAttack(this.bounds);
					else {
						mapHandler.unitMove(this.bounds);
					}

				}
				if (this.mapHandler != null && !mapHandler.isPlaying()) {
					if (this.unit != null) {
						if(this.unit.isForChoose()) {
							mapHandler.pickUnit(this.unit);
							chooseScreen.addUnit(((MapChoosing)mapHandler).getPickedUnit());
//							chooseScreen.addUnit(this.unit);
						}
						else if(!this.unit.isMoving()) 
							mapHandler.pickUnit(this.unit);
					}
					else {
						mapHandler.unitMove(this.bounds);
						mapHandler.pickUnit(null);
					}
				}
			}
			
			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
				if (this.mapHandler != null && !mapHandler.isPlaying()) {
					if (this.unit != null && !this.unit.isForChoose()) {
						this.mapHandler.pickUnit(null);
						this.unit.setCell(this.mapHandler.map.getBlankCell(), new Vector2(0, 0));
						chooseScreen.removeUnit(this.unit);
//						chooseScreen.addUnit(this.unit);
					}
				}
			}
		} else
			this.cell.changeTextureRegion(this.cell.getRegion());
	}
	
	public void unPickUnitChoose() {
		this.mapHandler.pickUnit(null);
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell() {
		return this.cell;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public boolean isBelong(float x, float y) {
		return this.bounds.contains(x, y) ? true : false;
	}

	public boolean isMouseCollide(float x, float y) {
		return this.bounds.contains(x - Gdx.graphics.getWidth() / 2, y - Gdx.graphics.getHeight() / 2) ? true : false;
	}
}
