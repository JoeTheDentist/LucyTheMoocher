package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateWallWalkingRight extends State {

	private float begin_;
	
	public StateWallWalkingRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {12,13,14,15};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		begin_ = Game.getTime();
	}

	@Override
	public void update() {
		super.update();
		if ( Game.getTime()-begin_ < WALL_WALKING_TIME ) {
			pos_.moveUp();
		} else {
			pc_.changeState(new StateWallSlidingRight(pc_, pos_, anim_));
		}
		
		if ( !pos_.hasRightCollision() ) {
			pc_.changeState(new StateFallingRight(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveUp() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}
	
	@Override
	public void moveLeft() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}
}
