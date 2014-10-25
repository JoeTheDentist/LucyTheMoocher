package com.lucythemoocher.loops;

import android.view.KeyEvent;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.controls.GlobalController;
import com.lucythemoocher.controls.KeysListener;
import com.lucythemoocher.util.Timer;

/**
 * Loop used for the Game, when player is playing (haha)
 */
public class LoopGame extends Loop implements KeysListener {

	private Timer lifeTime_;
	private int level_;
	
	/**
	 * Public constructor
	 * This constructor won't reset or launch the game. The
	 * game's instance should already have been initialized
	 * and game will continue.
	 */	
	public LoopGame() {
		registerKeys();
		lifeTime_ = new Timer(0);
	}
	
	/**
	 * Public constructor
	 * By specifying the level, you will reset the game and launch
	 * a new level. Use the default constructor to continue game.
	 * @param level
	 */
	public LoopGame(int level) {
		this();
		level_ = level;
	}
	
	@Override
	protected void load() {
		Globals.getInstance().getGame().load(level_);
	}
	
	@Override
	protected void start() {
		Globals.getInstance().getGame().start();
	}
	
	@Override
	protected void pause() {
		Globals.getInstance().getGame().pause();
	}
	
	@Override
	protected void resume() {
		registerKeys();
		Globals.getInstance().getGame().resume();
	}
	
	@Override
	protected void stop() {
		Globals.getInstance().getGame().stop();
	}
	
	@Override
	public void update() {
		Globals.getInstance().getGame().update();
	}
	
	/**
	 * Called for each rendering if this is the current loop
	 * Don't compute anything here, prefer update
	 */	
	public void render() {
		Globals.getInstance().getGame().render();
	}
	
	public void onLeaveLoop() {
		unregisterKeys();
	}
	
	public void registerKeys() {
		GlobalController.getInstance().registerKey(this);
	}

	public void unregisterKeys() {
		GlobalController.getInstance().unregisterKey(this);
	}

	public void onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK && lifeTime_.timeFromCreation() > 100 &&  event.getRepeatCount() == 0) {
			changeCurrentLoop(new LoopPause(this));
		}
	}

}
