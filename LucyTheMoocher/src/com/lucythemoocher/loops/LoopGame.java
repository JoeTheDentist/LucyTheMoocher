package com.lucythemoocher.loops;

import com.lucythemoocher.controls.PlayerController;
import com.lucythemoocher.game.Game;

/**
 * Loop used for the Game, when player is playing (haha)
 */
public class LoopGame extends Loop {
	
	/**
	 * Public constructor
	 */	
	public LoopGame() {
		
	}
	
	/**
	 * Called each frame if this is the current loop
	 * Implement here every computation
	 */	
	public void update() {
		PlayerController.update();
		Game.getCharacter().update();
		Game.update();
	}
	
	/**
	 * Called for each rendering if this is the current loop
	 * Don't compute anything here, prefer update
	 */	
	public void render() {
		Game.getBackground().draw();
		Game.getMap().draw();
		Game.getCharacter().draw();		
	}

}
