package com.lucythemoocher.gui;


import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Drawable;
import com.lucythemoocher.graphics.Image;

/**
 * MenuButton
 * A button has 3 images: one in normal state, one when focussed, one when clicked.
 * You can connect a listener to the button: when the button is clicked, onButtonClicked(int) 
 * will be called from the listener, with the value of the button's index. Reimplement this method
 * to to whatever you want when the button is clicked
 * 
 * The button has to be destroyed with destroy() method when you don't use it anymore (or the button will stay alive, 
 * and won't be rendered but will be clicked)
 */
public class MenuButton implements Drawable {
	private float posx_;
	private float posy_;
	private int index_;
	private boolean active_;
	private boolean clicked_;
	private boolean focussed_;
	private Image normalImage_;
	private Image focussedImage_;
	private Image clickedImage_;
	private Image currentImage_;
	private MenuButtonListener listenerToNotify_;
	private MenuButtonTouchListener menuButtonTouchListener_; // handle events for the button
	
	/**
	 * Public constructor
	 * @param x initial position
	 * @param y initial position
	 * @param index Index of the button
	 * @param idImage Image resource for normal state
	 * @param idImageFocussed Image resource for focussed state
	 * @param idImageClicked Image resource for clicked state
	 * @param listener The listener connected with the button
	 */
	public MenuButton(float x, float y, int index, int idImage, int idImageFocussed, int idImageClicked, MenuButtonListener listener) {
		posx_ = x;
		posy_ = x;
		index_ = index;
		active_ = true;
		clicked_ = false;
		focussed_ = false;
		normalImage_ = new Image(idImage);
		focussedImage_ = new Image(idImageFocussed);
		clickedImage_ = new Image(idImageClicked);
		currentImage_ = normalImage_;
		listenerToNotify_ = listener;
		menuButtonTouchListener_ = new MenuButtonTouchListener(this);
	}
	
	/**
	 * Draw the button at the correct position, without using scrolling
	 */
	public void draw() {
		if (active_)
			Globals.getInstance().getCamera().drawImageOnHud(posx_, posy_, currentImage());
	}

	/**
	 * Getter
	 * @return Button's width
	 */
	public float w() {
		return currentImage().w();
	}
	
	/**
	 * Getter
	 * @return Button's height
	 */	
	public float h() {
		return currentImage().h();
	}
	
	
	/**
	 * Getter
	 * @return Button's position x
	 */
	public float x() {
		return posx_;
	}
	
	/**
	 * Getter
	 * @return Button's position y
	 */	
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
		if (active_) {
			clicked_ = true;
			currentImage_ = clickedImage_;
			if (listenerToNotify_ != null)
				listenerToNotify_.onButtonClicked(index_);
		}
	}
	
	public void setFocussed(boolean focussed) {
		if (active_) {
			if (!focussed_ && focussed) {
				focussed_ = true;
				currentImage_ = focussedImage_;
			} else if (focussed_ && !focussed) {
				focussed_ = false;
				currentImage_ = normalImage_;
			}
		}
	}
	
	/**
	 * Move the button so that it's center is (x, y)
	 * @param x
	 * @param y
	 */
	public void centerOn(float x, float y) {
		posx_ = x - w() / 2;
		posy_ = y - h() / 2;
	}
	
	public void hide() {
		active_ = false;
	}
	
	public void show() {
		active_ = true;
	}
	
	/**
	 * Has to be called when you don't use the button anymore
	 * (or the button will stay activated but won't be rendered)
	 */
	public void destroy() {
		hide();
		menuButtonTouchListener_.unregister();
	}
}
