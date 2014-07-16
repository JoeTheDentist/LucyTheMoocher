package com.lucythemoocher.actors;

import com.lucythemoocher.Globals.Globals;

/**
 * Class for projectiles
 */
public class Projectile extends Actor {
	
	public Projectile() {
		super();
		Globals.getInstance().getGame().getProjectilesManager().add(this);
	}
}
