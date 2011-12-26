package com.lucythemoocher.graphics;

import java.io.InputStream;
import java.util.HashMap;

import com.lucythemoocher.Globals.Globals;

import android.graphics.drawable.BitmapDrawable;

public class PictureContainer {
	private HashMap<Integer, BitmapDrawable> images_;
	
	public PictureContainer() {
		images_ = new HashMap<Integer, BitmapDrawable>();
	}
	
	BitmapDrawable getImage(int image) {
		if ( images_.containsKey(image) ) {
			return images_.get(image);
		} else {
			//Faire comme ca... sinon pas la bonne taille
			InputStream is = Globals.getInstance().getCamera().getResources().openRawResource(image);
			BitmapDrawable bd = new BitmapDrawable(is);
			return bd;
		}
	}
	
	
}
