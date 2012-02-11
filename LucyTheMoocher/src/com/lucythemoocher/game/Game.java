package com.lucythemoocher.game;

import android.util.Log;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.actors.MonstersManager;
import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.controls.PlayerController;
import com.lucythemoocher.events.*;
import com.lucythemoocher.graphics.Background;
import com.lucythemoocher.physics.Map;
import com.lucythemoocher.util.Timer;
import com.lucythemoocher.R;

public class Game {
	private static PlayerCharacter character_;
	private static Map map_;
	private static int nbUpdates = 0;
	private static Event event_;
	private static Background background_;
	private static Timer timer_;
	private static MonstersManager monsters_; // temporary
	
	/**
	 * Launch a game
	 */
	public static void launchGame(int lvl) {
		Log.d("Game", "launchGame");
		timer_ = new Timer(0);
		character_ = new PlayerCharacter(new PlayerController());
		switch (lvl) {
		case 1:
			map_ = new Map(R.raw.lvl1);
			break;
		default:
			Log.w("Game", "Can't find this level, game may crash");
			break;
		}
		
		monsters_ = new MonstersManager();

		event_ = new EventNormal();
		background_ = new Background();
		Globals.getInstance().getSounds().start();
	}
	
	/**
	 * Change the game's speed. It affects the symbolic time
	 * in the game. If factorDt is 0.5f, getTime() will increase 
	 * twice slower. Default value is 1.0f
	 * @param factorDt
	 */
	public static void setSpeed(float factorDt) {
		timer_.setFactor(factorDt);
	}
	
	/**
	 * Getter
	 * @return The current map
	 */
	public static Map getMap() {
		return map_;
	}
	
	/**
	 * Getter
	 * @return Current event
	 */
	public static Event getEvent() {
		return event_;
	}
	
	/**
	 * Getter
	 * @return The main character
	 */
	public static PlayerCharacter getCharacter() {
		return character_;
	}
	
	/**
	 * Getter
	 * @return Monsters Manager
	 */
	public static MonstersManager getMonstersManager() {
		return monsters_;
	}
	
	/**
	 * Getter
	 * @return Level's background
	 */
	public static Background getBackground() {
		return background_;
	}
	
	/**
	 * Update the game 
	 */
	public static void update() {
		nbUpdates++;
		timer_.addDt(Globals.getInstance().getTimer().getDt());
		getCharacter().update();
		Globals.getInstance().getCamera().followPoint(getCharacter().getX(), getCharacter().getY());
		monsters_.update();
	}
	
	public static void render() {
		getBackground().draw();
		getMap().draw();
		getCharacter().draw();	
		monsters_.render();
	}
	
	/**
	 * Getter
	 * @return Number of frames since the begining
	 */
	public static int getTick() {
		return nbUpdates;
	}
	
	/**
	 * Getter
	 * @return Time elpased since the begining of the game in ms 
	 * 
	 * (care, the time is relative to game speed: game can be slowed
	 * or accelerated by events. However, it doesn't depend on the
	 * hardware speed)
	 */
	public static float getTime() {
		return timer_.getTime();
	}
	
	/**
	 * Getter
	 * @return Time elapsed between the two last frames in ms
	 */
	public static float getDt() {
		return timer_.getDt();
	}

}
