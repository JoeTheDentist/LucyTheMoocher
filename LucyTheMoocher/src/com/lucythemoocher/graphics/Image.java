package com.lucythemoocher.graphics;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Image {
	private BitmapDrawable image_;
	private static PictureContainer PC = new PictureContainer();
	
	public Image(BitmapDrawable b) {
		image_ = b;
	}
	
	public Image(int i) {
		image_ = PC.getImage(i);
	}
	
	BitmapDrawable getBitmap() {
		return image_;
	}

	public float h() {
		return (float)image_.getBitmap().getHeight();
	}
	
	public float w() {
		return (float)image_.getBitmap().getWidth();
	}
}
