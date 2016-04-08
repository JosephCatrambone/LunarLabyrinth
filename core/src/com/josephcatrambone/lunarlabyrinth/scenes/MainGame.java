package com.josephcatrambone.lunarlabyrinth.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.josephcatrambone.lunarlabyrinth.actors.*;

/**
 * Created by josephcatrambone on 4/1/16.
 */
public class MainGame extends Scene {
	Stage stage;
	OrthographicCamera camera;
	//SpriteBatch batch;

	Ship player;
	WormHole goal;
	Pawn[] stars;

	@Override
	public void create() {
		stage = new Stage(new FitViewport(640, 640));
		camera = (OrthographicCamera)stage.getCamera();
		camera.setToOrtho(false, 64, 64);
		Gdx.input.setInputProcessor(stage);

		// Place the player in some position.
		player = new Ship(10, 10, 1000, 15000f);
		stage.addActor(player);

		// Create a new goal.
		goal = new WormHole(1, 1, 10);

	}

	@Override
	public void dispose() {

		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(1, 0, 1, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void update(float dt) {
		// Update the controls
		handleInput();

		// Update ship positions.
		stage.act();

		// Recalculate forces.
	}

	void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.direction = Pawn.Direction.RIGHT;
		} else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.direction = Pawn.Direction.UP;
		} else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.direction = Pawn.Direction.LEFT;
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.direction = Pawn.Direction.DOWN;
		} else {
			player.direction = Pawn.Direction.NONE;
		}
	}

	@Override
	public void restore() {
		Gdx.input.setInputProcessor(this.stage);
	}
}
