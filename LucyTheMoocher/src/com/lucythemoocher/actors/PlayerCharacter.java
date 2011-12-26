package com.lucythemoocher.actors;

import com.lucythemoocher.actors.maincharacter.state.*;

import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.controls.PlayerController;
import com.lucythemoocher.R;

public class PlayerCharacter extends Actor {
	private State state_;
	private PlayerController controller_;
	
	/**
	 * Constructor. The controller will be attached with 
	 * the character in the constructor, you don't have
	 * to do it outside
	 * @param controller Player's controller
	 */
	public PlayerCharacter(PlayerController controller) {
		anim_ = new Animation(R.drawable.lucy_states, 80, 76);
		pos_ = new Cinematic(50,50,	anim_.getH(),anim_.getW());
		state_ = new StateNoneLeft(this, pos_, anim_);
		controller_ = controller;
		controller_.setPlayer(this);
	}
	
	/**
	 * Draw the character on the screen
	 */
	public void draw() {
		anim_.draw( pos_.x(), pos_.y());
	}
	
	/**
	 * Update the character
	 */
	public void update() {
		controller_.update();
		state_.update();
	}
	
	/**
	 * State pattern
	 * @param newState
	 */
	public void changeState(State newState) {
		state_ = newState;
	}
	
	/**
	 * Called when the character stops moving
	 */
	public void moveStop() {
		state_.moveStop();
	}

	/**
	 * Try to go to the left
	 */
	public void moveLeft() {
		state_.moveLeft();
	}

	/**
	 * Try to go to the right
	 */
	public void moveRight() {
		state_.moveRight();
	}

	/**
	 * Try to go up
	 */
	public void moveUp() {
		state_.moveUp();
	}

	/**
	 * Try to go down
	 */
	public void moveDown() {
		state_.moveDown();
	}

	/**
	 * Try to move fast to the right
	 */
	public void moveFastRight() {
		state_.moveFastRight();
	}
	
	/**
	 * Try to move fast to the left
	 */
	public void moveFastLeft() {
		state_.moveFastLeft();
	}
}
