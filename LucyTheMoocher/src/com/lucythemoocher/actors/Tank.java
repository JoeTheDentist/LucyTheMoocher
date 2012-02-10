package com.lucythemoocher.actors;

import com.lucythemoocher.R;
import com.lucythemoocher.physics.TankCinematic;

public class Tank extends Monster {
	
	private TankState state_;
	
	public Tank() {
		super();
		
		getDrawer().initializeAnimation(R.drawable.tank, 70, 150);
		setCinematic(new TankCinematic());
		getCinematic().addBox(60, 1199, getH(), getW());
		
		state_ = new MoveLeft(this);
	}
	
	public void update() {
		super.update();
		state_.update();
	}
	
	void changeState(TankState newState) {
		state_ = newState;
	}
}

/**
 * State of the Tank
 */
abstract class TankState {
	protected Tank context_;
	
	public TankState(Tank context) {
		context_ = context;
	}
	
	public void changeState(TankState newState) {
		context_.changeState(newState);
	}
	
	public abstract void update();
}

class MoveLeft extends TankState {

	public MoveLeft(Tank context) {
		super(context);
		int tab[] = {0,1,2,3};
		context_.getDrawer().setAnimation(tab, 200);
	}

	@Override
	public void update() {
		context_.moveLeft();
		if ( context_.pos_.hasLeftCollision() ) {
			changeState(new MoveRight(context_));
		}
	}
	
}

class MoveRight extends TankState {

	public MoveRight(Tank context) {
		super(context);
		int tab[] = {4,5,6,7};
		context_.getDrawer().setAnimation(tab, 200);
	}

	@Override
	public void update() {
		context_.moveRight();
		if ( context_.pos_.hasRightCollision() ) {
			changeState(new MoveLeft(context_));
		}
	}
	
}