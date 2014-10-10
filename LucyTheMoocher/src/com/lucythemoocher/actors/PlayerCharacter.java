package com.lucythemoocher.actors;

import java.util.Iterator;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.actors.maincharacter.state.*;

import com.lucythemoocher.controls.Controllable;
import com.lucythemoocher.controls.ActionController;
import com.lucythemoocher.util.Direction;
import com.lucythemoocher.R;

public class PlayerCharacter extends Actor implements Controllable {
	private State state_;
	private ActionController controller_;
	
	/**
	 * Constructor. The controller will be attached with 
	 * the character in the constructor, you don't have
	 * to do it outside
	 * @param controller Player's controller
	 * @param x
	 * @param y
	 */
	public PlayerCharacter(ActionController controller, float x, float y) {
		super();
		getDrawer().initializeAnimation(R.drawable.lucy_states);
		getCinematic().addBox(x, y, getH(), getW());
		state_ = new StateNone(this, pos_, getDrawer().getAnim(), Direction.LEFT);
		controller_ = controller;
		controller_.setControllable(this);
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
		Iterator<Monster> it = Globals.getInstance().getGame().getMonstersManager().getIterator();
		while (it.hasNext()) {
			Monster monster = it.next();
			if (collidesWith(monster)) {
				if (state_.isAttacking()) {
					monster.setToRemove();
				} else {
					Globals.getInstance().lose();
				}
			}
		}
	}
	
	// temporary
	public void checkCollisions() {
		checkMonstersCollisions();
	}
	
	public int getDir() {
		return state_.getDir();
	}
	
}
