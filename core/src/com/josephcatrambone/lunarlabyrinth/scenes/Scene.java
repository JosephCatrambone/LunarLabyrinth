package com.josephcatrambone.lunarlabyrinth.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by josephcatrambone on 2016/04/01.
 */
public abstract class Scene {
	public abstract void create ();
	public abstract void dispose();
	public abstract void render (float deltaTime);
	public abstract void update(float dt);
	public void pause() {}
	public void restore() {} // Called after stack push.
}
