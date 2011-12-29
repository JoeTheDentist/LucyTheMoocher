package com.lucythemoocher.controls;

import java.util.Iterator;
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

	synchronized public void motion(MotionEvent event) {
		//dumpEvent(event);
		if ( event.getActionMasked() == MotionEvent.ACTION_DOWN || 
				event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN ||
				event.getActionMasked() == MotionEvent.ACTION_MOVE ) {
			down(event);
		} else if ( event.getActionMasked() == MotionEvent.ACTION_UP || 
				event.getActionMasked() == MotionEvent.ACTION_POINTER_UP ) {
			up(event);
		}
	}

	synchronized public void update() {
		refreshPushed();

		//Check whether no button is pushed
		boolean allDown = true;
		for ( int i=0; i<4; i++) {
			for ( int j=0; j<3; j++) {
				allDown &= !pushed_[i][j];
			}
		}
		if ( allDown ) {
			player_.moveStop();
			return;
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
			if ( Game.getTime()-lastTouch_[LEFT] < DOUBLE_TOUCH_SENSIBILITY) {
				player_.moveFastLeft();
			} else {
				if ( moveRight && lastTouch_[LEFT] < lastTouch_[RIGHT] ) {
					player_.moveRight();
				} else {
					player_.moveLeft();
				}
			}
		} else if ( moveRight ) {
			if ( Game.getTime()-lastTouch_[RIGHT] < DOUBLE_TOUCH_SENSIBILITY) {
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

	private void down(MotionEvent event) {		
		pos_.clear();
		for (int i=0; i<event.getPointerCount(); i++  ) {
			if ( i>=3 ) {
				break;
			}
			pos_.add(new Point(event.getX(i), event.getY(i), event.getPointerId(i)));
		}
	}

	private void up(MotionEvent event) {
		if ( pos_.size() == 1 ) {
			lastTouch_[getPos(pos_.get(0))] = Game.getTime();
			pos_.clear();
		} else {
			Iterator<Point> it = pos_.iterator();
			while ( it.hasNext() ) {
				Point p = it.next();
				if ( p.pid ==  event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT) {
					lastTouch_[getPos(p)] = Game.getTime();
					it.remove();
				}
			}
		}
	}

	private void refreshPushed() {
		Camera cam = Globals.getInstance().getCamera();

		for ( int i=0; i<4; i++) {
			for ( int j=0; j<3; j++) {
				pushed_[i][j] = false;
			}
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

	private int getPos(Point p) {
		Camera cam = Globals.getInstance().getCamera();
		int ret = 4;
		if ( p.y < cam.h()/5) {
			ret = UP;
		}
		if ( p.x > 4*cam.w()/5 &&
				p.y > cam.h()/5 &&
				p.y < 4*cam.h()/5) {
			ret = RIGHT;
		}
		if (p.x < cam.w()/5 &&
				p.y > cam.h()/5 &&
				p.y < 4*cam.h()/5) {
			ret = LEFT;
		}

		if ( p.y > 4*cam.h()/5) {
			ret = DOWN;
		}
		return ret;
	}

	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
				"POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_" ).append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid " ).append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")" );
		}
		sb.append("[" );
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#" ).append(i);
			sb.append("(pid " ).append(event.getPointerId(i));
			sb.append(")=" ).append((int) event.getX(i));
			sb.append("," ).append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";" );
		}
		sb.append("]" );
		Log.d("Ctr", "###"+sb.toString());
	}
}


class Point {
	public float x;
	public float y;
	public int pid;

	public Point() {
		x = -1;
		y = -1;
	}

	public Point(float x, float y, int pid) {
		this.x = x;
		this.y = y;
		this.pid = pid;
	}

	public void init() {
		x = -1;
		y = -1;
	}

	public void move(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Point p) {
		return x == p.x && y == p.y;
	}
}
