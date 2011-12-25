/**
 * 
 */
package com.lucythemoocher.Globals;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.util.Timer;

/**
 * Global variables
 * Singleton pattern
 */
public class Globals {

	private static volatile Globals instance_ = null;
	
	private Camera camera_;
	private Timer timer_;
	
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

    public Camera getCamera() {
    	return camera_;
    }
    
    public Timer getTimer() {
    	return timer_;
    }
    
	private Globals() {
		camera_ = new Camera();
		timer_ = new Timer(0f);
	}
}
