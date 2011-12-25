package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateNoneLeft extends State {

	public StateNoneLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {2,3};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		pos_.moveStop();
	}

	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void moveUp() {
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveLeft() {
		pc_.changeState(new StateRunningLeft(pc_, pos_, anim_));
	}
	
	@Override
	public void moveRight() {
		pc_.changeState(new StateRunningRight(pc_, pos_, anim_));
	}

	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateWallWalkingLeft(pc_, pos_, anim_));
		} else {
			moveLeft();
		}
	}
}
