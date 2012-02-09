package com.lucythemoocher.gui;

import android.view.MotionEvent;

import com.lucythemoocher.controls.TouchListener;

public class MenuButtonTouchListener extends TouchListener {
	private MenuButton button_;
	private int index_;
	
	public MenuButtonTouchListener(MenuButton button, int index) {
		super();
		button_ = button;
		index_ = index;
	}
	
	@Override
	public void motion(MotionEvent e) {
		boolean focus = false;
		if ( e.getActionMasked() == MotionEvent.ACTION_DOWN ) {
			for (int i=0; i<e.getPointerCount(); i++  ) {
				float x = e.getX(i);
				float y = e.getY(i);
				if ((button_.x() < x) && (button_.x() + button_.w() > x)) {
					
					if ((button_.y() < y) && (button_.y() + button_.h() > y)) {
						button_.setClicked(true);
					}
				}
			}
			
		} else if ( e.getActionMasked() == MotionEvent.ACTION_MOVE ) {
			for (int i=0; i<e.getPointerCount(); i++  ) {
				float x = e.getX(i);
				float y = e.getY(i);
				if ((button_.x() < x) && (button_.x() + button_.w() > x)) {
					
					if ((button_.y() < y) && (button_.y() + button_.h() > y)) {
						focus = true;
					}
				}
			}
			button_.setFocussed(focus);
			
		}
	}
	

}
