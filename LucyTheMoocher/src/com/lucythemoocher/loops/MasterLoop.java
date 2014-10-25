package com.lucythemoocher.loops;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Camera;

/**
 * This class handles the main loop. It uses a state pattern
 * with the class Loop. For example, LoopGame inherits Loop and
 * handles the loop during the game. Update is used each frame, 
 * whatever the state of the game (menus, credits, game etc.)
 * MasterLoop is a singleton
 *
 */
public class MasterLoop {
	
	/**
	 * Get the instance of the singleton
	 * @return Instance
	 */
	synchronized public final static MasterLoop getInstance() {
		if (MasterLoop.instance_ == null) {
			MasterLoop.instance_ = new MasterLoop();
		}
        return MasterLoop.instance_;
	}
	
	/**
	 * Change the loop (state pattern)
	 * @param loop The new state
	 */
	public void setLoop(Loop loop) {
		loop_ = loop;
	}
	
	/**
	 * Called each frame
	 */
	public void update() {
		Globals.getInstance().update();
		loop_.update();
	}
	
	/**
	 * Update rendering
	 */
	public void render() {
		Camera cam = Globals.getInstance().getCamera();
		if (loop_.doRender() && cam.canDraw() && cam.lockScreen()) {
			cam.prepare();
			loop_.render();
			cam.unlockScreen();
		}
	}
	
	public void mainMenu() {
		setLoop(new InitMenuLoop());
	}
	
	public void gameOver() {
		setLoop(new GameOverLoop(3000));
	}
	
	public void restartLevel() {
		setLoop(new LivesMenuLoop(Globals.getInstance().getLives(), 1000, new LoopGame(Globals.getInstance().getLevel())));
	}
	
	/**
	 * Private constructor for singleton pattern
	 */
	private MasterLoop() {
		loop_ = new InitMenuLoop();
	}
	
	private static volatile MasterLoop instance_ = null;
	private Loop loop_;
}
