package com.lucythemoocher.graphics;

import android.graphics.Color;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.controls.ActionController;
import com.lucythemoocher.controls.Controllable;

public class HUD implements Controllable, Drawable {

	// copied from ActionControler.
	// TODO, move to generic class
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int UP = 3;
	
	private static final int DEFAULT_COLOR = Color.GRAY;
	private static final int ACTIVE_COLOR = Color.RED;
	
	private int colors_[];
	private ActionController ctrl_;
	
	public HUD(ActionController ctrl) {
		ctrl.setControllable(this);
		ctrl_ = ctrl;
		colors_ = new int[4];
		clearColors();
	}
	
	private void clearColors() {
		for (int i=0; i<4; i++) {
			colors_[i] = DEFAULT_COLOR;
		}
	}
	
	public void update() {
		ctrl_.update();
	}
	
	@Override
	public void moveStop() {
		clearColors();
	}

	@Override
	public void moveFastLeft() {
		moveLeft();
	}

	@Override
	public void moveRight() {
		colors_[RIGHT] = ACTIVE_COLOR;
	}

	@Override
	public void moveLeft() {
		colors_[LEFT] = ACTIVE_COLOR;
	}

	@Override
	public void moveFastRight() {
		moveRight();
	}

	@Override
	public void moveUp() {
		colors_[UP] = ACTIVE_COLOR;
	}

	@Override
	public void moveDown() {
		colors_[DOWN] = ACTIVE_COLOR;
	}

	@Override
	public void draw() {
		Camera cam = Globals.getInstance().getCamera();
		cam.drawRectOnHud(0, 0, cam.physicalH(), cam.physicalW() / 7, colors_[LEFT]);
		cam.drawRectOnHud(0, 0, cam.physicalH() / 5, cam.physicalW(), colors_[UP]);
		cam.drawRectOnHud(6 * cam.physicalW() / 7, 0, cam.physicalH(), cam.physicalW() / 7, colors_[RIGHT]);
		cam.drawRectOnHud(0, 4 *cam.physicalH() / 5, cam.physicalH() / 5, cam.physicalW(), colors_[DOWN]);
		clearColors();
	}

}
