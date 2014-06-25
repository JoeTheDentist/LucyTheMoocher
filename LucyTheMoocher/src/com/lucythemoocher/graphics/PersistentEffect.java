/**
 * 
 */
package com.lucythemoocher.graphics;

import java.util.LinkedList;

import com.lucythemoocher.Globals.Globals;

/**
 * Persistent effect which can be applied on any Drawable using pictures
 * Register a picture and its position with add(Image image, int x, int y)
 * and then it will stay at the screen with a decreasing alpha channel until 
 * it disappears (once add called, you have to use update and draw to make it work).
 * 
 * If the time between two calls to  add is longer than a value (timeBetweenAdd_ value), 
 * pictures will be added with an interpolation so that the pictures appear 
 * regularly and are frame rate independent.
 *
 */
public class PersistentEffect implements Drawable {

	private LinkedList<PersistentPic> list_;
	private int lastX_;
	private int lastY_;
	private float lastDrawTime_;
	private final static int timeBetweenAdd_ = 20;
	/**
	 * Constructor
	 */
	public PersistentEffect() {
		lastDrawTime_ = 0;
		list_ = new LinkedList<PersistentPic>();
	}
	
	/**
	 * Add a picture and its position to the persistence
	 * @param image 
	 * @param x
	 * @param y
	 * @param persistenceTime Time until the picture disappears in ms
	 */
	public void add(Image image, int x, int y, int persistenceTime) {
		float currentTime = Globals.getInstance().getGame().getTime();
		boolean hasDrawn = false;
		if (lastDrawTime_ == 0) { // first add
			list_.addLast(new PersistentPic(image, x, y, persistenceTime));
			hasDrawn = true;
		} else {
			for (float t = lastDrawTime_ + timeBetweenAdd_; t < currentTime; t += timeBetweenAdd_) {
				float a = (t - lastDrawTime_) / (currentTime - lastDrawTime_);
				int xx = (int)(a * (float)(lastX_ - x)) + x;
				int yy = (int)(a * (float)(lastY_ - y)) + y;
				list_.addLast(new PersistentPic(image, xx, yy, persistenceTime));
				hasDrawn = true;
			}
		}
		if (hasDrawn) {
			lastX_ = (int)x;
			lastY_ = (int)y;
			lastDrawTime_ = Globals.getInstance().getGame().getTime();
		}
	}
	
	/**
	 * Draw all the pictures added with correct alpha
	 */
	public void draw() {
		for (PersistentPic pic: list_) {
			pic.draw();
		}
	}
	
	/**
	 * Update the list of pictures
	 */
	public void update() {
		while (!list_.isEmpty() && list_.getFirst().isDead()) {
			list_.removeFirst();
		}	
	}
	
	/**
	 * Clear the persistent list
	 */
	public void reset() {
		list_.clear();
		lastDrawTime_ = 0;
	}

}
