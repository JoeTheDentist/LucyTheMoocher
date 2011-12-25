package com.lucythemoocher.game;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.events.*;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.graphics.Background;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.physics.Map;
import com.lucythemoocher.R;

public class Game {
	private static PlayerCharacter character_;
	private static Camera cam_;
	private static Map map_;
	private static GameThread gameThread_;
	private static int nbUpdates = 0;
	private static Event event_;
	private static float time_;
	private static float dt_ = 1;
	private static Background background_;
	
	public static void launchGame(Camera cam) {
		Log.d("Game", "launchGame");
		cam_ = cam;
		character_ = new PlayerCharacter();
		gameThread_ = new GameThread();
		map_ = new Map(R.raw.lvl1);
		event_ = new EventNormal();
		gameThread_.start();
		background_ = new Background();
	}
	
	public static Camera getCam() {
		return cam_;
	}
	
	public static void setSpeed(float dt) {
		Cinematic.setGeneralSpeed(dt);
		Camera.setSpeed(dt);
		dt_ = dt;
	}
	
	public static Map getMap() {
		return map_;
	}
	
	public static Event getEvent() {
		return event_;
	}
	
	public static PlayerCharacter getCharacter() {
		return character_;
	}
	
	public static Background getBackground() {
		return background_;
	}
	
	public static void update() {
		time_ += dt_;
		nbUpdates++;
		if ( nbUpdates % 300  == 0 ) {
			if ( event_ instanceof EventSlow ) {
				event_ = new EventNormal(); 
			} else {
				event_ = new EventSlow();
			}
		}
	}
	
	public static int getTick() {
		return nbUpdates;
	}
	
	public static float getTime() {
		return time_;
	}
	
	public static void stop() {
		gameThread_.setRunning(false);
	}

	public static void moveStop() {
		character_.moveStop();
	}

	public static void moveLeft() {
		character_.moveLeft();
	}

	public static void moveRight() {
		character_.moveRight();
	}

	public static void moveUp() {
		character_.moveUp();
	}
	
	public static void attack() {
		character_.attack();
	}

	public static void moveFastRight() {
		character_.moveFastRight();
	}
	
	public static void moveFastLeft() {
		character_.moveFastLeft();
	}
}
