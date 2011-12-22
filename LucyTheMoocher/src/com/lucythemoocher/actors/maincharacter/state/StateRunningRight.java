package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateRunningRight extends State {

	public StateRunningRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {4,5,6,7};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}

	public void update() {
		super.update();
	}
	
	public void moveUp() {
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
		}
	}

	public void moveDown() {
		pos_.moveDown();
	}
	
	public void moveLeft() {
		pos_.moveLeft();
		pc_.changeState(new StateRunningLeft(pc_, pos_, anim_));
	}
	
	public void moveRight() {
		pos_.moveRight();
	}

	public void moveStop() {
		pos_.moveStop();
		pc_.changeState(new StateNoneRight(pc_, pos_, anim_));
	}

	public void moveFastLeft() {
		moveLeft();
	}

	public void moveFastRight() {
		moveRight();
	}
	
}
