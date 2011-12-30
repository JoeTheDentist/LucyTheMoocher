package com.lucythemoocher.graphics;

import java.io.InputStream;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

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
		pxH_ = Game.getMap().pxH()*Camera.BACKGROUNDSPEED + 2*Globals.getInstance().getCamera().h();
		pxW_ = Game.getMap().pxW()*Camera.BACKGROUNDSPEED + 2*Globals.getInstance().getCamera().w();
		nbBoxH_ = (int) (pxH_/backgroundDown_.boxH());
		nbBoxW_ = (int) (pxW_/backgroundDown_.boxW());
		mapping_ = new int[nbBoxH_][nbBoxW_];
		for (int i=0; i<nbBoxH_; i++) {
			for (int j=0; j<nbBoxW_; j++) {
				if ( i == pxH_/2 ) {
					mapping_[i][j] = MathUtil.uniform(0, backgroundMiddle_.getSize()-1);
				} else if ( i < pxH_/2 ) {
					mapping_[i][j] = MathUtil.uniform(0, backgroundUp_.getSize()-1);
				} else {
					mapping_[i][j] = MathUtil.uniform(0, backgroundDown_.getSize()-1);
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
	 * Action asked by the camera
	 * @param x position of the screen in function of the background
	 * @param y position of the screen in function of the background
	 * @see Camera
	 */
	void draw(float x, float y) {
		Log.d("BACK", ""+x+" "+y);
		int i = (int) (y/backgroundUp_.boxW());
		int j = (int) (x/backgroundUp_.boxH());
		int h = (int) (Globals.getInstance().getCamera().h()/backgroundUp_.boxH())+2;
		int w = (int) (Globals.getInstance().getCamera().w()/backgroundUp_.boxW())+2;
		float offsetX = x-j*backgroundUp_.boxH();
		float offsetY = y-i*backgroundUp_.boxW();
		for ( int ii=i; ii<i+h && ii<nbBoxH_ ; ii++) {
			if ( ii == nbBoxH_/2 ) {
				if ( j%(backgroundMiddle_.boxW()/backgroundUp_.boxW()) != 0 ) {
					Globals.getInstance().getCamera().drawBackground(
							getImage(ii,
									(int) (j-j%(backgroundMiddle_.boxW()/backgroundUp_.boxW()))),
									(-j%(backgroundMiddle_.boxW()/backgroundUp_.boxW()))*backgroundUp_.boxW()-offsetX, 
									(ii-i)*backgroundUp_.boxH()-offsetY);
				}
			}
			for ( int jj=j; jj<j+w && jj<nbBoxW_ ; jj++ ) {
				if ( ii == nbBoxH_/2 ) {
					if ( jj%(backgroundMiddle_.boxW()/backgroundUp_.boxW()) == 0 ) {
						Globals.getInstance().getCamera().drawBackground(
								getImage(ii,jj), (jj-j)*backgroundUp_.boxW()-offsetX, (ii-i)*backgroundUp_.boxH()-offsetY);
					}
				} else {
					Globals.getInstance().getCamera().drawBackground(
							getImage(ii,jj), (jj-j)*backgroundUp_.boxW()-offsetX, (ii-i)*backgroundUp_.boxH()-offsetY);
				}
			}
		}
	}
	
	/**
	 * Getter
	 * @return The image of the Background
	 */
	public Image getImage() {
		return background_;
	}
	
	private Image getImage(int i, int j) {
		if ( i == nbBoxH_/2 ) {
			return backgroundMiddle_.getImage(mapping_[i][j]);
		} else if ( i < nbBoxH_/2 ) {
			return backgroundUp_.getImage(mapping_[i][j]);
		} else {
			return backgroundDown_.getImage(mapping_[i][j]);
		}
	}
}
