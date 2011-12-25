package com.lucythemoocher.controls;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Camera;

import android.util.Log;
import android.view.MotionEvent;

public class PlayerController {
	private static final int LEFT = -1;
	private static final int RIGHT = 1;
	private static final int DOWN = -1;
	private static final int UP = 1;
	private static final int DOUBLE_TOUCH_SENSIBILITY = 10;
	
	private static int hor_ = 0;
	private static int ver_ = 0;
	private static int lastTouch_ = 0;
	private static int lastHor_ = 0;


	public static void process(MotionEvent event) {
		Camera cam = Globals.getInstance().getCamera();
		//parcours de tous les points appuyés
		for (int i=0; i<event.getPointerCount(); i++  ) {
			// X X X
			// # # #
			// # # #
			// Saut
			if (event.getY(i) < cam.h()/5) {
				ver_ = UP;
			}

			// # # #
			// # # X
			// # # #
			// Deplacement droit
			if (event.getX(i) > 4*cam.w()/5 &&
					event.getY(i) > cam.h()/5 &&
					event.getY(i) < 4*cam.h()/5) {
				hor_ = RIGHT;
			}

			// # # #
			// X # #
			// # # #
			// Deplacement gauche
			if (event.getX(i) < cam.w()/5 &&
					event.getY(i) > cam.h()/5 &&
					event.getY(i) < 4*cam.h()/5) {
				hor_ = -1;
			}
			
			// # # #
			// # # #
			// # X #
			// Attaque
			if (event.getY(i) > 4*cam.h()/5 &&
					event.getX(i) > cam.w()/5 &&
					event.getX(i) < 4*cam.w()/5) {
				ver_ = DOWN;
			}
			
			
		}

		if ( event.getAction() == MotionEvent.ACTION_UP ) {
			Game.moveStop();
			lastHor_ = hor_;
			lastTouch_ = Game.getTick();
			hor_ = 0;
			ver_ = 0;
		}
	}

	public static void update() {

		if ( lastHor_ == hor_ && (Game.getTick()-lastTouch_ < DOUBLE_TOUCH_SENSIBILITY) ) {
			if ( hor_ == 1 ) {
				Game.moveFastRight();
			} else if ( hor_ == -1 ) {
				Game.moveFastLeft();
			}
		}
		
		if ( hor_ == 1 ) {
			Game.moveRight();
		} else if ( hor_ == -1 ) {
			Game.moveLeft();
		}
		if ( ver_ == 1 ) {
			Game.moveUp();
		} else if ( ver_ == -1 ) {
			Game.attack();
		}
		ver_ = 0;
	}
}
