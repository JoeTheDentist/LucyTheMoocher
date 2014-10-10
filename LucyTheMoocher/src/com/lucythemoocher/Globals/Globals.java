package com.lucythemoocher.Globals;

import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.loops.MasterLoop;
import com.lucythemoocher.util.Resources;
import com.lucythemoocher.util.Timer;

/**
 * Global variables
 * Singleton pattern
 */
public class Globals {

	private static volatile Globals instance_ = null;
	
	private Camera camera_;
	private Timer timer_;
	private Game game_;
	private int lives_ = 3;
	private int level_ = 1;
	
	/**
	 * Instance getter
	 * @return Globals' instance
	 */
    public final static Globals getInstance() {
		if (Globals.instance_ == null) {
			synchronized(Globals.class) {
				if (Globals.instance_ == null) {
					Globals.instance_ = new Globals();
				}
			}
		}
		return Globals.instance_;
    }
    
    /**
     * Called by MasterLoop, update the globals
     */
    public void update() {
    	camera_.update();
    	timer_.update();
    }

    public Camera getCamera() {
    	return camera_;
    }
    
    public Timer getTimer() {
    	return timer_;
    }
    
    public Game getGame() {
    	return game_;
    }
    
    public int getLives() {
    	return lives_;
    }
    
    public int getLevel() {
    	return level_;
    }
    
    public void lose() {
    	lives_--;
    	if (lives_ > 0) {
    		MasterLoop.getInstance().newLevel();
    	} else {
    		level_ = 1;
    		lives_ = 3;
    		MasterLoop.getInstance().mainMenu();
    	}
    }
    
	private Globals() {
		camera_ = new Camera();
		timer_ = new Timer(0f);
		game_ = new Game();
	}
	
	/**
	 * Stop game
	 */
	public void stopGame() {
		if (game_.isStarted()) {
			game_.stop();
		}
	}
	
	public void leave() {
		Resources.getActivity().finish();
	}
}
