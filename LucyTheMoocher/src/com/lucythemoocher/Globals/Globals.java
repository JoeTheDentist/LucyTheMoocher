/**
 * 
 */
package com.lucythemoocher.Globals;
import com.lucythemoocher.graphics.Camera;

/**
 * Global variables
 * Singleton pattern
 */
public class Globals {

	private static volatile Globals instance_ = null;
	
	private Camera camera_;
	
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
    
	private Globals() {
		camera_ = new Camera();
	}
}
