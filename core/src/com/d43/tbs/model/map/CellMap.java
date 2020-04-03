package com.d43.tbs.model.map;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.MapHandler;
import com.d43.tbs.model.GameObject;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.view.screen.ChooseScreen;

public class CellMap extends GameObject{
	
	private Cell[][] map;
	private int rows, cols;
	
	private ArrayList<Unit> choosingUnits;
	
	private TextureAtlas textureAtlas;
	
	private ChooseScreen chooseScreen;
	
	private Cell blankCell;
	
	public CellMap(TextureAtlas textureAtlas, TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.textureAtlas = textureAtlas;
		
		this.choosingUnits = new ArrayList<Unit>();
	}
	
	public void initCells(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		map = new Cell[this.rows][this.cols];
		
		Array<TextureRegion> regions = new Array<TextureRegion>(); 
		regions.add(this.textureAtlas.findRegion("cell"));
		regions.add(this.textureAtlas.findRegion("cellMouseOn"));
		regions.add(this.textureAtlas.findRegion("cellMouseOnBlocked"));
		
		int multiplier = 80;
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				Cell cell;
				cell = new Cell(regions, (-8 + i*1f)*multiplier, (-4.4f + j*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
				map[i][j] = cell;
			}
		
		this.blankCell = new Cell(regions, 1366, 768, 1f, 2f);
	}
	
	public Cell getBlankCell() {
		return this.blankCell;
	}
	
	public void initChooseScreen(ChooseScreen chooseScreen) {
		this.chooseScreen = chooseScreen;
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++) {
				map[i][j].setChooseScreen(chooseScreen);
			}
	}
	
	public void addChoosingUnit(Unit unit) {
		this.choosingUnits.add(unit);
	}
	
	public void reDrawCells() {
		
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public void setMapHandler(MapHandler mapHandler) {
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.cols; j++)
				map[i][j].setMapHandler(mapHandler);
	}
	
	public void placeUnits(ArrayList<Unit> units) {
		for(int i = 0; i < units.size(); i++) {
			Cell cell = this.getCell((int)units.get(i).getLocation().x, (int)units.get(i).getLocation().y);
			Vector2 coord = this.findCellCoord(cell);
			units.get(i).setCell(cell, coord);
		}
//		for(int i = 0; i < this.cols; i++)
//			for(int j = 0; j < this.rows; j++)
//				cells[i][j].getController().setUnitOn(-1);
		
//		for(int i = 0; i < units.size; i++)
//			units.get(i).getCell().getController().setUnitOn(units.get(i).getId());
				
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				map[i][j].draw(batch);
		
		for(int i = 0; i < this.rows; i++)
			for(int j = this.cols-1; j >= 0; j--)
				map[i][j].draw(batch);
	}
	
	public Cell[][] getCells() {
		return map;
	}
	
	public Cell getCell(int x, int y) {
		return map[x][y];
	}
	
	public Vector2 findCellCoord(Cell cell) {
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.cols; j++)
				if(map[i][j] == cell)
					return new Vector2(i, j);
		return null;
	}
	
//	public Cell findCell(Vector2 location) {
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				if(map[i][j].getController().isBelong(location.x, location.y))
//					return map[i][j];
//		return null;
//	}
	
//	public String findCellString(Vector2 location) {
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				if(cells[i][j].getController().isBelong(location.x, location.y))
//					return new String(Integer.toString(i)+" : "+Integer.toString(j));
//		return "";
//	}
	
	public boolean cellExist(Polygon bounds) {
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.cols; j++)
				if(map[i][j].getBounds() == bounds)
					return true;
		return false;
	}
	
	public Cell findCell(Polygon bounds) {
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.cols; j++)
				if(map[i][j].getBounds() == bounds)
					return map[i][j];
		return null;
	}
	
	public Vector2 findCellCoord(Polygon bounds) {
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.cols; j++)
				if(map[i][j].getBounds() == bounds)
					return new Vector2(i, j);
		return null;
	}
	
	public String findCellString(Polygon bounds) {
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.cols; j++)
				if(map[i][j].getBounds() == bounds)
					return new String(Integer.toString(i) + " : " + Integer.toString(j));
		return ":";
	}
	
	public void returnMap() {
		String str = "";
		for(int i = this.cols-1; i >= 0; i--) {
			for(int j = 0; j < this.rows; j++) {
				str += map[j][i].getUnit() != null ? Integer.toString(map[j][i].getUnit().getId()) : "*";
			}
			Gdx.app.log("tag", str);
			str = "";
		}
	}
}
