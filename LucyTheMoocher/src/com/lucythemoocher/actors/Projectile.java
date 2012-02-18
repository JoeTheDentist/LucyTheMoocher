package com.lucythemoocher.actors;

/**
 * Class for projectiles
 */
public class Projectile extends Actor {

	private static ProjectilesManager MANAGER_;
	
	static void setManager(ProjectilesManager manager) {
		MANAGER_ = manager;
	}
	
	public Projectile() {
		MANAGER_.add(this);
	}
}
