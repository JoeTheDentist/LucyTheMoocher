package com.lucythemoocher.graphics;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;

public class PersistentPic implements Drawable {

	private Image image_;
	private int x_;
	private int y_;
	private int timeOnCreation_;
	
	private static final int timeLife_ = 150;
	
	PersistentPic(Image image, int x, int y) {
		image_ = image;
		x_ = x;
		y_ = y;
		timeOnCreation_ = (int)Game.getTime();
	}
	
	public boolean isDead() {
		return Game.getTime() - timeOnCreation_ > timeLife_;
	}
	
	public void draw() {
		int previousAlpha = image_.getBitmap().getPaint().getAlpha();
		image_.getBitmap().getPaint().setAlpha(255 - (((int)Game.getTime() - timeOnCreation_) * 255) / timeLife_);
		Globals.getInstance().getCamera().drawImage(x_, y_, image_);
		image_.getBitmap().getPaint().setAlpha(previousAlpha);
	}

}
