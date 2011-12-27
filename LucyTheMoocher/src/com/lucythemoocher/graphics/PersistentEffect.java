/**
 * 
 */
package com.lucythemoocher.graphics;

import java.util.LinkedList;

/**
 * 
 *
 */
public class PersistentEffect implements Drawable {

	private LinkedList<PersistentPic> list_;
	/**
	 * 
	 */
	public PersistentEffect() {
		list_ = new LinkedList<PersistentPic>();
	}
	
	public void add(Image image, int x, int y) {
		list_.addLast(new PersistentPic(image, x, y));
	}
	
	public void draw() {
		while (!list_.isEmpty() && list_.getFirst().isDead()) {
			list_.removeFirst();
		}
		for (PersistentPic pic: list_) {
			pic.draw();
		}
	}

}
