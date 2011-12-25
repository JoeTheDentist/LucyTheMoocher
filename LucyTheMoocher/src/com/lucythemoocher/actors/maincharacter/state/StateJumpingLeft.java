package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateJumpingLeft extends State {
	
	public StateJumpingLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {24,25,26,27};
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ||
				Game.getMap().hasRightCollision(pos_.boundingBoxes()) ) {
			pos_.moveFastUp();
		}
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}

	public void update() {
		super.update();
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
		} else if ( pos_.speedy() > 0 ) {
			pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
		}
	}
	
	public void moveLeft() {
		pos_.moveLeft();
	}
	
	public void moveRight() {
		pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
	}

	public void moveStop() {
		pos_.moveStop();
	}
}
