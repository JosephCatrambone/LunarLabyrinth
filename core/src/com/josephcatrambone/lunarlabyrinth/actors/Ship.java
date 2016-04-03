package com.josephcatrambone.lunarlabyrinth.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.josephcatrambone.lunarlabyrinth.MainGDXGame;

/**
 * Created by josephcatrambone on 2016/04/01.
 */
public class Ship extends Pawn {
	float fuel;
	float thrust;

	public Ship(int x, int y, float fuel, float thrust) {
		super();
		this.fuel = fuel;
		this.thrust = thrust;
		this.spriteSheet = MainGDXGame.assetManager.get("ship.png", Texture.class);
		this.animations[0] = new Animation(1.0f, new TextureRegion[]{new TextureRegion(spriteSheet, 0, 0, 4, 4)});
		this.animations[1] = new Animation(1.0f, new TextureRegion[]{new TextureRegion(spriteSheet, 0, 0, 4, 4)});
		this.animations[2] = new Animation(1.0f, new TextureRegion[]{new TextureRegion(spriteSheet, 0, 0, 4, 4)});
		this.animations[3] = new Animation(1.0f, new TextureRegion[]{new TextureRegion(spriteSheet, 0, 0, 4, 4)});
		this.animations[4] = new Animation(1.0f, new TextureRegion[]{new TextureRegion(spriteSheet, 0, 0, 4, 4)});

		/*
		final Ship ref = this;
		this.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				// F = m*a
				// F/m = a
				switch(keycode) {
					case Input.Keys.W:
						ref.direction = Direction.UP;
						break;
					case Input.Keys.A:
						ref.direction = Direction.LEFT;
						break;
					case Input.Keys.S:
						ref.direction = Direction.DOWN;
						break;
					case Input.Keys.D:
						ref.direction = Direction.RIGHT;
						break;
				}
				return true;
			}

			public boolean keyUp(InputEvent event, int keycode) {
				ref.direction = Direction.NONE; // TODO: Lazy.
				return true;
			}

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
				return false;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
			}
		});
		*/
	}

	@Override
	public void act(float dt) {
		super.act(dt);
		System.out.println("Position: " + getX() + " " + getY());
		float jerk = thrust/(mass+fuel);
		switch(direction) {
			case RIGHT:
				this.velocity.x += jerk*dt;
				break;
			case UP:
				this.velocity.y += jerk*dt;
				break;
			case LEFT:
				this.velocity.x -= jerk*dt;
				break;
			case DOWN:
				this.velocity.y -= jerk*dt;
				break;
			case NONE:
			default:
				break;
		}
		stateTime = (stateTime + dt) % MAX_STATE_TIME;
	}

}
