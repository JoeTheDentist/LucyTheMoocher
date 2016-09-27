package com.lucythemoocher.graphics;

import com.lucythemoocher.Globals.Globals;

/**
 * Auxiliary class for PersistentEffect.
 * Contains a picture, a position and the time of creation
 * Dies after a certain time (timeLife_ attribute) and
 * has an decreasing alpha channel.
 */
public class PersistentPic implements Drawable {

	private Image image_;
	private int x_;
	private int y_;
	private int timeOnCreation_;
	
	private int timeLife_;
	
	
	/**
	 * Constructor 
	 * 
	 * @param image
	 * @param x Position in pixels
	 * @param y Position in pixels
	 * @param timeLife Time until the picture disappears in ms
	 */
	PersistentPic(Image image, int x, int y, int timeLife) {
		image_ = image;
		x_ = x;
		y_ = y;
		timeOnCreation_ = (int)Globals.getInstance().getGame().getTime();
		timeLife_ = timeLife;
	}
	
	/**
	 * Getter
	 * @return True if the picture has to be destroyed
	 */
	public boolean isDead() {
		return Globals.getInstance().getGame().getTime() - timeOnCreation_ > timeLife_;
	}
	
	/**
	 * Draw the picture with correct alpha
	 */
	public void draw() {
		int previousAlpha = image_.getBitmap().getPaint().getAlpha();
		image_.getBitmap().getPaint().setAlpha(255 - (((int)Globals.getInstance().getGame().getTime() - timeOnCreation_) * 255) / timeLife_);
		Globals.getInstance().getCamera().drawImage(x_, y_, image_);
		image_.getBitmap().getPaint().setAlpha(previousAlpha);
	}

}
