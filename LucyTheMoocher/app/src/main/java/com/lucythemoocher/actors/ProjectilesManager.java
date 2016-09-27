package com.lucythemoocher.actors;

import java.util.Iterator;
import java.util.LinkedList;

public class ProjectilesManager {
	private LinkedList<Projectile> proj_;
	
	public ProjectilesManager() {
		proj_ = new LinkedList<Projectile>();
	}
	
	/**
	 * Add a new projectile to draw and update.
	 * Package visibility.
	 * @param newProj
	 */
	void add(Projectile newProj) {
		proj_.add(newProj);
	}
	
	public void render() {
		for ( Projectile p : proj_ ) {
			p.draw();
		}
	}
	
	public void update() {
		Iterator<Projectile> i = proj_.iterator();
		while ( i.hasNext() ) {
			Projectile p = i.next();
			p.update();
			if (p.isToRemove()) {
				i.remove();
			}
		}
	}
	
	public Iterator<Projectile> getIterator() {
		return proj_.iterator();
	}
	
	/**
	 * Remove all projectiles
	 */
	public void clear() {
		proj_.clear();
	}
}
