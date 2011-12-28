package com.lucythemoocher.controls;

import java.util.LinkedList;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.actors.PlayerCharacter;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Control a PlayerCharacter
 * Don't forget to call setPlayer() after the constructor
 * before updating
 *
 */
public class PlayerController extends TouchListener {
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int UP = 3;
	private static final float DOUBLE_TOUCH_SENSIBILITY = 200;

	private float[] lastTouch_;
	private boolean[][] pushed_;
	private LinkedList<Point> pos_;
	private PlayerCharacter player_;

	public PlayerController() {
		lastTouch_ = new float[5];
		player_ = null;
		pushed_ = new boolean[4][3]; //on suppose qu'on a que trois points au max
		pos_ = new LinkedList<Point>();
	}

	public void setPlayer(PlayerCharacter player) {
		player_ = player;
	}

	public void motion(MotionEvent event) {
		updatePushed(event);
	}

	public void update() {
		
		//Check whether no button is pushed
		boolean allDown = true;
		for ( int i=0; i<4; i++) {
			for ( int j=0; j<3; j++) {
				allDown &= !pushed_[i][j];
			}
		}
		if ( allDown ) {
			player_.moveStop();
		}

		boolean moveLeft = false;
		for ( int i=0; i<3; i++) {
			moveLeft |= pushed_[LEFT][i];
		}

		boolean moveRight = false;
		for ( int i=0; i<3; i++) {
			moveRight |= pushed_[RIGHT][i];
		}
		
		
		if ( moveLeft ) {
			if ( Game.getTime()-lastTouch_[LEFT] < DOUBLE_TOUCH_SENSIBILITY ) {
				player_.moveFastLeft();
			} else {
				if ( moveRight && lastTouch_[LEFT] < lastTouch_[RIGHT] ) {
					player_.moveRight();
				} else {
					player_.moveLeft();
				}
			}
		}
		
		if ( moveRight && !moveLeft ) {
			if ( Game.getTime()-lastTouch_[RIGHT] < DOUBLE_TOUCH_SENSIBILITY ) {
				player_.moveFastRight();
			} else {
				player_.moveRight();
			}
		}

		boolean moveUp = false;
		for ( int i=0; i<3; i++) {
			moveUp |= pushed_[UP][i];
		}
		if ( moveUp ) {
			player_.moveUp();
		}

		boolean moveDown = false;
		for ( int i=0; i<3; i++) {
			moveDown |= pushed_[DOWN][i];
		}
		if ( moveDown ) {
			player_.moveDown();
		}
	}
	
	private void updatePushed(MotionEvent event) {
		Camera cam = Globals.getInstance().getCamera();
		for ( int i=0; i<4; i++) {
			for ( int j=0; j<3; j++) {
				pushed_[i][j] = false;
			}
		}
		
		pos_.clear();
		for (int i=0; i<event.getPointerCount(); i++  ) {
			if ( i>=3 ) {
				break;
			}
			if ( event.getActionMasked() == MotionEvent.ACTION_POINTER_UP ||
					event.getActionMasked() == MotionEvent.ACTION_UP &&
					i == event.getActionIndex()) {
				//Nothing
			} else {
				pos_.add(new Point(event.getX(i), event.getY(i)));
			}
		}
		if ( event.getActionMasked() == MotionEvent.ACTION_POINTER_UP ||
				event.getActionMasked() == MotionEvent.ACTION_UP ) {
			pos_.remove(new Point(event.getX(), event.getY()));
		}
		
		int i = 0;
		for ( Point p : pos_ ) {
			if ( p.y < cam.h()/5) {
				pushed_[UP][i] = true;
			}
			if ( p.x > 4*cam.w()/5 &&
					p.y > cam.h()/5 &&
					p.y < 4*cam.h()/5) {
				pushed_[RIGHT][i] = true;
			}
			if (p.x < cam.w()/5 &&
					p.y > cam.h()/5 &&
					p.y < 4*cam.h()/5) {
				pushed_[LEFT][i] = true;
			}
			
			if ( p.y > 4*cam.h()/5) {
				pushed_[DOWN][i] = true;
			}
			i++;
		}
	}
}


class Point {
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point p) {
		return x == p.x && y == p.y;
	}
}
