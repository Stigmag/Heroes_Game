package com.d43.tbs.control;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.ChoosingZone;
import com.d43.tbs.model.unit.Unit;

public class MapChoosing extends MapHandler {

	private ChoosingZone choosingZone;

	public MapChoosing(CellMap cells, ChoosingZone choosingZone, ArrayList<Unit> allies, ArrayList<Unit> enemies) {
		this.map = cells;
		this.allies = allies;
		this.enemies = enemies;

		this.choosingZone = choosingZone;

		this.pickedUnit = null;
		this.movingUnit = null;
	}
	
	public boolean isPlaying() {
		return false;
	}

	// ********************** Map Control *********************************

	public void calculateRange() {
		if (this.pickedUnit != null) {
			this.availableCells = new Array<Cell>();
			for (int i = 0; i < map.getRows(); i++)
				for(int j = 0; j < map.getCols(); j++)
					if(!map.getCell(i, j).containsUnit())
						this.availableCells.add(map.getCell(i, j));
		}
	}

	private void paintAvailableCell() {
		for (int i = 0; i < this.availableCells.size; i++)
			this.availableCells.get(i)
					.setRegion(this.availableCells.get(i).containsUnit() ? this.textureAtlas.findRegion("cellPickable")
							: this.textureAtlas.findRegion("cellPickable"));
	}

	private boolean cellIsAvailable(Cell cell) {
		return this.availableCells.contains(cell, true) ? true : false;
	}

	private void paintToDefault() {
		for (int i = 0; i < this.map.getRows(); i++)
			for (int j = 0; j < this.map.getCols(); j++)
				if (this.map.getCell(i, j).getUnit() == null || this.map.getCell(i, j).getUnit()
						.isEnemy()/* && this.cellIsAvailable(this.map.getCell(i, j)) */)
					this.map.getCell(i, j).setRegion(this.textureAtlas.findRegion("cell"));
//				else 
//					this.map.getCell(i, j).setRegion(this.textureAtlas.findRegion("cell"));

	}

	public void setAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	// ********************* Enemy Control ********************************

	// ********************** Unit Control ********************************

	public void pickUnit(Unit unit) {
		
		this.paintToDefault();		
		
		if (unit == null || unit.isMoving())
			return;
		
		if(unit.isForChoose()) {
			if(this.pickedUnit != null) {
				if(this.pickedUnit.equals(unit)) {
					if(this.availableCells != null)
						paintAvailableCell();
					return;
				}
				else {
					if(this.pickedUnit.getCell() == null)
						this.pickedUnit.getBounds().setPosition(1366, 768);
				}
			}
			Unit newUnit = unit.clone();
			newUnit.initAnimations(this.textureAtlas);
			this.pickedUnit = newUnit;
		}
		else this.pickedUnit = unit;
		
		if (!this.pickedUnit.isReplaceable()) {
			this.pickedUnit = null;
			return;
		}

		this.calculateRange();
		this.paintAvailableCell();
	}
	
	public Unit getPickedUnit() {
		return this.pickedUnit;
	}

	public void unitMove(Polygon bounds) {
		if(this.pickedUnit != null && this.pickedUnit.isMoving())
			return;
		if (this.pickedUnit != null) {
			Cell cell = map.findCell(bounds);
			this.movingUnit = this.pickedUnit;
			this.pickedUnit.setCell(cell, map.findCellCoord(cell));
			this.pickedUnit.setLocation(map.findCellCoord(cell.getBounds()).x, map.findCellCoord(cell.getBounds()).y);

			this.pickedUnit.getCell().setRegion(this.textureAtlas.findRegion("cell"));
			this.pickedUnit.getCell().changeTextureRegion(this.textureAtlas.findRegion("cell"));

			this.pickedUnit = null;
			this.availableCells = null;
			this.paintToDefault();
		}
	}

	@Override
	public void unitAttack(Polygon bounds) {
		
	}
}
