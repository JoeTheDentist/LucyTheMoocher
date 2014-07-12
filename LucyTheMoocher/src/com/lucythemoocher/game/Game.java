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
import com.lucythemoocher.util.Timer;
import com.lucythemoocher.R;

public class Game {
	private PlayerCharacter character_;
	private Map map_;
	private int nbUpdates = 0;
	private Event event_;
	private Background background_;
	private Timer timer_;
	private MonstersManager monsters_; // temporary
	private FXManager fx_; //temp
	private ProjectilesManager projectiles_;
	private HUD hud_;
	
	/**
	 * Launch a game
	 */
	public void launchGame(int lvl) {
		Log.d("Game", "launchGame");
		timer_ = new Timer(0);
		character_ = new PlayerCharacter(new ActionController());
		hud_ = new HUD(new ActionController());
		switch (lvl) {
		case 1:
			map_ = new Map(R.raw.lvl1);
			break;
		default:
			Log.w("Game", "Can't find this level, game may crash");
			break;
		}
		
		monsters_ = new MonstersManager();
		fx_ = new FXManager();
		projectiles_ = new ProjectilesManager();

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
