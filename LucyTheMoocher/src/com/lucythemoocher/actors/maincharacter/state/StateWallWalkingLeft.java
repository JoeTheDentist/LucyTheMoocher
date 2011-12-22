package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateWallWalkingLeft extends State {

	private float beginPos_;
	private float beginPause_; 
	private boolean distDone_ = false;
	
	public StateWallWalkingLeft(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {16,17,18,19};
		anim_.setAnimation(tab, ANIMATION_SPEED);
		beginPos_ = Game.getCharacter().getY();
		beginPause_ = Game.getTick();
	}

	public void update() {
		super.update();
		if ( Math.abs(Game.getCharacter().getY()-beginPos_) < WALL_WALKING_DISTANCE && !distDone_ ) {
			pos_.moveUp();
		} else {
			distDone_ = true;
			pos_.stay();
		}
		if ( !distDone_ ) {
			beginPause_ = Game.getTime();
		}
		
		if ( !Game.getMap().hasLeftCollision(pos_.boundingBoxes()) ||
				(Game.getTime()-beginPause_ > WALL_WALKING_PAUSE) ) {
			pc_.changeState(new StateFallingLeft(pc_, pos_, anim_));
		}
	}
	
	public void moveUp() {
		if ( Game.getMap().hasLeftCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
		}
	}

	public void moveDown() {
		pos_.moveDown();
	}
	
	public void moveLeft() {}
	
	public void moveRight() {
		pos_.moveRight();
		pc_.changeState(new StateJumpingRight(pc_, pos_, anim_));
	}

	public void moveStop() {}
	
	public void moveFastLeft() {}

	public void moveFastRight() {
		moveRight();
	}
	
}
