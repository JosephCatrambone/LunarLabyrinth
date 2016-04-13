package com.josephcatrambone.lunarlabyrinth.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.josephcatrambone.lunarlabyrinth.actors.*;

import java.util.Random;

/**
 * Created by josephcatrambone on 4/1/16.
 */
public class MainGame extends Scene {
	final int MAP_WIDTH = 64;
	final int MAP_HEIGHT = 64;
	final int MAX_STARS = 10;
	final float MIN_SOLAR_MASS = 1e12f;
	final float MAX_SOLAR_MASS = 2e12f;
	final float GRAVITATIONAL_CONSTANT = 6.674e-11f;
	final float PLAYER_START_FUEL = 100;
	final float PLAYER_SHIP_THRUST = 1000f; // 500 is hard but doable. You have to really rely on grav swing.
	final float MIN_DIST_TO_SPAWN_EXIT = 16*16; // If the player is dead center, that means we have 32 pixels in one direction.
	final float MIN_DIST_TO_EXIT = 16;

	Stage stage;
	OrthographicCamera camera;
	//SpriteBatch batch;
	Random random;

	Ship player;
	WormHole goal;
	Star[] stars;
	int currentLevel = 0;

	@Override
	public void create() {
		random = new Random();
		stage = new Stage(new FitViewport(640, 640));
		camera = (OrthographicCamera)stage.getCamera();
		camera.setToOrtho(false, MAP_WIDTH, MAP_HEIGHT);
		Gdx.input.setInputProcessor(stage);

		// Place the player in some position around 1/3rd the way down the screen.
		player = new Ship(MAP_WIDTH/2, MAP_HEIGHT/3, PLAYER_START_FUEL, PLAYER_SHIP_THRUST);
		stage.addActor(player);

		// Start the goal 2/3rds down.
		goal = new WormHole(MAP_WIDTH/2, 2*(MAP_HEIGHT/3), 0);
		stage.addActor(goal);

		stars = new Star[MAX_STARS];
		for(int i=0; i < MAX_STARS; i++) {
			stars[i] = new Star(-10, -10, 0); // Off screen.
			stage.addActor(stars[i]); // Add all of them to the stage, but don't make all of them visible.
		}

		generateNewLevel();
	}

	void generateNewLevel() {
		// Empty all actors.
		// Add an action to all the stars to remove them or...
		/*
		for(Star s : new SnapshotArray<Star>(stage.getActors())) {
			s.remove(); // Allows us to iterate over entries while removing them.
		}
		*/

		// Refill the player's fuel and cancel their inertia.
		player.velocity.set(0, 0);
		player.setFuel(PLAYER_START_FUEL);

		// Create a new goal.
		do {
			goal.setPosition(random.nextInt(MAP_WIDTH), random.nextInt(MAP_HEIGHT));
		} while(Math.pow(player.getX()-goal.getX(), 2) + Math.pow(player.getY()-goal.getY(), 2) < MIN_DIST_TO_SPAWN_EXIT);

		// Move around the stars and, if the level is right, add more.
		for(int i=0; i < numStarsFromLevel(currentLevel); i++) {
			stars[i].setPosition(random.nextInt(MAP_WIDTH), random.nextInt(MAP_HEIGHT));
			stars[i].mass = random.nextFloat()*(MAX_SOLAR_MASS-MIN_SOLAR_MASS) + MIN_SOLAR_MASS;
		}
	}

	int numStarsFromLevel(int level) {
		return (int)(Math.log(level+1)/Math.log(2));
	}

	@Override
	public void dispose() {

		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.01f, 0.05f, 0.1f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void update(float dt) {
		// Update the controls
		handleInput();

		// Update ship positions.
		stage.act();

		// Recalculate forces on player.
		float distanceToNearestStar = Float.POSITIVE_INFINITY;
		for(int i=0; i < numStarsFromLevel(currentLevel); i++) {
			// F1 = G1 = G*(m1*m2)/r^2
			Star s = stars[i];
			Vector2 r = new Vector2(s.getX()-player.getX(), s.getY()-player.getY());
			float distanceSquared = (float)r.dst2(0, 0); // Squared magnitude of vector relative to zero.
			float force = GRAVITATIONAL_CONSTANT * s.mass * player.getEffectiveMass() / distanceSquared;
			float netForce = force/player.getEffectiveMass();
			player.acceleration.add(netForce * r.x, netForce * r.y);

			// While we're at it, track the distance ot the nearest star.
			if(distanceSquared < distanceToNearestStar) {
				distanceToNearestStar = distanceSquared;
			}
		}

		// Distance to goal.
		float distToGoal = new Vector2(player.getX() - goal.getX(), player.getY() - goal.getY()).dst2(0, 0);
		if(distToGoal < MIN_DIST_TO_EXIT) {
			currentLevel++;
			generateNewLevel();
		}

		// Check success or failure.
		System.out.println("Distance to nearest star: " + distanceToNearestStar);

		// Cap player pos to edge of screen.
		player.setX(Math.max(player.getWidth(), player.getX()));
		player.setX(Math.min(player.getX(), MAP_WIDTH-player.getWidth()-1));
		player.setY(Math.max(player.getHeight(), player.getY()));
		player.setY(Math.min(player.getY(), MAP_HEIGHT-player.getHeight()-1));
		System.out.println("Player X:" + player.getX());
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

		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			currentLevel++;
			generateNewLevel();
		}
	}

	@Override
	public void restore() {
		Gdx.input.setInputProcessor(this.stage);
	}
}
