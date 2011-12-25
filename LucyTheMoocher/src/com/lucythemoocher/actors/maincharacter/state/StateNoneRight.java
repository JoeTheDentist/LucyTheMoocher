package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateNoneRight extends State {

	public StateNoneRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {0,1};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		pos_.moveStop();
	}

	public void update() {
		super.update();
	}
	
	public void moveUp() {
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
		}
	}
	
	public void moveLeft() {
		pc_.changeState(new StateRunningLeft(pc_, pos_, anim_));
	}
	
	public void moveRight() {
		pc_.changeState(new StateRunningRight(pc_, pos_, anim_));
	}

	public void moveFastRight() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateWallWalkingRight(pc_, pos_, anim_));
		} else {
			moveRight();
		}
	}
}
