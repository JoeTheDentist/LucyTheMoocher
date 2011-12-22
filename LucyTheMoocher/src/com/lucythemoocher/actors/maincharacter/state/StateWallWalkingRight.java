package com.lucythemoocher.actors.maincharacter.state;

import android.util.Log;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public class StateWallWalkingRight extends State {

	private float beginPos_;
	private float beginPause_; 
	private boolean distDone_ = false;
	
	public StateWallWalkingRight(PlayerCharacter pc, Cinematic pos, Animation anim) {
		super(pc, pos, anim);
		int tab[] = {12,13,14,15};
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
		
		if ( !Game.getMap().hasRightCollision(pos_.boundingBoxes()) ||
				(Game.getTime()-beginPause_ > WALL_WALKING_PAUSE) ) {
			pc_.changeState(new StateFallingRight(pc_, pos_, anim_));
		}
	}
	
	public void moveUp() {
		if ( Game.getMap().hasRightCollision(pos_.boundingBoxes()) ) {
			pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
		}
	}

	public void moveDown() {
		pos_.moveDown();
	}
	
	public void moveLeft() {
		pos_.moveLeft();
		pc_.changeState(new StateJumpingLeft(pc_, pos_, anim_));
	}
	
	public void moveRight() {}

	public void moveStop() {}
	
	public void moveFastLeft() {
		moveLeft();
	}

	public void moveFastRight() {}
	
}
