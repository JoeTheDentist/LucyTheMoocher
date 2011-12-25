package com.lucythemoocher.game;

import android.util.Log;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.events.*;
import com.lucythemoocher.graphics.Background;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.physics.Map;
import com.lucythemoocher.util.Timer;
import com.lucythemoocher.R;

public class Game {
	private static PlayerCharacter character_;
	private static Map map_;
	private static GameThread gameThread_;
	private static int nbUpdates = 0;
	private static Event event_;
	private static Background background_;
	private static Timer timer_;
	
	public static void launchGame() {
		Log.d("Game", "launchGame");
		timer_ = new Timer(0);
		character_ = new PlayerCharacter();
		gameThread_ = new GameThread();
		map_ = new Map(R.raw.lvl1);
		event_ = new EventNormal();
		background_ = new Background();
		gameThread_.start();
		Globals.getInstance().getSounds().start();
	}
	
	
	public static void setSpeed(float dt) {
		timer_.setFactor(dt);
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
		nbUpdates++;
		timer_.addDt(Globals.getInstance().getTimer().getDt());
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
		return timer_.getTime();
	}
	
	public static float getDt() {
		return timer_.getDt();
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
