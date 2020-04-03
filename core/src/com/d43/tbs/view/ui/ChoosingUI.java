package com.d43.tbs.view.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.view.screen.ChooseScreen;

public class ChoosingUI {
	private TextureAtlas atlas;

	private Stage stage;
	private Skin skin;
	private BitmapFont font;

	private Array<Label> hps;
	private Array<Label> info;
	private ArrayList<Unit> units;
	
	private TextButton btnStart, btnSave, btnBack, btnExit;
	
	private ChooseScreen screen;

	public ChoosingUI(TextureAtlas atlas, ChooseScreen screen) {
		this.atlas = atlas;
		this.screen = screen;

		this.stage = new Stage(new StretchViewport(1366, 768));
		Gdx.input.setInputProcessor(stage);

		this.skin = new Skin(Gdx.files.internal("core/assets/skin.json"));
		skin.addRegions(this.atlas);
		font = new BitmapFont(Gdx.files.internal("core/assets/font.fnt"));
		
		hps = new Array<Label>();
		info = new Array<Label>();
		
		initButtons();
	}
	
	private void initButtons() {
//		btnNew = createButton("New Game", centreX, centreY - 100);
//		btnContinue = createButton("New Game", centreX, centreY);
//		btnExit = createButton("New Game", centreX, centreY + 100);
		
		btnBack = createButton("Back", -250, 270);
		btnBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screen.backToMenu();
			}
		});
		
		btnSave = createButton("Save level", -100, 270);
		btnSave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screen.save();
			}
		});
		
		btnStart = createButton("Start", 50, 270);
		btnStart.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				screen.start();
			}
		});
		
		btnExit = createButton("Exit", 200, 270);
		btnExit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}
	
	private TextButton createButton(String text, float x, float y) {
		int lr = 10;
		int ud = 0;
		float fontScale = 0.3f;

		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = font;
		btnStyle.up = skin.getDrawable("cell");
		btnStyle.down = skin.getDrawable("cellMouseOnBlocked");
		btnStyle.over = skin.getDrawable("cellMouseOn");
//		btnStyle.checked = skin.getDrawable("cellMouseOnBlocked");
		btnStyle.pressedOffsetX = 1;
		btnStyle.pressedOffsetY = -1;

		TextButton btn = new TextButton(text, btnStyle);
//		btn.setPosition(x, y);
		btn.pad(ud, lr, ud, lr);
		btn.padBottom(ud + 7);
		btn.getLabel().setFontScale(fontScale);

		Table table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setPosition(x, y);
		table.add(btn);
		this.stage.addActor(table);
		/*
		 * btn.addListener(new ChangeListener() {
		 * 
		 * @Override public void changed(ChangeEvent event, Actor actor) {
		 * Gdx.app.log("tag", "ConfirmButton pressed"); } });
		 */
		return btn;
	}

	private void initLabels() {
		hps = new Array<Label>();
		for (int i = 0; i < units.size(); i++) {
			Label label = createLabel("", 0.3f, 0, 0);
			hps.add(label);
		}
		
		float koef = 6;
		String info1 = "Press RIGHT CLICK to delete unit from map";
//		Label lInfo1 = createLabel(info1, 0.3f, Gdx.graphics.getWidth()/2 - info1.length() * koef, Gdx.graphics.getHeight() - 170);
		Label lInfo1 = createLabel(info1, 0.3f, Gdx.graphics.getWidth()/2 - info1.length() * koef, -60);
//		String info2 = "Press ENTER to start play";
//		Label lInfo2 = createLabel(info2, 0.3f, Gdx.graphics.getWidth()/2 - info2.length() * koef, Gdx.graphics.getHeight() - 210);
	}

	public void attachLabels() {
		for (int i = 0; i < units.size(); i++) {
			hps.get(i).setPosition(Gdx.graphics.getWidth() / 2 + units.get(i).getBounds().getX() + 25,
					Gdx.graphics.getHeight() / 2 + units.get(i).getBounds().getY() + 5);
			if (units.get(i).getHp() > 0) {
				hps.get(i).setText(units.get(i).getHp());
			} else
				hps.get(i).setText("");
		}
	}

	public void setUnits(ArrayList<Unit> allies, ArrayList<Unit> enemies) {
		units = new ArrayList<Unit>();
		for (int i = 0; i < allies.size(); i++)
			units.add(allies.get(i));
		for (int i = 0; i < enemies.size(); i++)
			units.add(enemies.get(i));

		this.initLabels();
	}

	private Label createLabel(String text, float fontScale, float x, float y) {
		Label label = new Label(text, this.skin.get("default", LabelStyle.class));
		label.setPosition(x, y);
		label.setFontScale(fontScale);

		this.stage.addActor(label);

		return label;
	}

	public void draw() {
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		stage.act();
		stage.draw();

		this.attachLabels();
	}

	public void dispose() {
		stage.dispose();
	}
}
