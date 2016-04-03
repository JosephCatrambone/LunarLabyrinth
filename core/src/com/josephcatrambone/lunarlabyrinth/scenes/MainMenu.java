package com.josephcatrambone.lunarlabyrinth.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.josephcatrambone.lunarlabyrinth.MainGDXGame;
import com.josephcatrambone.lunarlabyrinth.actors.*;

/**
 * Created by josephcatrambone on 2016/04/01.
 */
public class MainMenu extends Scene {
	Stage stage;
	OrthographicCamera camera;
	Texture texture;
	ActionButton button;

	@Override
	public void create() {
		stage = new Stage(new FitViewport(640, 640));
		camera = (OrthographicCamera)stage.getCamera();
		camera.setToOrtho(false, 64, 64);
		Gdx.input.setInputProcessor(stage);

		texture = MainGDXGame.assetManager.get("badlogic.jpg", Texture.class);
		button = new ActionButton(10, 10, 50, 50, texture, () -> {
			MainGDXGame.pushScene(MainGame.class);
		});
		stage.addActor(button);
	}

	@Override
	public void dispose() {
		MainGDXGame.assetManager.unload("badlogic.jpg");
		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*
		batch.begin();
		button.draw(batch, 1.0f);
		batch.end();
		*/
		stage.draw();
	}

	@Override
	public void update(float dt) {
		//button.update(dt);
		stage.act(dt);
		if(Gdx.input.isButtonPressed(0)) {
			System.out.println(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
		}
	}

	@Override
	public void restore() {
		Gdx.input.setInputProcessor(this.stage);
	}
}
