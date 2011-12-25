package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateFallingLeft extends State {
	public StateFallingLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {32,33,34,35};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}
	
	@Override
	public void update() {
		super.update();
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveLeft() {
		pos_.moveLeft();
	}
	
	@Override
	public void moveRight() {
		pc_.changeState(new StateFallingRight(pc_, pos_, anim_));
	}
	
	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateWallSlidingLeft(pc_, pos_, anim_));
		}
	}
}
