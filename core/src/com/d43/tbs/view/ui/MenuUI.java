package com.d43.tbs.view.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.d43.tbs.model.unit.Unit;
import com.d43.tbs.view.screen.MenuScreen;

public class MenuUI {
	private TextureAtlas atlas;

	private Stage stage;
	private Skin skin;
	private BitmapFont font;

	private TextButton btnNew, btnContinue, btnExit;
	private ArrayList<TextButton> fileButtons;

	private MenuScreen screen;
	
	public MenuUI(TextureAtlas atlas, MenuScreen screen) {
		this.atlas = atlas;
		this.screen = screen;

		this.stage = new Stage(new StretchViewport(1366, 768));
		Gdx.input.setInputProcessor(stage);


		this.skin = new Skin(Gdx.files.internal("core/assets/skin.json"));
		skin.addRegions(this.atlas);
		font = new BitmapFont(Gdx.files.internal("core/assets/font.fnt"));

		initButtons();
		

//		stage.addActor(table);
	}

	private void initButtons() {
//		btnNew = createButton("New Game", centreX, centreY - 100);
//		btnContinue = createButton("New Game", centreX, centreY);
//		btnExit = createButton("New Game", centreX, centreY + 100);
		btnNew = createButton("New Game", -310, 280);
		btnNew.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				clickedOnStart();
			}
		});
		btnContinue = createButton("Start compaign", 80, 150);
		btnNew.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				clickedOnStart();
			}
		});
		btnContinue = createButton("Choose level", 10, 280);
		btnContinue.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				clickedOnContrinue();
			}
		});
		btnExit = createButton("Exit", 300, 280);
		btnExit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}
	
	private void clickedOnContrinue() {
		if(fileButtons != null)
			clearFileButtons();
		generateFileButtons();
	}
	
	public void clearFileButtons() {
		for(TextButton btn : fileButtons) {
			btn.remove();
		}
	}
	
	private void generateFileButtons() {
		ArrayList<String> names = this.screen.getFileNames();
		
		fileButtons = new ArrayList<TextButton>();
		
		float x = -600;
		float y = 60;
		float stepX = 150;
		float stepY = 80;
		
		int count = 0;
		
		for(String name : names) {
			String text = name.split("\\.")[0];
			TextButton btn = createFileButton(name, text, x, y);
			
			fileButtons.add(btn);
			x += stepX;
			count++;
			if(count % 9 == 0) {
//			if(count == 9) {
				x = -600;
				y -= stepY;
			}
		}

		Vector2 center = getCenter();
		Label l = createLabel("Saves:", 0.5f, center.x, center.y + 90);
		l.setPosition(l.getX() - l.getWidth()/4, l.getY());
	}
	
	private Vector2 getCenter() {
		return new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}

	private void clickedOnStart() {
		this.screen.startNewGame();
	}

	private TextButton createFileButton(final String name, String text, float x, float y) {
		final TextButton btn = createButton(text, x, y);
		
		btn.addListener(new ClickListener(Buttons.LEFT) {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ArrayList<Unit> units = screen.getUnits(name);
				screen.play(units);
			}
		});
		
		btn.addListener(new ClickListener(Buttons.RIGHT) {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screen.deleteFile(name);
				fileButtons.remove(btn);
				btn.remove();
			}
		});
		
		return btn;
	}
	
	private TextButton createButton(String text, float x, float y) {
		int lr = 30;
		int ud = 0;
		float fontScale = 0.45f;

		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = font;
		btnStyle.up = skin.getDrawable("cell");
		btnStyle.down = skin.getDrawable("cellMouseOnBlocked");
		btnStyle.over = skin.getDrawable("cellMouseOn");
		btnStyle.pressedOffsetX = 1;
		btnStyle.pressedOffsetY = -1;

		TextButton btn = new TextButton(text, btnStyle);
		btn.pad(ud, lr, ud, lr);
		btn.padBottom(ud + 7);
		btn.getLabel().setFontScale(fontScale);

		Table table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setPosition(x, y);
//		table.add(btn).width(280).height(50);
		table.add(btn).height(60);
		btn.setWidth(300);
		this.stage.addActor(table);
		/*
		 * btn.addListener(new ChangeListener() {
		 * 
		 * @Override public void changed(ChangeEvent event, Actor actor) {
		 * Gdx.app.log("tag", "ConfirmButton pressed"); } });
		 */
		return btn;
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
	}
	
	public void dispose() {
		stage.dispose();
	}
}
