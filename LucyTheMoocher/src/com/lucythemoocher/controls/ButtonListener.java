package com.lucythemoocher.controls;

import android.view.MotionEvent;

public abstract class ButtonListener {
	
	public ButtonListener() {
		GlobalController.getInstance().registerButton(this);
	}
	
	/**
	 * When a button is pushed
	 * @param event
	 */
	public abstract void button(MotionEvent event);
}
