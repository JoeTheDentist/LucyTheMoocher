package com.lucythemoocher.controls;

import android.view.MotionEvent;

public abstract class TouchListener {
	
	public TouchListener() {
		GlobalController.getInstance().registerTouch(this);
	}
	
	public abstract void motion(MotionEvent e);
}
