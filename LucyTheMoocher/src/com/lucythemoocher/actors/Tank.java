package com.lucythemoocher.actors;

import com.lucythemoocher.R;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.util.Direction;

public class Tank extends Monster {
	private TankState state_;
	
	public Tank(float x, float y, int direction) {
		super();
		getDrawer().initializeAnimation(R.drawable.tank);
		setCinematic(new Cinematic(0.4f));
		getCinematic().addBox(x, y, getH(), getW());
		state_ = new Move(this, direction);
	}
	
	public void update() {
		super.update();
		state_.update();
	}
	
	void changeState(TankState newState) {
		state_ = newState;
	}
	
	/**
	 * X position where to create projectiles
	 * @return
	 */
	public float getXFire() {
		float x = getCinematic().x();
		if (state_.getDir() == Direction.LEFT) {
			x += getDrawer().getAnim().getW();
		}
		return x;
	}
	
	/**
	 * Y position where to create projectiles
	 * @return
	 */
	public float getYFire() {
		float y = getCinematic().y();
		y += getDrawer().getAnim().getH() / 2;
		return y;
	}
}

/**
 * State of the Tank
 */
abstract class TankState {
	protected Tank context_;
	protected int dir_;
	
	public TankState(Tank context, int direction) {
		context_ = context;
		dir_ = direction;
	}
	
	public void changeState(TankState newState) {
		context_.changeState(newState);
	}
	
	public int getDir() {
		return dir_;
	}
	
	public abstract void update();
}

class Move extends TankState {

	public Move(Tank context, int direction) {
		super(context, direction);
		if (dir_ == Direction.LEFT) {
			int tab[] = {0,1,2,3};
			context_.getDrawer().setAnimation(tab, 200);
		} else {
			int tab[] = {4,5,6,7};
			context_.getDrawer().setAnimation(tab, 200);
		}
	}

	@Override
	public void update() {
		if (dir_ == Direction.LEFT) {
			context_.moveLeft();
			if ( context_.pos_.hasLeftCollision() ) {
				changeState(new Fire(context_, Direction.RIGHT));
			}
		} else {
			context_.moveRight();
			if ( context_.pos_.hasRightCollision() ) {
				changeState(new Fire(context_, Direction.LEFT));
			}
		}
	}
}

class Fire extends TankState {

	public Fire(Tank context, int direction) {
		super(context, direction);
		if (dir_ == Direction.LEFT) {
			int tab[] = {8,9,10,11};
			context_.getDrawer().setAnimation(tab, 70);
		} else {
			int tab[] = {12,13,14,15};
			context_.getDrawer().setAnimation(tab, 70);
		}
		new Projectile(context_.getXFire(), context_.getYFire(), dir_);
	}

	@Override
	public void update() {
		if ( context_.getDrawer().getAnim().cycleEnded() ) {
			changeState(new Move(context_, dir_));
		}
	}
}
