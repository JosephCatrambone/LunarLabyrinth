package com.josephcatrambone.lunarlabyrinth.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.josephcatrambone.lunarlabyrinth.MainGDXGame;

/**
 * Created by josephcatrambone on 2016/04/01.
 */
public class Star extends Pawn {
	public Star(float x, float y, float mass) {
		super(x, y, mass);
		this.spriteSheet = MainGDXGame.assetManager.get("star.png", Texture.class);
		for(int i=0; i < Direction.NUM_DIRECTIONS.ordinal(); i++) {
			this.animations[i] = new Animation(1.0f, new TextureRegion[]{new TextureRegion(spriteSheet, 4, 4)});
		}
	}

	@Override
	public void act(float dt) {

	}
}
