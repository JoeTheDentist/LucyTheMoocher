package com.lucythemoocher.actors;

import com.lucythemoocher.R;
import com.lucythemoocher.actors.maincharacter.state.StateNone;
import com.lucythemoocher.controls.AIController;
import com.lucythemoocher.controls.ActionController;
import com.lucythemoocher.util.Direction;

/**
 * Target of the game.
 * I.e. Freddy!!!
 */
public class TargetCharacter extends Actor {
	/**
	 * Constructor.
	 * Provided controller expresses the behavior of Freddy
	 * @param controller AI controller
	 * @param x
	 * @param y
	 */
	public TargetCharacter(AIController controller, float x, float y) {
		super();
		getDrawer().initializeAnimation(R.drawable.freddy);
		getCinematic().addBox(x, y, getH(), getW());
		controller_ = controller;
		controller_.setControllable(this);
		
		int tab[] = {0,1,2,3};
		getDrawer().getAnim().setAnimation(tab, 100);
	}
	
	private AIController controller_;
}
