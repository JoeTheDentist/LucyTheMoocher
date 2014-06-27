package com.lucythemoocher.loops;

import android.util.Log;

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
	public final static MasterLoop getInstance() {
        if (MasterLoop.instance_ == null) {
           synchronized(MasterLoop.class) {
             if (MasterLoop.instance_ == null) {
            	 MasterLoop.instance_ = new MasterLoop();
             }
           }
        }
        return MasterLoop.instance_;
	}
	
	/**
	 * Change the loop (state pattern)
	 * 
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
		if (cam.canDraw()) {
			if (cam.lockScreen()) {
				loop_.render();
				cam.unlockScreen();
			}
			Log.w("MasterLoop", "Failing to draw, canvas not ready.");
		}
		
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
