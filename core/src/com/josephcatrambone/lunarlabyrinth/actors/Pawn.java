package com.josephcatrambone.lunarlabyrinth.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.josephcatrambone.lunarlabyrinth.MainGDXGame;

/**
 * Created by josephcatrambone on 4/1/16.
 */
public class Pawn extends Actor {
	public enum Direction {NONE, RIGHT, UP, LEFT, DOWN, NUM_DIRECTIONS};
	public static float MAX_STATE_TIME = 100f; // 100 seconds.

	public Texture spriteSheet;
	public Animation[] animations; // Idle, right, up, left, down.
	public Direction direction;
	public float stateTime;
	public Vector2 velocity;
	public Vector2 acceleration;
	public float mass;

	public Pawn() {
		this.direction = Direction.NONE;
		this.animations = new Animation[Direction.NUM_DIRECTIONS.ordinal()];
		this.velocity = new Vector2();
		this.acceleration = new Vector2();
		mass = 1.0f;
	}

	@Override
	public void draw(Batch spriteBatch, float alpha) {
		spriteBatch.draw(animations[direction.ordinal()].getKeyFrame(stateTime), getX(), getY());
	}

	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);
		this.setPosition(getX() + velocity.x*deltaTime, getY() + velocity.y*deltaTime, Align.center);
		velocity = velocity.add(acceleration.scl(deltaTime));
	}
}
