package com.lucythemoocher.controls;

import java.util.Iterator;
import java.util.LinkedList;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.actors.PlayerCharacter;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Control a game actions
 */
public class ActionController extends TouchListener {
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int UP = 3;
	private static final float DOUBLE_TOUCH_SENSIBILITY = 200;

	private float[] lastTouch_;
	private boolean[] pushed_;
	private LinkedList<Point> pos_;
	private Controllable ctrlable_;

	/**
	 * Constructor
	 */
	public ActionController() {
		lastTouch_ = new float[5];
		ctrlable_ = null;
		pushed_ = new boolean[4];
		pos_ = new LinkedList<Point>();
	}

	/**
	 * Setter
	 * @param ctrlable, targeted controlled
	 */
	public void setControllable(Controllable ctrlable) {
		ctrlable_ = ctrlable;
	}

	/**
	 * Fill a list of point according to the touch events
	 * @param event
	 */
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

	/**
	 * Call the actions of the controlled according to the action array
	 */
	synchronized public void update() {
		refreshPushed();

		//Check whether no button is pushed
		boolean allDown = true;
		for ( int i=0; i<4; i++) {
			for ( int j=0; j<3; j++) {
				allDown &= !pushed_[i];
			}
		}
		if ( allDown ) {
			ctrlable_.moveStop();
			return;
		}

		boolean moveLeft = pushed_[LEFT];
		boolean moveRight = pushed_[RIGHT];

		if ( moveLeft ) {
			if ( Globals.getInstance().getGame().getTime()-lastTouch_[LEFT] < DOUBLE_TOUCH_SENSIBILITY) {
				ctrlable_.moveFastLeft();
			} else {
				if ( moveRight && lastTouch_[LEFT] < lastTouch_[RIGHT] ) {
					ctrlable_.moveRight();
				} else {
					ctrlable_.moveLeft();
				}
			}
		} else if ( moveRight ) {
			if ( Globals.getInstance().getGame().getTime()-lastTouch_[RIGHT] < DOUBLE_TOUCH_SENSIBILITY) {
				ctrlable_.moveFastRight();
			} else {
				ctrlable_.moveRight();
			}
		}
		if ( !moveLeft && !moveRight ) {
			ctrlable_.moveStop();
		}

		boolean moveUp = pushed_[UP];
		if ( moveUp ) {
			ctrlable_.moveUp();
		}

		boolean moveDown = pushed_[DOWN];
		if ( moveDown ) {
			ctrlable_.moveDown();
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
			lastTouch_[getPos(pos_.get(0))] = Globals.getInstance().getGame().getTime();
			pos_.clear();
		} else {
			Iterator<Point> it = pos_.iterator();
			while ( it.hasNext() ) {
				Point p = it.next();
				if ( p.pid ==  event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT) {
					lastTouch_[getPos(p)] = Globals.getInstance().getGame().getTime();
					it.remove();
				}
			}
		}
	}

	/**
	 * Update action event
	 */
	private void refreshPushed() {
		Camera cam = Globals.getInstance().getCamera();

		for ( int i=0; i<4; i++) {
			pushed_[i] = false;
		}
		
		for ( Point p : pos_ ) {
			if ( p.y < cam.physicalH()/5) {
				pushed_[UP] = true;
			}
			if ( p.x > 4*cam.physicalW()/5) {
				pushed_[RIGHT] = true;
			}
			if (p.x < cam.physicalW()/5) {
				pushed_[LEFT] = true;
			}
			if ( p.y > 4*cam.physicalH()/5) {
				pushed_[DOWN] = true;
			}
		}
	}

	private int getPos(Point p) {
		Camera cam = Globals.getInstance().getCamera();
		if ( p.y < cam.physicalH()/5) {
			return UP;
		}
		if ( p.x > 4*cam.physicalW()/5 &&
				p.y > cam.physicalH()/5 &&
				p.y < 4*cam.physicalH()/5) {
			return RIGHT;
		}
		if (p.x < cam.physicalW()/5 &&
				p.y > cam.physicalH()/5 &&
				p.y < 4*cam.physicalH()/5) {
			return LEFT;
		}

		if ( p.y > 4*cam.physicalH()/5) {
			return DOWN;
		}
		return 4;
	}

	/**
	 * To print event in details
	 * @param event
	 */
	@SuppressWarnings("unused")
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
