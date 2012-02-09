package com.lucythemoocher.loops;

import android.view.KeyEvent;

import com.lucythemoocher.controls.GlobalController;
import com.lucythemoocher.controls.KeysListener;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.util.Timer;

/**
 * Loop used for the Game, when player is playing (haha)
 */
public class LoopGame extends Loop implements KeysListener {

	private Timer lifeTime_;
	
	/**
	 * Public constructor
	 */	
	public LoopGame() {
		registerKeys();
		lifeTime_ = new Timer(0);
	}
	
	/**
	 * Called each frame if this is the current loop
	 * Implement here every computation
	 */	
	public void update() {
		Game.update();
	}
	
	/**
	 * Called for each rendering if this is the current loop
	 * Don't compute anything here, prefer update
	 */	
	public void render() {
		Game.render();
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
			changeCurrentLoop(new LoopPause());
		}
	}

}
