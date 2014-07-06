package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.util.Direction;

public class StateAttack extends State {

	public StateAttack(PlayerCharacter pc, Cinematic pos, Animation anim, int dir) {
		super(pc, pos, anim, dir);
		if ( dir == Direction.LEFT ) {
			int tab[] = {32,33,34,35};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		} else {
			int tab[] = {28,29,30,31};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		}
		pc_.getDrawer().enablePersistence(150);
	}
	
	public void update() {
		super.update();
		pos_.moveDown();
		if ( pos_.hasDownCollision() ) {
			onLeave();
			pc_.changeState(new StateNone(pc_, pos_, anim_, dir_));
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
