package com.d43.tbs.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.d43.tbs.TurnBasedStrategy;
import com.d43.tbs.control.MapPlaying;
import com.d43.tbs.model.map.Cell;
import com.d43.tbs.model.map.CellMap;
import com.d43.tbs.model.map.DefeatedZone;
import com.d43.tbs.model.unit.*;
import com.d43.tbs.utils.Animation;
import com.d43.tbs.view.ui.GameCompaignUI;
import com.d43.tbs.view.ui.GameUI;

import java.awt.*;
import java.util.ArrayList;

public class CompaignGameScreen implements  Screen{

        private SpriteBatch batch;
        private OrthographicCamera camera;
        public static TextureAtlas textureAtlas;
        public static float delta;
        private GameUI ui;

        ArrayList<Unit> allies;
        ArrayList<Unit> enemies;
        ArrayList<Unit> units;
        private CellMap map;
        DefeatedZone defeatedZone;

        private MapPlaying mapPlaying;

        private TurnBasedStrategy game;

        private boolean isFromFile;

        @Override
        public void show() {
            batch = new SpriteBatch();

// *********************************************************** MAP
            map = new CellMap(com.d43.tbs.view.screen.GameScreen.textureAtlas, textureAtlas.findRegion("dirt"), -Gdx.graphics.getWidth() / 2,
                    -Gdx.graphics.getHeight() / 2, 1366, 768);
            int a, b;
            a = 16;
            b = 10;
            map.initCells(a, b);
            for (int i = 0; i < a; i++)
                for (int j = 0; j < b; j++)
                    map.getCell(i, j).setCell();

            this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        private void init() {

            defeatedZone = new DefeatedZone(com.d43.tbs.view.screen.CompaignGameScreen.textureAtlas, textureAtlas.findRegion("0"), -5f, 10f, 1f, 1f);
            defeatedZone.initCells(this.units.size());
            defeatedZone.setAllies(allies);
            for (int i = 0; i < this.units.size(); i++)
                defeatedZone.getCell(i).setCell();


            this.mapPlaying = new MapPlaying(map, defeatedZone, allies, enemies);
            this.mapPlaying.setAtlas(com.d43.tbs.view.screen.CompaignGameScreen.textureAtlas);
            this.mapPlaying.setGame(game);
            this.mapPlaying.setScreen(this);

            map.setMapHandler(this.mapPlaying);
            for (int i = 0; i < allies.size(); i++) {
                allies.get(i).setMapHandler(this.mapPlaying);
            }
            map.placeUnits(allies);
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).setMapHandler(this.mapPlaying);
            }
            map.placeUnits(enemies);


//            ui = new GameCompaignUI(com.d43.tbs.view.screen.CompaignGameScreen.textureAtlas, this);
            ui.setUnits(allies, enemies);

// *********************************************************** UNITS CONTAINER
        }

        public void backToMenu() {
            this.game.backToMenu();
        }

        public void setGame(TurnBasedStrategy game) {
            this.game = game;
        }

        public void setFromFile(boolean isFromFile) {
            this.isFromFile = isFromFile;
        }

        public void setUnits(ArrayList<Unit> units) {
            if(this.isFromFile)
                unitsFromFile(units);
            else unitsFromInit(units);

            init();
        }

        private void unitsFromFile(ArrayList<Unit> units) {
            this.units = units;

            for(int i = 0; i < units.size(); i++) {
                Unit unit;
                Vector2 location = units.get(i).getLocation();
                Dimension size = units.get(i).getSize();

                if(units.get(i).getClass().toString().equals(Archer.class.toString())) {
                    unit = new Archer(com.d43.tbs.view.screen.GameScreen.textureAtlas.findRegion("archer"), location.x, location.y, size.width, size.height);
                } else if(units.get(i).getClass().toString().equals(Knight.class.toString())) {
                    unit = new Knight(com.d43.tbs.view.screen.GameScreen.textureAtlas.findRegion("knight"), location.x, location.y, size.width, size.height);
                } else if(units.get(i).getClass().toString().equals(Orc.class.toString())) {
                    unit = new Orc(com.d43.tbs.view.screen.GameScreen.textureAtlas.findRegion("orc"), location.x, location.y, size.width, size.height);
                } else if(units.get(i).getClass().toString().equals(Zombie.class.toString())) {
                    unit = new Zombie(com.d43.tbs.view.screen.GameScreen.textureAtlas.findRegion("zombie"), location.x, location.y, size.width, size.height);
                }
                else {
                    unit = new Archer(com.d43.tbs.view.screen.GameScreen.textureAtlas.findRegion("archer"), location.x, location.y, size.width, size.height);
                    Gdx.app.log("class determinig", "could't find required class");
                }

                unit.initAnimations(textureAtlas);
                Array<Animation> animations = unit.getAnimations();
                Vector2 coord = units.get(i).getCoordinates();
                Cell cell = map.getCell((int)coord.x, (int)coord.y);

                units.get(i).reInit(com.d43.tbs.view.screen.GameScreen.textureAtlas, cell, unit.getTextureRegion(), animations.get(1), animations.get(2));
            }

            this.allies = new ArrayList<Unit>();
            this.enemies = new ArrayList<Unit>();
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).isEnemy())
                    enemies.add(units.get(i));
                else
                    allies.add(units.get(i));
            }
        }

        private void unitsFromInit(ArrayList<Unit> units) {
            this.units = units;
            this.allies = new ArrayList<Unit>();
            this.enemies = new ArrayList<Unit>();
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).isEnemy())
                    enemies.add(units.get(i));
                else
                    allies.add(units.get(i));
            }
        }

        private void sortUnits() {
            for (int j = 0; j < units.size(); j++)
                for (int i = 0; i < units.size() - 1; i++)
                    if (units.get(i).getCell() != null && units.get(i + 1).getCell() != null)
                        if (map.cellExist(units.get(i).getCell().getBounds())
                                && map.cellExist(units.get(i + 1).getCell().getBounds()))
                            if (map.findCellCoord(units.get(i).getCell().getBounds()).y < map
                                    .findCellCoord(units.get(i + 1).getCell().getBounds()).y) {
                                Unit buff = units.get(i);
                                units.set(i, units.get(i + 1));
                                units.set(i + 1, buff);
                            }

        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            com.d43.tbs.view.screen.CompaignGameScreen.delta = delta;

            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            map.draw(batch);
            defeatedZone.draw(batch);
            batch.draw(com.d43.tbs.view.screen.CompaignGameScreen.textureAtlas.findRegion("game_behind"), -Gdx.graphics.getWidth() / 2,
                    -Gdx.graphics.getHeight() / 2);

            for (int i = 0; i < units.size(); i++)
                units.get(i).draw(batch, delta);

            batch.draw(com.d43.tbs.view.screen.CompaignGameScreen.textureAtlas.findRegion("choose_above"), -Gdx.graphics.getWidth() / 2,
                    -Gdx.graphics.getHeight() / 2);
            batch.end();

            ui.draw();

            this.sortUnits();
        }

        public void endGame(String[] result) {
            ui.setResult(result);
        }

        @Override
        public void resize(int width, int height) {
            camera = new OrthographicCamera(1366f, 768f);
            camera.update();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            batch.dispose();
            ui.dispose();
        }

        public void setTextureAtlas(TextureAtlas textureAtlas) {
            com.d43.tbs.view.screen.CompaignGameScreen.textureAtlas = textureAtlas;
        }
    }

