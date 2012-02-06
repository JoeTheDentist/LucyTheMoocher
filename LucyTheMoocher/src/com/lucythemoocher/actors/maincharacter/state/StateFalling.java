package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateFalling extends State {
	public StateFalling(PlayerCharacter pc, Cinematic pos, Animation anim, Direction dir) {
		super(pc, pos, anim, dir);
		if ( dir_ == Direction.LEFT ) {
			int tab[] = {32,33,34,35};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		} else {
			int tab[] = {28,29,30,31};
			anim_.setAnimation(tab, ANIMATION_SPEED);
		}
	}
	
	@Override
	public void update() {
		super.update();
		if ( pos_.hasDownCollision() ) {
			pc_.changeState(new StateNone(pc_, pos_, anim_, dir_));
		}
	}
	
	@Override
	public void moveLeft() {
		if ( dir_ == Direction.RIGHT ) {
			pc_.changeState(new StateFalling(pc_, pos_, anim_, Direction.LEFT));
		}
		pos_.moveLeft();
	}
	
	@Override
	public void moveRight() {
		if ( dir_ == Direction.LEFT ) {
			pc_.changeState(new StateFalling(pc_, pos_, anim_, Direction.RIGHT));
		}
		pos_.moveRight();
	}
	
	@Override
	public void moveFastLeft() {
		if ( pos_.hasLeftCollision() ) {
			pc_.changeState(new StateWallSliding(pc_, pos_, anim_, dir_));
		}
	}
	
	@Override
	public void moveFastRight() {
		if ( pos_.hasRightCollision() ) {
			pc_.changeState(new StateWallSliding(pc_, pos_, anim_, dir_));
		}
	}
	
	@Override
	public void moveDown() {
		pc_.changeState(new StateAttack(pc_, pos_, anim_, dir_));
	}
}
