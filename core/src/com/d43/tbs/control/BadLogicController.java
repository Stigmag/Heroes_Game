package com.d43.tbs.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.d43.tbs.view.screen.GameScreen;

public class BadLogicController {

	private Polygon badLogicBounds;

	private float speed, velocity = 10f, speedMax = 10f;
	private float rotationSpeed = 30f;

	public BadLogicController(Polygon badLogicBounds) {
		this.badLogicBounds = badLogicBounds;
	}

	public void handle() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			this.speed += this.velocity * GameScreen.delta;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			this.speed -= this.velocity * GameScreen.delta;
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			stop();

		this.speed = this.sliceSpeed();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			this.badLogicBounds.rotate(rotationSpeed * speed * GameScreen.delta);
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			this.badLogicBounds.rotate(-rotationSpeed * speed * GameScreen.delta);

		badLogicBounds.setPosition(
				badLogicBounds.getX()
						+ MathUtils.cosDeg(badLogicBounds.getRotation() + 90) * this.speed * GameScreen.delta,
				badLogicBounds.getY()
						+ MathUtils.sinDeg(badLogicBounds.getRotation() + 90) * this.speed * GameScreen.delta);

	}

	private void stop() {
		if (this.speed > 0)
			this.speed -= this.velocity * GameScreen.delta;
		else if (this.speed < 0)
			this.speed += this.velocity * GameScreen.delta;
		else
			this.speed = 0;
	}

	private float sliceSpeed() {
		if (this.speed > this.speedMax)
			return this.speedMax;

		if (this.speed < -this.speedMax)
			return -this.speedMax;
		return this.speed;
	}

	public float getSpeed() {
		return this.speed;
	}
}
