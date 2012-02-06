package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateJumping extends State {
	private boolean neverBeenUpdated_;
	public StateJumping(PlayerCharacter pc, Cinematic pos, Animation anim, Direction dir) {
		super(pc, pos, anim, dir);
		if ( dir_ == Direction.LEFT ) {
			int tab[] = {24,25,26,27};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		} else {
			int tab[] = {20,21,22,23};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		}
		neverBeenUpdated_ = true;
	}

	@Override
	public void update() {
		super.update();
		if (neverBeenUpdated_) { // first update, let's do the jump
			neverBeenUpdated_ = false;
			if ( pos_.hasDownCollision() ||
					pos_.hasLeftCollision()||
					pos_.hasRightCollision() ) {
				pos_.moveFastUp();
			}
		} else { // jump has been done, should we leave the state?
			if ( pos_.hasDownCollision() ) {
				pc_.changeState(new StateNone(pc_, pos_, anim_, dir_));
			} else if ( pos_.speedy() > 0 ) {
				pc_.changeState(new StateFalling(pc_, pos_, anim_, dir_));
			}
		}
	}

	@Override
	public void moveLeft() {
		if ( dir_ == Direction.RIGHT ) {
			pc_.changeState(new StateJumping(pc_, pos_, anim_, Direction.LEFT));
		}
		pos_.moveLeft();
	}

	@Override
	public void moveRight() {
		if ( dir_ == Direction.LEFT ) {
			pc_.changeState(new StateJumping(pc_, pos_, anim_, Direction.RIGHT));
		}
		pos_.moveRight();
	}

	@Override
	public void moveStop() {
		pos_.moveStop();
	}

	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateWallSliding(pc_, pos_, anim_, dir_));
		}
	}

	@Override
	public void moveFastRight() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateWallSliding(pc_, pos_, anim_, dir_));
		}
	}

	@Override
	public void moveDown() {
		pc_.changeState(new StateAttack(pc_, pos_, anim_, dir_));
	}
}
