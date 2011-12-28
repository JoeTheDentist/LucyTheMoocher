package com.lucythemoocher.actors;

import java.util.ArrayList;

import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.graphics.Drawable;
import com.lucythemoocher.graphics.ActorDrawer;

import com.lucythemoocher.physics.Box;
import com.lucythemoocher.physics.Cinematic;

/**
 * 
 *
 */
public abstract class Actor implements Drawable {
	protected Cinematic pos_;
	private boolean isToRemove_ = false;
	private int id_ = -1;
	private ActorDrawer drawer_;
	
	public Actor() {
		drawer_ = new ActorDrawer();
	}
	
	public void update() {
		pos_.update();
		getDrawer().update();
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

	public void draw() {
		getDrawer().draw((int)getX(), (int)getY());
	}
	
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
	
	/**
	 * Getter (valid only once animation has been initialized)
	 * @return Actor's width
	 */
	public float getW() {
		return getDrawer().getAnim().getW();
	}
	
	/**
	 * Getter (valid only once animation has been initialized)
	 * @return Actor's height
	 */	
	public float getH() {
		return getDrawer().getAnim().getH();
	}
	
	
	
	public ActorDrawer getDrawer() {
		return drawer_;
	}
}
