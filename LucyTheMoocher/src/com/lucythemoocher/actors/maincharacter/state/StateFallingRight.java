package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateFallingRight extends State {

	public StateFallingRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {28,29,30,31};
		anim_.setAnimation(tab, ANIMATION_SPEED);
	}
	
	public void update() {
		super.update();
		if ( Game.getMap().hasDownCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateNoneRight(pc_, pos_, anim_));
		}
	}

	public void moveUp() {}

	public void moveDown() {}
	
	public void moveLeft() {
		pos_.moveLeft();
		pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
	}
	public void moveRight() {
		pos_.moveRight();
	}

	public void moveStop() {}

	public void moveFastLeft() {
		moveLeft();
	}

	public void moveFastRight() {
		moveRight();
	}
}
