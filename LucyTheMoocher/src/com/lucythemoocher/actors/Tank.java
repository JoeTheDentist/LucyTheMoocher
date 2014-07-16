package com.lucythemoocher.actors;

import android.util.Log;

import com.lucythemoocher.R;
import com.lucythemoocher.FX.FX;
import com.lucythemoocher.physics.Cinematic;

public class Tank extends Monster {
	
	private TankState state_;
	
	public Tank(float x, float y) {
		super();
		
		getDrawer().initializeAnimation(R.drawable.tank);
		setCinematic(new Cinematic(0.4f));
		getCinematic().addBox(x, y, getH(), getW());
		
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
			FX fx = new FX(R.drawable.spawning, 0, context_.pos_.x(), context_.pos_.y());
			changeState(new FireRight(context_));
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
			changeState(new FireLeft(context_));
		}
	}
}

class FireLeft extends TankState {

	public FireLeft(Tank context) {
		super(context);
		int tab[] = {8,9,10,11};
		context_.getDrawer().setAnimation(tab, 70);
	}

	@Override
	public void update() {
		if ( context_.getDrawer().getAnim().cycleEnded() ) {
			changeState(new MoveLeft(context_));
		}
	}
}

class FireRight extends TankState {

	public FireRight(Tank context) {
		super(context);
		int tab[] = {12,13,14,15};
		context_.getDrawer().setAnimation(tab, 70);
	}

	@Override
	public void update() {
		if ( context_.getDrawer().getAnim().cycleEnded() ) {
			changeState(new MoveRight(context_));
		}
	}
}