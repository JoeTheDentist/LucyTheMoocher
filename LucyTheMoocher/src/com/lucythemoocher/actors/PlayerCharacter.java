package com.lucythemoocher.actors;

import com.lucythemoocher.actors.maincharacter.state.*;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.R;

public class PlayerCharacter extends Actor {
	private State state_;
	
	public PlayerCharacter() {
		anim_ = new Animation(R.drawable.lucy_states, 80, 76);
		pos_ = new Cinematic(50,50,	anim_.getH(),anim_.getW());
		state_ = new StateNoneLeft(this, pos_, anim_);
	}
	
	public void draw() {
		anim_.draw( pos_.x(), pos_.y());
	}
	
	public void update() {
		state_.update();
	}
	
	public void changeState(State newState) {
		state_ = newState;
	}
	
	public void moveStop() {
		state_.moveStop();
	}

	public void moveLeft() {
		state_.moveLeft();
	}

	public void moveRight() {
		state_.moveRight();
	}

	public void moveUp() {
		state_.moveUp();
	}
	
	public void moveDown() {
		state_.moveDown();
	}

	public void moveFastRight() {
		state_.moveFastRight();
	}
	
	public void moveFastLeft() {
		state_.moveFastLeft();
	}
}
