package com.lucythemoocher.actors;

import java.util.ArrayList;

import com.lucythemoocher.graphics.Drawable;
import com.lucythemoocher.graphics.ActorDrawer;

import com.lucythemoocher.physics.Box;
import com.lucythemoocher.physics.Cinematic;

/**
 * A Drawable and physical entity interacting with the other
 * in a game
 */
public abstract class Actor implements Drawable {
	protected Cinematic pos_;
	private boolean isToRemove_ = false;
	private int id_ = -1;
	private ActorDrawer drawer_;
	
	/**
	 * Public constructor
	 */
	public Actor() {
		drawer_ = new ActorDrawer();
		setCinematic(new Cinematic());
	}
	
	/**
	 * Updates the Actor, should be called at every game's update
	 */
	public void update() {
		pos_.update();
		getDrawer().update();
	}

	/**
	 * Getter
	 * @return True if the Actor should be removed from the game
	 */
	public boolean isToRemove() {
		return isToRemove_;
	}

	/**
	 * Notify that the actor should be removed from the game
	 */
	public void setToRemove() {
		isToRemove_ = true;
	}

	/**
	 * Mutator
	 * @param id
	 */
	public void setId(int id) {
		if ( id_ == -1 ) {
			id_ = id;
		}
	}
	
	/**
	 * Getter
	 * @return Actor's id
	 */
	public int id() {
		return id_;
	}

	/**
	 * Override Drawable.draw
	 */
	public void draw() {
		getDrawer().draw((int)getX(), (int)getY());
	}
	
	/**
	 * Called when the actor should stop 
	 * (can be overrode)
	 */
	public void moveStop() {
		pos_.moveStop();
	}

	/**
	 * Called when the actor should go to left 
	 * (can be overrode)
	 */
	public void moveLeft() {
		pos_.moveLeft();
	}
	
	/**
	 * Called when the actor should go to right 
	 * (can be overrode)
	 */
	public void moveRight() {
		pos_.moveRight();
	}

	/**
	 * Called when the actor should go up 
	 * (can be overrode)
	 */
	public void moveUp() {
		pos_.moveUp();
	}
	
	/**
	 * Called when the actor should attack 
	 * (can be overrode)
	 */
	public void attack() {
		
	}
	
	/**
	 * Getter
	 * @return Actor's bounding boxes
	 */
	public ArrayList<Box> getBoundingBoxes() {
		return pos_.boundingBoxes();
	}
	
	/**
	 * Getter
	 * @return Actor's position x
	 */
	public float getX() {
		return pos_.x();
	}

	/**
	 * Getter
	 * @return Actor's position y
	 */
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
	
	/**
	 * Getter
	 * @return Actor's drawer
	 */
	public ActorDrawer getDrawer() {
		return drawer_;
	}
	
	/**
	 * Getter
	 * @return Actor's cinematic
	 */
	public Cinematic getCinematic() {
		return pos_;
	}
	
	/**
	 * Mutator
	 * @param cinematic 
	 */
	public void setCinematic(Cinematic cinematic) {
		pos_ = cinematic;
	}
	
	/**
	 * Checks interaction between actors (if one is dead, returns false)
	 * @param actor
	 * @return True if actors are alive and if their bounding boxes collide
	 */
	public boolean collidesWith(Actor actor, float ratio) {
		return !isToRemove() && !actor.isToRemove() && getCinematic().collidesWith(actor.getCinematic(), ratio);
	}
	
	// temporary
	public void checkCollision() {}
}
