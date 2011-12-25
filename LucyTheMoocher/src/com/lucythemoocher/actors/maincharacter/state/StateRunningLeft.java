package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateRunningLeft extends State {

	public StateRunningLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {8,9,10,11};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}

	public void update() {
		super.update();
		pos_.moveLeft();
	}
	
	public void moveUp() {
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}
	
	public void moveRight() {
		pc_.changeState(new StateRunningRight(pc_, pos_, anim_));
	}

	public void moveStop() {
		pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
	}
}
