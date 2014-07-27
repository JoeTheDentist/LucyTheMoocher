package com.lucythemoocher.graphics;

import java.io.InputStream;

import com.lucythemoocher.Globals.Globals;

import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;

public class PictureContainer {
	private SparseArray<BitmapDrawable> images_;
	
	public PictureContainer() {
		images_ = new SparseArray<BitmapDrawable>();
	}
	
	BitmapDrawable getImage(int image) {
		BitmapDrawable img = images_.get(image);
		if ( img != null ) {
			return img;
		} else {
			// Only way to get good size...
			InputStream is = Globals.getInstance().getCamera().getResources().openRawResource(image);
			BitmapDrawable bd = new BitmapDrawable(is);
			images_.put(image, bd);
			return bd;
		}
	}
}
