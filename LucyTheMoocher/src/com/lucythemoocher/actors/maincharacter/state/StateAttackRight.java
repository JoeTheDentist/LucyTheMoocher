package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateAttackRight extends State {

	public StateAttackRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {28,29,30,31};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}
	
	public void update() {
		super.update();
		pos_.moveDown();
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateNoneRight(pc_, pos_, anim_));
		}
	}
}
