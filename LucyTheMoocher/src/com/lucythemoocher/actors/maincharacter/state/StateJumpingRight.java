package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateJumpingRight extends State {
	
	public StateJumpingRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {20,21,22,23};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ||
				Game.getMap().hasLeftCollision(pos_.boundingBoxes()) ) {
			pos_.moveFastUp();
		}
	}

	public void update() {
		super.update();
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateNoneRight(pc_, pos_, anim_));
		} else if ( pos_.speedy() > 0 ) {
			pc_.changeState(new StateFallingRight(pc_, pos_, anim_));
		}
	}
	
	public void moveUp() {}

	public void moveDown() {
		pos_.moveDown();
	}
	
	public void moveLeft() {
		pos_.moveLeft();
		pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
	}
	
	public void moveRight() {
		pos_.moveRight();
	}

	public void moveStop() {
		pos_.moveStop();
	}

	public void moveFastLeft() {
		moveLeft();
	}

	public void moveFastRight() {
		moveRight();
	}

}
