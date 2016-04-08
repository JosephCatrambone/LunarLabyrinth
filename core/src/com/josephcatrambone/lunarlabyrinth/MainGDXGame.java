package com.josephcatrambone.lunarlabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.josephcatrambone.lunarlabyrinth.scenes.MainMenu;
import com.josephcatrambone.lunarlabyrinth.scenes.Scene;

import java.util.Random;
import java.util.Stack;

public class MainGDXGame extends ApplicationAdapter {
	public static AssetManager assetManager;
	public static Random random;
	public static Preferences userData;
	public static Stack<Scene> sceneManager;

	static {
		random = new Random();
		assetManager = new AssetManager();
		sceneManager = new Stack<Scene>();
	}
	
	@Override
	public void create () {
		// Can't init user data in static.
		MainGDXGame.userData = Gdx.app.getPreferences("lunarlabyrinth_userdata");

		// Preload all assets.
		MainGDXGame.assetManager.load("badlogic.jpg", Texture.class);
		MainGDXGame.assetManager.load("star.png", Texture.class);
		MainGDXGame.assetManager.load("ship.png", Texture.class);
		MainGDXGame.assetManager.finishLoading();

		//
		MainGDXGame.pushScene(MainMenu.class);

		// Set up first run stuff.

	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();

		sceneManager.peek().render(dt);
		sceneManager.peek().update(dt);
	}

	public static void popScene() {
		if(!sceneManager.isEmpty()) {
			sceneManager.pop();
		}

		// Report empty scene stack.
		if(sceneManager.isEmpty()) {
			// TODO: Report error.
			MainGDXGame.pushScene(MainMenu.class);
		}

		// Notify the new person on top that it's active.
		sceneManager.peek().restore();
	}

	public static void pushScene(Class<? extends Scene> newSceneClass) {
		try {
			Scene newScene = newSceneClass.newInstance();
			newScene.create();
			pushScene(newScene);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void pushScene(Scene newScene) {
		if(!sceneManager.isEmpty() && sceneManager.peek() != null) {
			sceneManager.peek().pause();
		}
		sceneManager.push(newScene);
		newScene.restore();
	}

	/*** swapScene
	 * Given a class, instantiate a scene and call create().
	 * Remove the old scene (if it exists) and destruct it.
	 * @param newSceneClass
	 */
	public static void swapScene(Class<? extends Scene> newSceneClass) {
		try {
			Scene newScene = newSceneClass.newInstance();
			newScene.create();
			swapScene(newScene);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void swapScene(Scene newScene) {
		if(!sceneManager.isEmpty()) {
			Scene oldScene = sceneManager.pop();
			oldScene.dispose();
		}
		sceneManager.push(newScene);
		newScene.restore();
	}
}
