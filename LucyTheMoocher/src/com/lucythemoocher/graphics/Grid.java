package com.lucythemoocher.graphics;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Grid {
	private Image fullImage_;
	private Image grid_[];
	private int nbImg_;
	private float height_;
	private float width_;
	
	public Grid(int imgId, int height, int width) {
		height_ = height;
		width_ = width;
		fullImage_ = new Image(imgId);
		int ver = (int)fullImage_.h()/(height+2) ;
		int hor = (int)fullImage_.w()/(width+2);
		
		nbImg_ = ver*hor;
		grid_ = new Image[nbImg_];
		for (int i=0; i<ver; i++) {
			for (int j=0; j<hor; j++) {
				BitmapDrawable currBitmap = new BitmapDrawable(
						Bitmap.createBitmap(fullImage_.getBitmap().getBitmap(),
								(int)(1+j*(width+2)), (int)(1+i*(height+2)), (int)width, (int)height, null, true));
				grid_[i*hor+j] = new Image(currBitmap);
			}
		}
	}
	
	public Image getImage(int id) {
		if ( id >= nbImg_ ) {
			Log.w("Grid", "out of range : "+id+":"+nbImg_);
		}
		return grid_[id%nbImg_];
	}
	
	public float boxH() {
		return height_;
	}
	
	public float boxW() {
		return width_;
	}
	
	public Image getFullImage() {
		return fullImage_;
	}
	
	public int getSize() {
		return nbImg_;
	}
}
