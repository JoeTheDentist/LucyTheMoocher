package com.lucythemoocher.graphics;

import java.io.InputStream;

import android.graphics.drawable.BitmapDrawable;

import com.lucythemoocher.R;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.util.Ressources;

public class Background implements Drawable {

	public Background() {
		InputStream is = Ressources.openRawRessources(R.drawable.background1);
		BitmapDrawable bd = new BitmapDrawable(is);
		background_ = new Image(bd);		
	}
	
	public void draw() {
		Game.getCam().drawBackground(this);
	}
	
	public Image getImage() {
		return background_;
	}
	
	private Image background_;

}
