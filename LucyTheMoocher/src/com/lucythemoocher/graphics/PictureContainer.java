package com.lucythemoocher.graphics;

import java.io.InputStream;
import java.util.HashMap;

import com.lucythemoocher.game.Game;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

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
			InputStream is = Game.getCam().getResources().openRawResource(image);
			BitmapDrawable bd = new BitmapDrawable(is);
			return bd;
		}
	}
	
	
}
