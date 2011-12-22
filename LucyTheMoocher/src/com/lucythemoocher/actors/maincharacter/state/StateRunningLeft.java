package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
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
	}
	
	public void moveUp() {
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}

	public void moveDown() {
		pos_.moveDown();
	}
	
	public void moveLeft() {
		pos_.moveLeft();
	}
	
	public void moveRight() {
		pos_.moveRight();
		pc_.changeState(new StateRunningRight(pc_, pos_, anim_));
	}

	public void moveStop() {
		pos_.moveStop();
		pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
	}

	public void moveFastLeft() {
		moveLeft();
	}

	public void moveFastRight() {
		moveRight();
	}
	
}
