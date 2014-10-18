package com.lucythemoocher.game;

import android.util.Log;

import com.lucythemoocher.FX.FXManager;
import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.actors.MonstersManager;
import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.actors.ProjectilesManager;
import com.lucythemoocher.controls.ActionController;
import com.lucythemoocher.events.*;
import com.lucythemoocher.graphics.Background;
import com.lucythemoocher.graphics.HUD;
import com.lucythemoocher.physics.Map;
import com.lucythemoocher.sounds.SoundManager;
import com.lucythemoocher.util.Timer;
import com.lucythemoocher.R;

public class Game {
	private PlayerCharacter character_;
	private Map map_;
	private int nbUpdates = 0;
	private Event event_;
	private Background background_;
	private Timer timer_;
	private MonstersManager monsters_;
	private FXManager fx_;
	private ProjectilesManager projectiles_;
	private HUD hud_;
	private SoundManager sounds_;
	private boolean started_ = false;
	
	public Game() {
		Log.d("Game", "Create Game class");
		fx_ = new FXManager();
		projectiles_ = new ProjectilesManager();
		sounds_ = new SoundManager();
	}
	
	/**
	 * Load the game with the given level
	 * @param level to load
	 */
	public void load(int lvl) {
		Log.d("Game", "Loading level "+lvl);
		timer_ = new Timer(0);
		projectiles_.clear();
		LevelLoader ll = null;
		switch (lvl) {
		case 1:
			ll = new LevelLoader(R.raw.lvl1);
			break;
		default:
			Log.e("Game", "Can't find this level, game may crash");
			break;
		}
		character_ = ll.getCharacter();
		monsters_ = ll.getMonsters();
		map_ = new Map(ll);
		
		hud_ = new HUD(new ActionController());
		Globals.getInstance().getCamera().moveTo(character_.getX(), character_.getY());

		event_ = new EventNormal();
		background_ = new Background();
		
		sounds_.load();
	}
	
	/**
	 * Start the game
	 */
	public void start() {
		timer_.reset();
		sounds_.start();
		started_ = true;
	}
	
	/**
	 * Stop the game
	 */
	public void stop() {
		sounds_.stop();
		fx_.clear();
		projectiles_.clear();
		started_ = false;
	}
	
	/**
	 * Getter
	 * @return whether the game is started or not
	 */
	public boolean isStarted() {
		return started_;
	}
	
	/**
	 * Change the game's speed. It affects the symbolic time
	 * in the game. If factorDt is 0.5f, getTime() will increase 
	 * twice slower. Default value is 1.0f
	 * @param factorDt
	 */
	public void setSpeed(float factorDt) {
		timer_.setFactor(factorDt);
	}
	
	/**
	 * Getter
	 * @return The current map
	 */
	public Map getMap() {
		return map_;
	}
	
	/**
	 * Getter
	 * @return Current event
	 */
	public Event getEvent() {
		return event_;
	}
	
	/**
	 * Getter
	 * @return The main character
	 */
	public PlayerCharacter getCharacter() {
		return character_;
	}
	
	/**
	 * Getter
	 * @return Monsters Manager
	 */
	public MonstersManager getMonstersManager() {
		return monsters_;
	}
	
	/**
	 * Getter
	 * @return Monsters Manager
	 */
	public ProjectilesManager getProjectilesManager() {
		return projectiles_;
	}
	
	/**
	 * Getter
	 * @return FXManager
	 */
	public FXManager getFXManager() {
		return fx_;
	}
	
	/**
	 * Getter
	 * @return Level's background
	 */
	public Background getBackground() {
		return background_;
	}
	
	/**
	 * Update the game 
	 */
	public void update() {
		nbUpdates++;
		timer_.addDt(Globals.getInstance().getTimer().getDt());
		getCharacter().update();
		Globals.getInstance().getCamera().update();
		monsters_.update();
		fx_.update();
		projectiles_.update();
		hud_.update();
		sounds_.update();
	}
	
	public void render() {
		getBackground().draw();
		getMap().draw();
		projectiles_.render();
		monsters_.render();
		getCharacter().draw();
		fx_.render();
		hud_.draw();
	}
	
	/**
	 * Getter
	 * @return Number of frames since the begining
	 */
	public int getTick() {
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
	public float getTime() {
		return timer_.getTime();
	}
	
	/**
	 * Getter
	 * @return Time elapsed between the two last frames in ms
	 */
	public float getDt() {
		return timer_.getDt();
	}

}
