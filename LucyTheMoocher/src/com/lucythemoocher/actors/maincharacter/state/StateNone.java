package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateNone extends State {

	public StateNone(PlayerCharacter pc, Cinematic pos, Animation anim, Direction dir) {
		super(pc, pos, anim, dir);
		if ( dir_ == Direction.LEFT ) {
			int tab[] = {2,3};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		} else {
			int tab[] = {0,1};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		}
		pos_.moveStop();
	}

	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void moveUp() {
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateJumping(pc_, pos_, anim_, dir_));
		}
	}
	
	@Override
	public void moveLeft() {
		pc_.changeState(new StateRunning(pc_, pos_, anim_, Direction.LEFT));
	}
	
	@Override
	public void moveRight() {
		pc_.changeState(new StateRunning(pc_, pos_, anim_, Direction.RIGHT));
	}

	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateWallWalking(pc_, pos_, anim_, Direction.LEFT));
		} else {
			moveLeft();
		}
	}
	
	@Override
	public void moveFastRight() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateWallWalking(pc_, pos_, anim_, Direction.RIGHT));
		} else {
			moveRight();
		}
	}
}
