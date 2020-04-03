package com.d43.tbs.control;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.Unit;

public abstract class MapHandler {

	protected CellMap map;
	protected ArrayList<Unit> allies;
	protected ArrayList<Unit> enemies;

	protected Unit pickedUnit;
	
	protected TextureAtlas textureAtlas;
	
	protected Array<Cell> availableCells;
	
	protected Unit movingUnit;
	
	public abstract boolean isPlaying();
	
	public abstract void pickUnit(Unit unit);
	public abstract void unitAttack(Polygon bounds);
	public abstract void unitMove(Polygon bounds);
}
