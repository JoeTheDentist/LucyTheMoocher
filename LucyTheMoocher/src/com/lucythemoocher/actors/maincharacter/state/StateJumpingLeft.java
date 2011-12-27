package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateJumpingLeft extends State {
	
	public StateJumpingLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {24,25,26,27};
		if ( pos_.hasDownCollision() ||
				pos_.hasRightCollision() ) {
			pos_.moveFastUp();
		}
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}

	@Override
	public void update() {
		super.update();
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
		} else if ( pos_.speedy() > 0 ) {
			pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveLeft() {
		pos_.moveLeft();
	}
	
	@Override
	public void moveRight() {
		pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
	}

	@Override
	public void moveStop() {
		pos_.moveStop();
	}
	
	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateWallSlidingLeft(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveDown() {
		pc_.changeState(new StateAttackLeft(pc_, pos_, anim_));
	}
}
