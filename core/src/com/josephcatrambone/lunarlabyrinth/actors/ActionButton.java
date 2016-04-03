package com.josephcatrambone.lunarlabyrinth.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * Created by josephcatrambone on 3/31/16.
 */
public class ActionButton extends Button {
	Runnable onClick = null;
	Texture texture = null;
	boolean wasPressed = false;

	public ActionButton(float x, float y, float w, float h, Texture texture, Runnable onClick) {
		this.setPosition(x, y);
		this.setSize(w, h);
		this.onClick = onClick;
		this.texture = texture;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	public void act(float timeDelta) {
		// TODO: We could use an input handler, but laziness wins out.
		if(this.isPressed()) {
			wasPressed = true;
		} else if(wasPressed) { // Just released.
			wasPressed = false;
			onClick.run();
		}
	}

}
