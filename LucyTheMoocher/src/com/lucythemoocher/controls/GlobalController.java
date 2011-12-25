package com.lucythemoocher.controls;

import java.util.ArrayList;

import android.view.MotionEvent;

public class GlobalController {

	private ArrayList<TouchListener> touchListenners_;
	private ArrayList<ButtonListener> buttonListenners_;
	private static GlobalController instance_ = null;
	
	private GlobalController() {
		touchListenners_ = new ArrayList<TouchListener>();
		buttonListenners_ = new ArrayList<ButtonListener>();
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
		if ( event.getAction() == MotionEvent.ACTION_POINTER_UP ||
				event.getAction() == MotionEvent.ACTION_POINTER_DOWN ) {
			alertTouch(event);
		} else {
			alertButton(event);
		}
	}
	
	private void alertTouch(MotionEvent event) {
		for ( TouchListener l : touchListenners_ ) {
			l.motion(event);
		}
	}
	
	private void alertButton(MotionEvent event) {
		for ( ButtonListener l : buttonListenners_ ) {
			l.button(event);
		}
	}
}
