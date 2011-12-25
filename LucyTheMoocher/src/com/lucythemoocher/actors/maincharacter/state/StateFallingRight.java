package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateFallingRight extends State {

	public StateFallingRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {28,29,30,31};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}
	
	@Override
	public void update() {
		super.update();
		pos_.moveRight();
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateNoneRight(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveLeft() {
		pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
	}
	
	@Override
	public void moveRight() {
		pos_.moveRight();
	}
	
	@Override
	public void moveFastRight() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateWallSlidingRight(pc_, pos_, anim_));
		}
	}
}
