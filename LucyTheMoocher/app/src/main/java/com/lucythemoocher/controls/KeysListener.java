package com.lucythemoocher.controls;

import android.view.KeyEvent;


/**
 * Observer pattern to get keys from the global controller
 * Note that we use an interface for keys and a class for touchEvents
 * We can't use classes for keysListener since most of the classes using it
 * already extend classes
 *
 */
public interface KeysListener {
	
	/**
	 * Connect this with the global controls notifier
	 * use GlobalController.getInstance().registerKey(this);
	 */
	public void registerKeys(); 
			
	/**
	 * Disconnect this from the global controls notifier
	 * use GlobalController.getInstance().unregisterKey(this);
	 */
	public void unregisterKeys();
		
	/**
	 * Called if this is registered and if the global controller 
	 * gets an event e
	 * @param e
	 */
	public void onKeyDown(int KeyCode, KeyEvent event);
}
