package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateRunning extends State {

	public StateRunning(PlayerCharacter pc, Cinematic pos, Animation anim, Direction dir) {
		super(pc, pos, anim, dir);
		if ( dir_ == Direction.LEFT ) {
			int tab[] = {8,9,10,11};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		} else {
			int tab[] = {4,5,6,7};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		}
	}

	@Override
	public void update() {
		super.update();
		if ( dir_ == Direction.LEFT ) {
			pos_.moveLeft();
		} else {
			pos_.moveRight();
		}
		if ( !pos_.hasDownCollision() ) {
			pc_.changeState(new StateFalling(pc_, pos_, anim_, dir_));
		}
	}

	@Override
	public void moveUp() {
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateJumping(pc_, pos_, anim_, dir_));
		}
	}

	@Override
	public void moveLeft() {
		if ( dir_ == Direction.RIGHT ) {
			pc_.changeState(new StateRunning(pc_, pos_, anim_, Direction.LEFT));
		}
	}

	@Override
	public void moveRight() {
		if ( dir_ == Direction.LEFT ) {
			pc_.changeState(new StateRunning(pc_, pos_, anim_, Direction.RIGHT));
		}
	}

	@Override
	public void moveStop() {
		pc_.changeState(new StateNone(pc_, pos_, anim_, dir_));
	}
}
