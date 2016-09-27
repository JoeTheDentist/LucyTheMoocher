package com.lucythemoocher.controls;

import android.view.MotionEvent;

/**
 * This class is automatically connected to GlobalController
 * (which is a singleton existing during the whole execution)
 * with the observer design pattern. When the GlobalController 
 * gets an action button, it notifies every connected ButtonListeners.
 * 
 * @see GlobalController
 *
 */
public abstract class ButtonListener {
	
	/**
	 * Constructor (connect the object to the controller)
	 */
	public ButtonListener() {
		GlobalController.getInstance().registerButton(this);
	}
	
	/**
	 * When a button is pushed
	 * @param event
	 */
	public abstract void button(MotionEvent event);
}
