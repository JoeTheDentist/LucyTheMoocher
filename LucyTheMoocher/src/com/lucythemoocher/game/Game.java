package com.lucythemoocher.game;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.events.*;
import com.lucythemoocher.graphics.Background;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.physics.Map;
import com.lucythemoocher.R;

public class Game {
	private static PlayerCharacter character_;
	private static Map map_;
	private static GameThread gameThread_;
	private static int nbUpdates = 0;
	private static Event event_;
	private static float time_;
	private static float gameSpeed_ = 1; // factor controlling game's speed
	private static Background background_;
	
	public static void launchGame() {
		Log.d("Game", "launchGame");
		character_ = new PlayerCharacter();
		gameThread_ = new GameThread();
		map_ = new Map(R.raw.lvl1);
		event_ = new EventNormal();
		gameThread_.start();
		background_ = new Background();
	}
	
	
	public static void setSpeed(float dt) {
		Cinematic.setGeneralSpeed(dt);
		gameSpeed_ = dt;
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
		time_ += gameSpeed_;
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
