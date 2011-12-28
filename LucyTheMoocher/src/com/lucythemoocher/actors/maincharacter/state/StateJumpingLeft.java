package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateJumpingLeft extends State {
	private boolean neverBeenUpdated_;
	public StateJumpingLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {24,25,26,27};
		neverBeenUpdated_ = true;
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}

	@Override
	public void update() {
		super.update();
		if (neverBeenUpdated_) { // first update, let's do the jump
			neverBeenUpdated_ = false;
			if ( pos_.hasDownCollision() ||
					pos_.hasRightCollision() ) {
				pos_.moveFastUp();
			}
		} else { // jump has been done, should we leave the state?
			if ( pos_.hasDownCollision() ) {
				pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
			} else if ( pos_.speedy() > 0 ) {
				pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
			}
		}
	}
	
	@Override
	public void moveRight() {
		Log.d("plop", "12moveright");
		pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
	}

	@Override
	public void moveStop() {
		pos_.moveStop();
	}
	
	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			Log.d("plop", "12fastleft");
			pc_.changeState(new StateWallSlidingLeft(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveDown() {
		Log.d("plop", "12attack");
		pc_.changeState(new StateAttackLeft(pc_, pos_, anim_));
	}
}
