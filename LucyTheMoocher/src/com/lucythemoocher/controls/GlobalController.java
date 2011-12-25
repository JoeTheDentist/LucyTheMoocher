package com.lucythemoocher.controls;

import java.util.ArrayList;

import android.view.MotionEvent;

public class GlobalController {

	private ArrayList<TouchListener> touchListenners_;
	private ArrayList<ButtonListener> buttonListenners_;
	
	private GlobalController() {
		touchListenners_ = new ArrayList<TouchListener>();
		buttonListenners_ = new ArrayList<ButtonListener>();
	}
	
	public void process(MotionEvent event) {
		if ( event.getAction() == MotionEvent.ACTION_POINTER_UP ||
				event.getAction() == MotionEvent.ACTION_POINTER_DOWN ) {
			alertTouch(event);
		} else if ( event.getAction() == MotionEvent.ACTION_UP ||
				event.getAction() == MotionEvent.ACTION_DOWN ) {
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
