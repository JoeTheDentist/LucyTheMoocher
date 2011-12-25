package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateWallSlidingLeft extends State {

	private float begin_;
	
	public StateWallSlidingLeft(PlayerCharacter pc, Cinematic pos,
			Animation anim) {
		super(pc, pos, anim);
		int tab[] = {16,17,18,19};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		begin_ = Game.getTime();
	}
	
	public void update() {
		super.update();
		if ( Game.getTime() - begin_ > WALL_WALKING_PAUSE ) {
			pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
		}
		
		if ( pos_.hasLeftCollision() ) {
			pos_.stay();
		} else {
			pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
		}
	}
	
	public void moveUp() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
		}
	}
	
	public void moveRight() {
		pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
	}
}
