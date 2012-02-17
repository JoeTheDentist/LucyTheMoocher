package com.lucythemoocher.FX;

import java.util.Iterator;
import java.util.LinkedList;

public class FXManager {
	private LinkedList<FX> fx_;
	
	/**
	 * Constructor
	 */
	public FXManager() {
		//Oui c'est moche !
		FX.setManager(this);
		fx_ = new LinkedList<FX>();
	}
	
	/**
	 * Add a new FX to manage
	 * @param newFX
	 */
	void add(FX newFX) {
		fx_.add(newFX);
	}
	
	public void update() {
		Iterator<FX> it = fx_.iterator();
		while ( it.hasNext() ) {
			FX currFX = it.next();
			currFX.update();
			if ( currFX.ended() ) {
				it.remove();
			}
		}
	}
	
	public void render() {
		for ( FX f : fx_ ) {
			f.draw();
		}
	}
}
