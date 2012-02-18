package com.lucythemoocher.actors;

import java.util.LinkedList;

public class ProjectilesManager {
	private LinkedList<Projectile> proj_;
	
	public ProjectilesManager() {
		Projectile.setManager(this);
		proj_ = new LinkedList<Projectile>();
	}
	
	void add(Projectile newProj) {
		proj_.add(newProj);
	}
	
	public void render() {
		for ( Projectile p : proj_ ) {
			p.draw();
		}
	}
	
	public void update() {
		for ( Projectile p : proj_ ) {
			p.update();
		}
	}
}
