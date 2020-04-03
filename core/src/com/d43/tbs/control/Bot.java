package com.d43.tbs.control;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.unit.RangeUnit;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.utils.CellCalculator;
import com.d43.tbs.utils.Rnd;

public class Bot {

	ArrayList<Unit> allies;
	ArrayList<Unit> enemies;
	CellMap map;
	MapPlaying mapChecker;

	public Bot(ArrayList<Unit> allies, ArrayList<Unit> enemies, MapPlaying mapChecker, CellMap map) {
		this.allies = allies;
		this.enemies = enemies;
		this.mapChecker = mapChecker;
		this.map = map;
	}

	public void makeBotsMove() {
		Array<Cell> moveCells, attackCells;

		sortEnemiesByType();
		sortEnemiesByAlive();

		for (int i = 0; i < this.enemies.size(); i++) {

			if (!enemies.get(i).isAlive())
				continue;

			this.enemies.get(i).setDelay(i * 0.5f + 1f);

			Vector2 coord = map.findCellCoord(this.enemies.get(i).getCell().getBounds());

			CellCalculator calculator = new CellCalculator(false, this.map, coord,
					this.enemies.get(i).getRangeMovement(), this.enemies.get(i).getRangeAttack());

			moveCells = calculator.getAvailableCellsForMove();
			attackCells = calculator.getAvailableCellsForAttack();

			if (attackCells.size > 0) {
				this.enemies.get(i).setDelay(0f);
				enemies.get(i).attack(attackCells.get(0).getUnit());
				this.mapChecker.checkAllies();
			} else {
				Cell cell = moveCells.get(Rnd.generate(0, moveCells.size - 1));
				enemies.get(i).setCell(cell, map.findCellCoord(cell));
			}
		}

		int lastAliveIndex = findLastAliveEnemy();

		if (lastAliveIndex >= 0)
			this.enemies.get(lastAliveIndex).lastEnemy();

	}

	private void sortEnemiesByAlive() {
		for (int i = 0; i < this.enemies.size(); i++) {
			for (int j = 0; j < this.enemies.size() - 1; j++) {
				if (!enemies.get(j).isAlive() && enemies.get(j + 1).isAlive()) {
					Unit buff = enemies.get(j);
					enemies.set(j, enemies.get(j + 1));
					enemies.set(j + 1, buff);
				}
			}
		}
	}

	private void sortEnemiesByType() {
		for (int i = 0; i < this.enemies.size(); i++)
			for (int j = 0; j < this.enemies.size() - 1; j++)
				if (!RangeUnit.class.isAssignableFrom(enemies.get(j).getClass())
						&& RangeUnit.class.isAssignableFrom(enemies.get(j + 1).getClass())) {
					Unit buff = enemies.get(j);
					enemies.set(j, enemies.get(j + 1));
					enemies.set(j + 1, buff);
				}

	}

	private int findLastAliveEnemy() {
		for (int i = 0; i < enemies.size(); i++)
			if (!enemies.get(i).isAlive())
				return i - 1;
			else if (i == enemies.size() - 1)
				return i;
		return -1;
	}

}
