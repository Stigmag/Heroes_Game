package com.d43.tbs.control;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.TurnBasedStrategy;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.DefeatedZone;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.CellCalculator;
import com.d43.tbs.view.screen.CompaignGameScreen;
import com.d43.tbs.view.screen.GameScreen;

public class MapPlaying extends MapHandler {

	private DefeatedZone defeatedZone;
	private Bot bot;

	private TurnBasedStrategy game;
	private GameScreen gameScreen;
	private CompaignGameScreen compaignGameScreen;

	private boolean win;
	
	private int alliesCount, enemiesCount;

	public MapPlaying(CellMap cells, DefeatedZone defeatedZone, ArrayList<Unit> allies, ArrayList<Unit> enemies) {
		this.map = cells;
		this.allies = allies;
		this.enemies = enemies;

		this.defeatedZone = defeatedZone;

		this.pickedUnit = null;

		this.bot = new Bot(this.allies, this.enemies, this, this.map);

		this.movingUnit = null;
		
		this.alliesCount = allies.size();
		this.enemiesCount = enemies.size();
	}

	public boolean isPlaying() {
		return true;
	}

	// ********************** Map Control *********************************

	public void calculateRange() {
		Cell cell = pickedUnit.getCell();
		Vector2 coord = map.findCellCoord(cell.getBounds());

		if (this.pickedUnit != null) {
			CellCalculator calculator = new CellCalculator(true, this.map, coord, this.pickedUnit.getRangeMovement(),
					this.pickedUnit.getRangeAttack());
			String[] str = calculator.getStringRepresentation();

//			Gdx.app.log("tag", "");
//			for(int i = 0; i < str.length; i++)
//				Gdx.app.log("tag", str[i]);

			this.availableCells = calculator.getAvailableCellsForMove();
			Array<Cell> attackCells = calculator.getAvailableCellsForAttack();
			for (int i = 0; i < attackCells.size; i++)
				this.availableCells.add(attackCells.get(i));
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



	public void pickUnit(Unit unit) {

		if (unit.isMoving())
			return;

		this.paintToDefault();
		this.pickedUnit = unit;

		if (!this.pickedUnit.isReplaceable()) {
			this.pickedUnit = null;
			return;
		}

		this.calculateRange();
		this.paintAvailableCell();
	}

	public void unitMove(Polygon bounds) {
		if (this.pickedUnit != null) {
			Cell cell = map.findCell(bounds);
			if (this.cellIsAvailable(cell)) {
				this.movingUnit = this.pickedUnit;
				this.pickedUnit.setCell(cell, map.findCellCoord(cell));

				this.pickedUnit.getCell().setRegion(this.textureAtlas.findRegion("cellBlocked"));
				this.pickedUnit.getCell().changeTextureRegion(this.textureAtlas.findRegion("cellBlocked"));

				this.pickedUnit.setReplaceability(false);

				this.pickedUnit = null;
				this.availableCells = null;
				this.paintToDefault();

				if (this.unitsAreDone()) {
					bot.makeBotsMove();
				}
			} else {
				this.pickedUnit = null;
				this.paintToDefault();
			}
		}
	}

	public void unitAttack(Polygon bounds) {
		if (this.pickedUnit != null) {
			Cell cell = map.findCell(bounds);
			if (this.cellIsAvailable(cell)) {
				Unit enemy = cell.getUnit();
				this.pickedUnit.attack(enemy);

				this.pickedUnit.getCell().setRegion(this.textureAtlas.findRegion("cellBlocked"));
				this.pickedUnit.getCell().changeTextureRegion(this.textureAtlas.findRegion("cellBlocked"));

				this.pickedUnit.setReplaceability(false);

				this.pickedUnit = null;
				this.availableCells = null;
				this.paintToDefault();

				if (this.unitsAreDone()) {
					bot.makeBotsMove();
				}
			}
		}
		for (int i = 0; i < enemies.size(); i++)
			if (enemies.get(i).getHp() < 1) {
				defeatedZone.addUnit(enemies.get(i));
				enemies.remove(i);
			}
		if (!enemiesHasAlive()) {
			win = true;
			this.endGame();
		}
	}

	public void checkAllies() {
		for (int i = 0; i < allies.size(); i++)
			if (allies.get(i).getHp() < 1) {
				defeatedZone.addUnit(allies.get(i));
				allies.remove(i);
				this.paintToDefault();
			}

		if (!alliesHasAlive()) {
			win = false;
			this.endGame();
		}
	}

	public void endGame() {
		this.gameScreen.endGame(this.formResult(win));
	}

	private String[] formResult(boolean win) {
		String[] result = new String[4];

		result[0] = win ? "You WON" : "You LOST";

		int aliveAlliesCount = allies.size();
//		int aliveAlliesCount = 0;
//		for (int i = 0; i < allies.size; i++)
//			if (allies.get(i).isAlive())
//				aliveAlliesCount++;
//		result[1] = "Your team has " + Integer.toString(aliveAlliesCount) + " units alive";

		int aliveEnemiesCount = enemies.size();
//		int aliveEnemiesCount = 0;
//		for (int i = 0; i < enemies.size; i++)
//			if (enemies.get(i).isAlive())
//				aliveEnemiesCount++;
//		result[2] = "Enemy team has " + Integer.toString(aliveEnemiesCount) + " units alive";

		if (win)
			result[1] = "Your team has " + Integer.toString(aliveAlliesCount) + " units alive";
		else
			result[1] = "Enemy team has " + Integer.toString(aliveEnemiesCount) + " units alive";

		result[2] = "You have slain " + Integer.toString(enemiesCount - enemies.size()) + " enemy units";

		result[3] = "Enemy have slain " + Integer.toString(alliesCount - allies.size()) + " your units";

		return result;
	}

	public void setGame(TurnBasedStrategy game) {
		this.game = game;
	}

	public void setScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	public void setScreen(CompaignGameScreen compaignGameScreen) {
		this.compaignGameScreen = compaignGameScreen;
	}

	public boolean alliesHasAlive() {
		for (int i = 0; i < this.allies.size(); i++)
			if (this.allies.get(i).isAlive())
				return true;
		return false;
	}

	public boolean enemiesHasAlive() {
		for (int i = 0; i < this.enemies.size(); i++)
			if (this.enemies.get(i).isAlive())
				return true;
		return false;
	}

	public void killUnit(Unit unit) {
		defeatedZone.addUnit(unit);
	}

	private boolean unitsAreDone() {
		int k, j;
		k = 0;
		j = 0;
		for (int i = 0; i < allies.size(); i++)
			if (allies.get(i).isAlive()) {
				j += !allies.get(i).isReplaceable() ? 1 : 0;
				k++;
			}
//		Gdx.app.log("tag", Integer.toString(i) + " : " + Integer.toString(j));
		return k == j ? true : false;
	}

	public void makeUnitsRaplaceable() {
		for (int i = 0; i < allies.size(); i++) {
			allies.get(i).getCell().setRegion(this.textureAtlas.findRegion("cell"));
			allies.get(i).setReplaceability(true);
		}
	}
}
