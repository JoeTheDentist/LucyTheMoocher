package com.lucythemoocher.actors;

import java.util.ArrayList;

import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.graphics.Drawable;
import com.lucythemoocher.physics.Box;
import com.lucythemoocher.physics.Cinematic;

/**
 * 
 * @author Guillaume
 *
 */
public abstract class Actor implements Drawable {
	protected Animation anim_;
	protected Cinematic pos_;
	private boolean isToRemove_ = false;
	private int id_ = -1;
	
	public void update() {
		pos_.update();
	}

	public boolean isToRemove() {
		return isToRemove_;
	}

	public void setToRemove() {
		isToRemove_ = true;
	}

	public void setId(int id) {
		if ( id_ == -1 ) {
			id_ = id;
		}
	}
	
	public int id() {
		return id_;
	}

	abstract public void draw();
	
	public void moveStop() {
		pos_.moveStop();
	}

	public void moveLeft() {
		pos_.moveLeft();
	}

	public void moveRight() {
		pos_.moveRight();
	}

	public void moveUp() {
		pos_.moveUp();
	}
	
	public void attack() {
		
	}
	
	public ArrayList<Box> getBoundingBoxes() {
		return pos_.boundingBoxes();
	}
	
	public float getX() {
		return pos_.x();
	}

	public float getY() {
		return pos_.y();
	}
}
