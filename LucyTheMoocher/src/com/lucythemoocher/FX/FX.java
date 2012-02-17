package com.lucythemoocher.FX;

import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.graphics.Drawable;

/**
 * Class for FX.<br/>
 * Represents a visual and sound effects 
 */
public class FX implements Drawable {
	private Animation anim_;
	private boolean ended_ = false;
	private float x_;
	private float y_;
	
	private static FXManager MANAGER_;
	
	static void setManager(FXManager manager) {
		MANAGER_ = manager;
	}
	
	/**
	 * Constructor
	 * @param resourceImg : resource of the image
	 * @param resourceSound : resource of the sound to play at the construction TODO
	 * @param x : pos x
	 * @param y : pos y
	 */
	public FX(int resourceImg, int resourceSound, float x, float y) {
		anim_ = new Animation(resourceImg, true);
		x_ = x;
		y_ = y;
		MANAGER_.add(this);
	}
	
	/**
	 * Update
	 */
	public void update() {
		anim_.update();
		if ( anim_.cycleEnded() ) {
			ended_ = true;
		}
	}
	
	/**
	 * Drawing the FX
	 */
	public void draw() {
		anim_.draw(x_, y_);
	}
	
	/**
	 * If the FX is ended (and need to be destroyed)
	 * @return if the animation is ended
	 */
	public boolean ended() {
		return ended_;
	}
	
	/**
	 * Getter
	 * @return x
	 */
	public float getX() {
		return x_;
	}
	
	/**
	 * Getter
	 * @return y
	 */
	public float getY() {
		return y_;
	}
}
