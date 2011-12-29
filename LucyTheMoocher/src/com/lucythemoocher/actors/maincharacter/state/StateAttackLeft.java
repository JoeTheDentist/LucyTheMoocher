package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateAttackLeft extends State {

	public StateAttackLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {32,33,34,35};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		pc_.getDrawer().enablePersistence(150);
	}
	
	public void update() {
		super.update();
		pos_.moveDown();
		if ( pos_.hasDownCollision() ) {
			onLeave();
			pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
		}
	}
	
	/**
	 * Called when the state ends
	 */
	public void onLeave() {
		pc_.getDrawer().disablePersistence();
	}
	
	public boolean isAttacking() {
		return true;
	}

}
