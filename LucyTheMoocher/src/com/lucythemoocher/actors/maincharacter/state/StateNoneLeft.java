package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateNoneLeft extends State {

	public StateNoneLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {2,3};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		pos_.moveStop();
	}

	public void update() {
		super.update();
	}
	
	public void moveUp() {
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}
	
	public void moveLeft() {
		pc_.changeState(new StateRunningLeft(pc_, pos_, anim_));
	}
	
	public void moveRight() {
		pc_.changeState(new StateRunningRight(pc_, pos_, anim_));
	}

	public void moveFastLeft() {
		if ( Game.getMap().hasLeftCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateWallWalkingLeft(pc_, pos_, anim_));
		} else {
			moveLeft();
		}
	}
}
