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
		stage.getViewport().update(64, 64, true);
		camera = (OrthographicCamera)stage.getCamera();
		camera.translate(0, 0, -1);
		Gdx.input.setInputProcessor(stage);

		player = new Ship(10, 10, 100, 2.0f);
		stage.addActor(player);
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
		camera.update(true);
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
