package com.d43.tbs.model.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.control.MapHandler;
import com.d43.tbs.model.GameObject;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.view.screen.ChooseScreen;

public class ChoosingZone extends GameObject {
	private Cell[] allies, enemies;
	
	private TextureAtlas textureAtlas;
	
	public ChoosingZone(TextureAtlas textureAtlas, TextureRegion textureRegion, float x, float y, float width, float height) {
		super(textureRegion, x, y, width, height);
		
		this.textureAtlas = textureAtlas;
	}
	
	public void initCells(ChooseScreen chooseScreen, int alliesCount, int enemiesCount) {
		this.allies = new Cell[alliesCount];
		this.enemies = new Cell[enemiesCount];
		
		Array<TextureRegion> regions = new Array<TextureRegion>(); 
		regions.add(this.textureAtlas.findRegion("cell"));
		regions.add(this.textureAtlas.findRegion("cellMouseOn"));
		regions.add(this.textureAtlas.findRegion("cellMouseOnBlocked"));
		
		int multiplier = 80;
		
		for(int i = 0; i < alliesCount; i++) {
//			Cell cell = new Cell(regions, (-8 + i*0.99f)*multiplier, (-3.5f + 11f*0.6f)*multiplier, 1f * multiplier, 0.625f * multiplier);
//			Cell cell = new Cell(regions, (-8 + i*1f)*multiplier, (-3.5f + 11*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
			Cell cell = new Cell(regions, (-7 + i*1f)*multiplier, (-3.5f + 11.1f*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
			cell.setForDefeated(true);
			cell.setChooseScreen(chooseScreen);
			this.allies[i] = cell;
		}
		
		for(int i = enemiesCount-1; i >= 0; i--) {
//			Cell cell = new Cell(regions, (2f + i*0.99f)*multiplier, (-3.5f + 11f*0.6f)*multiplier, 1f * multiplier, 0.625f * multiplier);
//			Cell cell = new Cell(regions, (-8 + i*1f)*multiplier, (-3.5f + 10f*0.6f)*multiplier, 1f * multiplier, 0.625f * multiplier);
//			Cell cell = new Cell(regions, (-8 + i*1f)*multiplier, (-3.5f + 10*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
			Cell cell = new Cell(regions, (5 + i*1f)*multiplier, (-3.5f + 10.1f*0.625f)*multiplier, 1f * multiplier, 0.625f * multiplier);
			cell.setForDefeated(true);
			cell.setChooseScreen(chooseScreen);
			this.enemies[i] = cell;
		}
	}
	
	public void setMapHandler(MapHandler mapHandler) {
		for(int i = 0; i < allies.length; i++)
			allies[i].setMapHandler(mapHandler);
		
		for(int i = 0; i < enemies.length; i++)
			enemies[i].setMapHandler(mapHandler);
	}
	
	public void addAllies(Unit unit) {
		unit.setAlive(false);
		for(int i = 0; i < allies.length; i++)
			if(!allies[i].containsUnit()) {
				unit.setCell(allies[i], new Vector2(0, 0));
//				return;
			}
	}
	
	public void addEnemies(Unit unit) {
		unit.setAlive(false);
		for(int i = 0; i < enemies.length; i++)
			if(!enemies[i].containsUnit()) {
				unit.setCell(enemies[i], new Vector2(0, 0));
//				return;
			}
	}
	
	public int getAlliesCount() {
		return this.allies.length;
	}
	
	public int getEnemiesCount() {
		return this.enemies.length;
	}		
	
	@Override
	public void draw(SpriteBatch batch) {
		for(int i = 0; i < enemies.length; i++) 
			enemies[i].draw(batch);
		
		for(int i = 0; i < allies.length; i++) 
			allies[i].draw(batch);
	}
	
	public Cell[] getAlliesCells() {
		return this.allies;
	}
	public Cell[] getEnemiesCells() {
		return this.enemies;
	}
	
	public Cell getAlliesCell(int x) {
		return this.allies[x];
	}
	
	public Cell getEnemiesCell(int x) {
		return this.enemies[x];
	}
	
//	public Cell findCell(Vector2 location) {
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				if(cells[i][j].getController().isBelong(location.x, location.y))
//					return cells[i][j];
//		return null;
//	}
//	
//	public String findCellString(Vector2 location) {
//		for(int i = 0; i < this.rows; i++)
//			for(int j = 0; j < this.cols; j++)
//				if(cells[i][j].getController().isBelong(location.x, location.y))
//					return new String(Integer.toString(i)+" : "+Integer.toString(j));
//		return "";
//	}
}
