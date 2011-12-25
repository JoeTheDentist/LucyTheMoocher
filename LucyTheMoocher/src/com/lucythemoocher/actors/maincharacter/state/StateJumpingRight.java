package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateJumpingRight extends State {
	
	public StateJumpingRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {20,21,22,23};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		if ( pos_.hasDownCollision() ||
				pos_.hasLeftCollision() ) {
			pos_.moveFastUp();
		}
	}

	@Override
	public void update() {
		super.update();
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateNoneRight(pc_, pos_, anim_));
		} else if ( pos_.speedy() > 0 ) {
			pc_.changeState(new StateFallingRight(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveLeft() {
		pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
	}
	
	@Override
	public void moveRight() {
		pos_.moveRight();
	}

	@Override
	public void moveStop() {
		pos_.moveStop();
	}
	
	@Override
	public void moveFastRight() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateWallSlidingRight(pc_, pos_, anim_));
		}
	}
}
