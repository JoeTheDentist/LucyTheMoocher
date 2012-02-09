package com.lucythemoocher.gui;

import java.util.Currency;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Drawable;
import com.lucythemoocher.graphics.Image;

public class MenuButton implements Drawable {
	private float posx_;
	private float posy_;
	private int index_;
	private boolean clicked_;
	private boolean focussed_;
	private Image normalImage_;
	private Image focussedImage_;
	private Image clickedImage_;
	private Image currentImage_;
	private MenuButtonListener listenerToNotify_;
	private MenuButtonTouchListener menuButtonTouchListener_;
	
	public MenuButton(float x, float y, int index, int idImage, int idImageFocussed, int idImageClicked, MenuButtonListener listener) {
		posx_ = x;
		posy_ = x;
		index_ = index;
		clicked_ = false;
		focussed_ = false;
		normalImage_ = new Image(idImage);
		focussedImage_ = new Image(idImageFocussed);
		clickedImage_ = new Image(idImageClicked);
		currentImage_ = normalImage_;
		listenerToNotify_ = listener;
		menuButtonTouchListener_ = new MenuButtonTouchListener(this, 0);
	}
	
	public void draw() {
		Globals.getInstance().getCamera().drawImage(posx_, posy_, currentImage());
	}

	public float w() {
		return currentImage().w();
	}
	
	public float h() {
		return currentImage().h();
	}
	
	public float x() {
		return posx_;
	}
	
	public float y() {
		return posy_;
	}
	
	public void setPos(float x, float y) {
		posx_ = x;
		posy_ = y;
	}
	
	public Image currentImage() {
		return currentImage_;
	}
	
	public void setClicked(boolean clicked) {
		clicked_ = true;
		currentImage_ = clickedImage_;
		listenerToNotify_.onButtonClicked(index_);
	}
	
	public void setFocussed(boolean focussed) {
		if (!focussed_ && focussed) {
			focussed_ = true;
			currentImage_ = focussedImage_;
		} else if (focussed_ && !focussed) {
			focussed_ = false;
			currentImage_ = normalImage_;
		}
	}
}
