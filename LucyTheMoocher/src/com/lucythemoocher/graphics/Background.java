package com.lucythemoocher.graphics;

import java.io.InputStream;

import android.graphics.drawable.BitmapDrawable;

import com.lucythemoocher.R;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.util.Ressources;

/**
 * Background of the level
 * For now, contains a picture hardcoded
 * Can be rendered by the Camera
 * @see Camera#drawBackground(Background)
 *
 */
public class Background implements Drawable {

	/**
	 * Constructor
	 * @todo 
	 */
	public Background() {
		InputStream is = Ressources.openRawRessources(R.drawable.background1);
		BitmapDrawable bd = new BitmapDrawable(is);
		background_ = new Image(bd);		
	}
	
	/**
	 * Draw the Background properly in the Camera
	 * (according to the Camera's position)
	 * @see Camera
	 */
	public void draw() {
		Game.getCam().drawBackground(this);
	}
	
	/**
	 * Getter
	 * @return The image of the Background
	 */
	public Image getImage() {
		return background_;
	}
	
	private Image background_;

}
