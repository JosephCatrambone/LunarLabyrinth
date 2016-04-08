package com.josephcatrambone.lunarlabyrinth.actors;

import com.badlogic.gdx.utils.Align;

/**
 * Created by josephcatrambone on 3/30/16.
 */
public class WormHole extends Pawn {

	public WormHole(int x, int y, float mass) {
		this.setPosition(x, y, Align.center);
		this.mass = mass;
	}
}
