package com.lucythemoocher.actors;

import java.util.ListIterator;

import android.content.res.Resources;
import android.util.Log;

import com.lucythemoocher.actors.maincharacter.state.*;

import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.controls.PlayerController;
import com.lucythemoocher.game.Game;
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
		super();
		getDrawer().initializeAnimation(R.drawable.lucy_states, 80, 76);
		getCinematic().addBox(50, 50, getH(), getW());
		state_ = new StateNoneLeft(this, pos_, getDrawer().getAnim());
		controller_ = controller;
		controller_.setPlayer(this);
	}

	
	/**
	 * Update the character
	 */
	public void update() {
		super.update();
		controller_.update();
		state_.update();
		checkMonstersCollisions();
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
	
	// temporary
	private void checkMonstersCollisions() {
		ListIterator<Monster> it = Game.getMonstersManager().getIterator();
		while (it.hasNext()) {
			Monster monster = it.next();
			if (collidesWith(monster)) {
				if (state_.isAttacking()) {
					monster.setToRemove();
				}
			}
		}
	}
	
	// temporary
	public void checkCollisions() {
		checkMonstersCollisions();
	}
	
}
