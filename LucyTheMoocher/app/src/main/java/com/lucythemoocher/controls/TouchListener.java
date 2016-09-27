package com.lucythemoocher.controls;

import android.view.MotionEvent;

/**
 * Observer pattern to get events from the global controller
 *
 */
public abstract class TouchListener {
	
	public TouchListener() {
		register();
	}
	
	/**
	 * Connect this with the global controls notifier
	 */
	public void register() {
		GlobalController.getInstance().registerTouch(this);
	}
	
	/**
	 * Disconnect this from the global controls notifier
	 */
	public void unregister() {
		GlobalController.getInstance().unregisterTouch(this);
	}
	
	/**
	 * Called if this is registered and if the global controller 
	 * gets an event e
	 * @param e
	 */
	public abstract void motion(MotionEvent e);
}
