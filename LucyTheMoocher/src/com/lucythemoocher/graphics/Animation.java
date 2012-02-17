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
	private boolean cycleEnded_ = false;

	private static final float DEFAULT_PERIOD = 100;

	public Animation() {
		grid_ = null;
		offsetCurrentFrame_ = 0.0f;
	}

	/**
	 * Constructor
	 * @param resource Resource index
	 * @see Grid
	 */
	public Animation(int resource) {
		this();
		initialize(resource);
	}

	/**
	 * Constructor
	 * @param resource Resource index
	 * @param allImages : set or not all the images 
	 * @see Grid
	 */
	public Animation(int resource, boolean allImages) {
		this(resource);
		if ( allImages ) {
			int t[] = new int[grid_.getSize()];
			for ( int i=0; i<t.length; i++ ) {
				t[i] = i;
			}
			setAnimation(t, DEFAULT_PERIOD);
		}
	}

	/**
	 * Initialize the animation 
	 * @param resource Resource index
	 * @see Grid
	 */	
	public void initialize(int resource) {
		grid_ = new Grid(resource);
		int t[] = {0};
		setAnimation(t, 1);
		timeOnLastDraw_ = Game.getTime();
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
		Globals.getInstance().getCamera().drawImage(x, y, getCurrentImage());
	}

	/**
	 * Getter
	 * @return The current image
	 */
	public Image getCurrentImage() {
		return grid_.getImage(tab_[currentFrame_]);
	}

	/**
	 * Update the animation, must be called at least once a frame
	 */
	public void update() {
		offsetCurrentFrame_ += (Game.getTime() - timeOnLastDraw_) / period_;
		currentFrame_ += (int) (offsetCurrentFrame_);
		if ( currentFrame_ == tab_.length ) {
			cycleEnded_ = true;
		} else {
			cycleEnded_ = false;
		}
		currentFrame_ %= tab_.length;
		offsetCurrentFrame_ -= Math.floor((double)(offsetCurrentFrame_));
		timeOnLastDraw_ = Game.getTime();
	}

	/**
	 * True when the first image of the animation will be displayed in the coming cycle.
	 * @return if the this is the end of the cycle of the animation
	 */
	public boolean cycleEnded() {
		return cycleEnded_;
	}

}
