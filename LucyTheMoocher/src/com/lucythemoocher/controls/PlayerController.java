package com.lucythemoocher.controls;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.actors.PlayerCharacter;

import android.view.MotionEvent;

/**
 * Control a PlayerCharacter
 * Don't forget to call setPlayer() after the constructor
 * before updating
 *
 */
public class PlayerController extends ButtonListener {
	private static final int LEFT = -1;
	private static final int RIGHT = 1;
	private static final int DOWN = -1;
	private static final int UP = 1;
	private static final float DOUBLE_TOUCH_SENSIBILITY = 50.0f; // ms
	
	private int hor_;
	private int ver_;
	private float lastTouch_;
	private int lastHor_;
	private PlayerCharacter player_;

	public PlayerController() {
		hor_ = 0;
		ver_ = 0;
		lastTouch_ = 0.0f;
		lastHor_ = 0;
		player_ = null;
	}
	
	public void setPlayer(PlayerCharacter player) {
		player_ = player;
	}

	public void button(MotionEvent event) {
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
				hor_ = LEFT;
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
			player_.moveStop();
			lastHor_ = hor_;
			lastTouch_ = Game.getTime();
			hor_ = 0;
			ver_ = 0;
		}
	}

	public void update() {

		if ( lastHor_ == hor_ && (Game.getTime()-lastTouch_ < DOUBLE_TOUCH_SENSIBILITY) ) {
			if ( hor_ == 1 ) {
				player_.moveFastRight();
			} else if ( hor_ == -1 ) {
				player_.moveFastLeft();
			}
		}
		
		if ( hor_ == 1 ) {
			player_.moveRight();
		} else if ( hor_ == -1 ) {
			player_.moveLeft();
		}
		if ( ver_ == 1 ) {
			player_.moveUp();
		} else if ( ver_ == -1 ) {
			player_.attack();
		}
		ver_ = 0;
	}
}
