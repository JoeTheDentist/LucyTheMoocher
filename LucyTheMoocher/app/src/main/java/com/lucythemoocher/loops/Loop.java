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
		onLeaveLoop();
		MasterLoop.getInstance().setLoop(loop);
	}
	
	/**
	 * Do the load of the resources of the loop.
	 * Useful for delaying load after construction.
	 * @see LivesMenuLoop.update
	 */
	protected void load() {}
	
	/**
	 * Do the start of the resources of the loop.
	 * Useful for delaying start after construction and load.
	 * @see LivesMenuLoop.update
	 */
	protected void start() {}
	
	/**
	 * Pause
	 * @see LivesMenuLoop.update
	 */
	protected void pause() {}
	
	/**
	 * Pause
	 * @see LivesMenuLoop.update
	 */
	protected void resume() {}
	
	/**
	 * Stop, makes sure to clean up resources for the loop.
	 * @see LivesMenuLoop.update
	 */
	protected void stop() {}

	/**
	 * Called when the loop is left for another loop
	 */
	public void onLeaveLoop() {}
	
	void unregisterKeys() {}
	
	/**
	 * If need to draw
	 * @return
	 */
	public boolean doRender() {
		return true;
	}
	
	protected boolean drawn_ = false;
}
