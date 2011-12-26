package com.lucythemoocher.graphics;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;

public class Animation {
	private Grid grid_;
	private int tab_[];
	private float period_;
	private int currentFrame_; // we print grid[tab_[currentFrame_]]
	private float timeOnLastDraw_; 
	private float offsetCurrentFrame_; // will be added to currentFrame_ when >= 1
	
	/**
	 * Constructor 
	 * @param resource Resource index
	 * @param picH Pictures's height
	 * @param picW Pictures's width
	 */
	public Animation(int resource, int picH, int picW) {
		grid_ = new Grid(resource, picH, picW);
		int t[] = {0};
		setAnimation(t, 1);
		timeOnLastDraw_ = Game.getTime();
		offsetCurrentFrame_ = 0.0f;
	}
	
	/**
	 * 
	 * @param tab Indices of the pictures the the animation grid
	 * @param period Animation's period in ms
	 */
	public void setAnimation(int tab[], float period) {
		tab_ = new int[tab.length];
		for (int i=0; i<tab.length; i++) {
			tab_[i] = tab[i];
		}
		period_ = period;
		currentFrame_ = 0;
	}
	
	/**
	 * Getter
	 * @return Pictures' height in pixels
	 */
	public float getH() {
		return grid_.getImage(0).h();
	}
	
	/**
	 * Getter
	 * @return Pictures' width in pixels
	 */
	public float getW() {
		return grid_.getImage(0).w();
	}
	
	/**
	 * Draw the animation at the position x, y
	 * @param x
	 * @param y 
	 */
	public void draw(float x, float y) {
		offsetCurrentFrame_ += (Game.getTime() - timeOnLastDraw_) / period_;
		currentFrame_ += (int) (offsetCurrentFrame_);
		currentFrame_ %= tab_.length;
		offsetCurrentFrame_ -= Math.floor((double)(offsetCurrentFrame_));
		timeOnLastDraw_ = Game.getTime();
		Globals.getInstance().getCamera().drawImage(x, y, grid_.getImage(tab_[currentFrame_]));
	}
	
}
