package com.lucythemoocher.graphics;

import android.graphics.Color;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.controls.ActionController;
import com.lucythemoocher.controls.Controllable;
import com.lucythemoocher.util.Direction;

public class HUD implements Controllable, Drawable {
	
	private static final int DEFAULT_COLOR = Color.GRAY;
	private static final int ACTIVE_COLOR = Color.BLACK;
	
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
		clearColors();
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
		colors_[Direction.RIGHT] = ACTIVE_COLOR;
	}

	@Override
	public void moveLeft() {
		colors_[Direction.LEFT] = ACTIVE_COLOR;
	}

	@Override
	public void moveFastRight() {
		moveRight();
	}

	@Override
	public void moveUp() {
		colors_[Direction.UP] = ACTIVE_COLOR;
	}

	@Override
	public void moveDown() {
		colors_[Direction.DOWN] = ACTIVE_COLOR;
	}

	@Override
	public void draw() {
		Camera cam = Globals.getInstance().getCamera();
		cam.drawRectOnHud(Direction.LEFT, colors_[Direction.LEFT]);
		cam.drawRectOnHud(Direction.UP, colors_[Direction.UP]);
		cam.drawRectOnHud(Direction.RIGHT, colors_[Direction.RIGHT]);
		cam.drawRectOnHud(Direction.DOWN, colors_[Direction.DOWN]);
	}

}
