package com.lucythemoocher.loops;

/**
 * State of the state pattern used for the main Loop. Each 
 * loop has to implement update and render, which will
 * be called by MasterLoop. 
 */
public abstract class Loop {
	/**
	 * Public constructor
	 */
	public Loop() {
	}
	
	/**
	 * Called each frame if this is the current loop
	 * Implement here every computation
	 */
	abstract public void update();
	
	/**
	 * Called for each rendering if this is the current loop
	 * Don't compute anything here, prefer update
	 */
	abstract public void render();
	
	/**
	 * Change the current loop (current state in 
	 * the state pattern).
	 * 
	 * @param loop The new loop
	 */
	public void changeCurrentLoop(Loop loop) {
		MasterLoop.getInstance().setLoop(loop);
	}

}
