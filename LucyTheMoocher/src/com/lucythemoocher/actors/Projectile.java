package com.lucythemoocher.actors;

import com.lucythemoocher.R;
import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.physics.Cinematic;
import com.lucythemoocher.util.Direction;

/**
 * Class for projectiles
 */
public class Projectile extends Actor {
	private int dir_;
	
	public Projectile(float x, float y, int direction) {
		super();
		Globals.getInstance().getGame().getProjectilesManager().add(this);
		dir_ = direction;
		getDrawer().initializeAnimation(R.drawable.tank_projectile);
		if (direction == Direction.LEFT) {
			int tab[] = {1};
			getDrawer().setAnimation(tab, 200);
		} else {
			int tab[] = {0};
			getDrawer().setAnimation(tab, 200);
		}
		setCinematic(new Cinematic(1f, false));
		getCinematic().addBox(x, y, getH(), getW());
	}
	
	public void update() {
		super.update();
		if (dir_ == Direction.LEFT) {
			moveLeft();
		} else {
			moveRight();
		}
		if (getCinematic().hasLeftCollision() || getCinematic().hasRightCollision()) {
			setToRemove();
		}
	}
}
