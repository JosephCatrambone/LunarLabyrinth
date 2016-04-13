package com.josephcatrambone.lunarlabyrinth.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import com.josephcatrambone.lunarlabyrinth.MainGDXGame;

/**
 * Created by josephcatrambone on 3/30/16.
 */
public class WormHole extends Pawn {

	public WormHole(int x, int y, float mass) {
		super(x, y, mass);
		this.spriteSheet = MainGDXGame.assetManager.get("wormhole.png", Texture.class);
	}
}
