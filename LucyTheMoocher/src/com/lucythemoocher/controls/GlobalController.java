package com.lucythemoocher.controls;

import java.util.ArrayList;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;


/**
 * Singleton class.
 * Recieve hardware events and notifies
 * its listeners (observer pattern) 
 *
 */
public class GlobalController {

	private ArrayList<TouchListener> touchListenners_;
	private ArrayList<ButtonListener> buttonListenners_;
	private ArrayList<KeysListener> keysListeners_;
	private static GlobalController instance_ = null;
	
	/**
	 * Private constructor
	 */
	private GlobalController() {
		touchListenners_ = new ArrayList<TouchListener>();
		buttonListenners_ = new ArrayList<ButtonListener>();
		keysListeners_ = new ArrayList<KeysListener>();
	}
	
	/**
	 * Getter of the instance of GlobalController
	 * @return instance
	 */
	public static GlobalController getInstance() {
		if ( instance_ == null ) {
			instance_ = new GlobalController();
		}
		return instance_;
	}
	
	/**
	 * Register
	 * @param l
	 */
	void registerTouch(TouchListener l) {
		touchListenners_.add(l);
	}

	public void unregisterTouch(TouchListener l) {
		touchListenners_.remove(l);
	}
	
	public void registerKey(KeysListener l) {
		keysListeners_.add(l);
	}
	
	public void unregisterKey(KeysListener l) {
		keysListeners_.remove(l);
	}
	
	/**
	 * Register
	 * @param l
	 */
	void registerButton(ButtonListener l) {
		buttonListenners_.add(l);
	}
	
	
	/**
	 * Process the event
	 * @param event
	 */
	public void process(MotionEvent event) {
		alertTouch(event);
	}
	
	public void processKey(int KeyCode, KeyEvent event) {
		Log.d("plop", "processKey");
		for (int i = 0; i < keysListeners_.size(); ++i) {
			keysListeners_.get(i).onKeyDown(KeyCode, event);
		}
	}
	
	private void alertTouch(MotionEvent event) {
		for (int i = 0; i < touchListenners_.size(); ++i) {
			touchListenners_.get(i).motion(event);
		}
	}
	
	private void alertButton(MotionEvent event) {
		for ( ButtonListener l : buttonListenners_ ) {
			l.button(event);
		}
	}
	
}
