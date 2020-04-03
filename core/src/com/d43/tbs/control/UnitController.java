package com.d43.tbs.control;

import java.io.Serializable;

import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.unit.Unit;

public class UnitController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private transient Cell cell;										// Не серіалізується

	private Unit unit;

	private MapHandler mapHandler;

	public UnitController(int id) {
		this.id = id;
	}
	
	public MapHandler getMapHandler() {
		return this.mapHandler;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public void setCell(Cell cell) {
		if (this.unit == null)
			return;
		this.cell = cell;
//		this.bounds.setPosition(
//				this.cell.getBounds().getX() + this.cell.getSize().width / 2 - this.unit.getSize().width / 2,
//				this.cell.getBounds().getY() + this.cell.getSize().height / 2 - this.cell.getSize().height / 4);
	}

	public void setMapHandler(MapHandler mapHandler) {
		this.mapHandler = mapHandler;
	}

	public void handle() {
		
	}

	public void pickedUp() {

	}
}
