package com.lucythemoocher.actors;

import com.lucythemoocher.R;
import com.lucythemoocher.game.Game;

public class Monster extends Actor {
	
	public Monster() {
		super();
		getDrawer().initializeAnimation(R.drawable.lucy_states, 80, 76);
		getCinematic().addBox(60, 1199, getH(), getW());
	}
	
	public void update() {
		super.update();
		/*
		if (Game.getTime() % 1000 < 500) {
			moveLeft();
		} else {
			moveRight();
		}
		*/
	}
	
	public void moveLeft() {
		super.moveLeft();
		int tab[] = {4,5,6,7};
		getDrawer().setAnimation(tab, 200);
	}

	public void moveRight() {
		super.moveRight();
		int tab[] = {8,9,10,11};
		getDrawer().setAnimation(tab, 200);
	}
	
}