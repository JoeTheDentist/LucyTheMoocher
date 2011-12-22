package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

import android.util.Log;

public class StateFallingLeft extends State {
	public StateFallingLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {32,33,34,35};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}
	
	public void update() {
		super.update();
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateNoneLeft(pc_, pos_, anim_));
		}
	}

	public void moveUp() {}

	public void moveDown() {}
	
	public void moveLeft() {
		pos_.moveLeft();
	}
	public void moveRight() {
		pos_.moveRight();
		pc_.changeState(new StateFallingRight(pc_, pos_, anim_));
	}

	public void moveStop() {}

	public void moveFastLeft() {
		moveLeft();
	}

	public void moveFastRight() {
		moveRight();
	}
}
