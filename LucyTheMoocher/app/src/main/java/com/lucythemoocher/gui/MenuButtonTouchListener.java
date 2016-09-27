package com.lucythemoocher.gui;

import android.view.MotionEvent;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.controls.TouchListener;

public class MenuButtonTouchListener extends TouchListener {
	private MenuButton button_;
	
	public MenuButtonTouchListener(MenuButton button) {
		super();
		button_ = button;
	}
	
	@Override
	public void motion(MotionEvent e) {
		boolean focus = false;
		if ( e.getActionMasked() == MotionEvent.ACTION_DOWN ) {
			for (int i=0; i<e.getPointerCount(); i++  ) {
				float x = e.getX(i) / Globals.getInstance().getCamera().getScale();
				float y = e.getY(i) / Globals.getInstance().getCamera().getScale();
				if ((button_.x() < x) && (button_.x() + button_.w() > x)) {
					
					if ((button_.y() < y) && (button_.y() + button_.h() > y)) {
						button_.setClicked(true);
					}
				}
			}
			
		} else if ( e.getActionMasked() == MotionEvent.ACTION_MOVE ) {
			for (int i=0; i<e.getPointerCount(); i++  ) {
				float x = e.getX(i) / Globals.getInstance().getCamera().getScale();
				float y = e.getY(i) / Globals.getInstance().getCamera().getScale();
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
