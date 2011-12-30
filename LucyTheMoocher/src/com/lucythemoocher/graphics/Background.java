package com.lucythemoocher.graphics;

import java.io.InputStream;

import android.graphics.drawable.BitmapDrawable;

import com.lucythemoocher.R;
import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.util.MathUtil;
import com.lucythemoocher.util.Resources;

/**
 * Background of the level\n
 * Randomly generated with elementary blocks.\n
 * The sizes of images (in the grids) must be multiples.
 * Can be rendered by the Camera
 * @see Camera#drawBackground(Background)
 *
 */
public class Background implements Drawable {

	private Image background_;
	private Grid backgroundUp_;
	private Grid backgroundDown_;
	private Grid backgroundMiddle_;
	private int[][] mapping_;
	private float pxH_;
	private float pxW_;
	private int nbBoxH_;
	private int nbBoxW_;
	
	/**
	 * Constructor
	 * @todo 
	 */
	public Background() {
		InputStream is = Resources.openRawRessources(R.drawable.background1);
		BitmapDrawable bd = new BitmapDrawable(is);
		background_ = new Image(bd);
		backgroundUp_ = new Grid(R.drawable.background_up, 256, 256);
		backgroundDown_ = new Grid(R.drawable.background_down, 256, 256);
		backgroundMiddle_ = new Grid(R.drawable.background_middle, 256, 1024);
		pxH_ = Game.getMap().pxH()/Camera.BACKGROUNDSPEED + Globals.getInstance().getCamera().h();
		pxW_ = Game.getMap().pxW()/Camera.BACKGROUNDSPEED + Globals.getInstance().getCamera().w();
		nbBoxH_ = (int) (pxH_/backgroundDown_.boxH());
		nbBoxW_ = (int) (pxW_/backgroundDown_.boxW());
		mapping_ = new int[nbBoxH_][nbBoxW_];
		for (int i=0; i<nbBoxH_; i++) {
			for (int j=0; j<nbBoxW_; j++) {
				if ( i == pxH_/2 ) {
					mapping_[i][j] = MathUtil.uniform(0, backgroundMiddle_.getSize());
				} else if ( i < pxH_/2 ) {
					mapping_[i][j] = MathUtil.uniform(0, backgroundUp_.getSize());
				} else {
					mapping_[i][j] = MathUtil.uniform(0, backgroundDown_.getSize());
				}
			}
		}
	}
	
	/**
	 * Draw the Background properly in the Camera
	 * (according to the Camera's position)
	 * @see Camera
	 */
	public void draw() {
		Globals.getInstance().getCamera().drawBackground(this);
	}
	
	/**
	 * Getter
	 * @return The image of the Background
	 */
	public Image getImage() {
		return background_;
	}
	
	public Image getImage(int i, int j) {
		if ( i == nbBoxH_/2 ) {
			return backgroundMiddle_.getImage(mapping_[i][j]);
		} else if ( i < nbBoxH_/2 ) {
			return backgroundUp_.getImage(mapping_[i][j]);
		} else {
			return backgroundDown_.getImage(mapping_[i][j]);
		}
	}
}
