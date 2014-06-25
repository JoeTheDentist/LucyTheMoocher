package com.lucythemoocher.physics;

import java.util.ArrayList;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.util.MathUtil;

public class Cinematic {
	protected static final float GRAVITY = 0.004f;
	protected static final float MOVESPEED = 0.8f;
	protected static final float JUMPSPEED = 1.5f;
	protected static final float ATTACKSPEED = 2f;
	
	
	private float posx_;
	private float posy_;
	protected float speedx_;
	protected float speedy_;
	private float offsetx_;
	private float offsety_;
	private float normalSpeed_;
	
	private ArrayList<Box> boundingBoxes_;
	
	/**
	 * Constructor
	 */
	public Cinematic() {
		boundingBoxes_ = new ArrayList<Box>();
		normalSpeed_ = MOVESPEED;
		offsetx_ = 0.0f;
		offsety_ = 0.0f;
	}
	
	/**
	 * Constructor with default speed
	 * @param speed : default speed for walking
	 */
	public Cinematic(float speed) {
		boundingBoxes_ = new ArrayList<Box>();
		normalSpeed_ = speed;
		offsetx_ = 0.0f;
		offsety_ = 0.0f;
	}
	
	public Cinematic(Box box) {
		this();
		box.setCin(this);
		boundingBoxes_.add(box);
	}
	
	public Cinematic(float x, float y, float h, float w) {
		this();
		addBox(x, y, h, w);
	}
	
	public void addBox(float x, float y, float h, float w) {
		posx_ = x;
		posy_ = y;
		Box box = new Box(x, y, h, w);
		box.setCin(this);
		boundingBoxes_.add(box);
	}
	
	public void update() {
		updateNormalSpeed();
		updatePos();
	}
	

	private void updatePos() {
		float tempx = speedx() * Globals.getInstance().getGame().getDt() + offsetx_;
		float tempy = speedy() * Globals.getInstance().getGame().getDt() + offsety_;
		int movex = (int)tempx;
		int movey = (int)tempy;
		if (tempx > 0)
			offsetx_ = tempx - (float)Math.floor((double)tempx);
		else 
			offsetx_ = tempx - (float)Math.ceil((double)tempx); 

		for ( int i=0; i< Math.abs(movex) && !Globals.getInstance().getGame().getMap().hasCollision(boundingBoxes_) ; i++ ) {
			posx_ += MathUtil.sign(movex);
		}
		if ( Globals.getInstance().getGame().getMap().hasCollision(boundingBoxes_) ) {
			posx_ -= MathUtil.sign(movex);
			speedx_ *= 0;
		}
		for ( int i=0; i< (int)Math.abs(movey) && !Globals.getInstance().getGame().getMap().hasCollision(boundingBoxes_) ; i++ ) {
			posy_ += MathUtil.sign(movey);
		}
		if ( Globals.getInstance().getGame().getMap().hasCollision(boundingBoxes_) ) {
			posy_ -= MathUtil.sign(movey);
			speedy_ = 0;
		}
	}
	
	public float x() {
		return posx_ + offsetx_;
	}
	
	public float y() {
		return posy_ + offsety_;
	}
	
	public float speedx() {
		return speedx_;
	}
	
	public float speedy() {
		return speedy_;
	}
	
	public ArrayList<Box> boundingBoxes() {
		return boundingBoxes_;
	}

	public void moveStop() {
		speedx_ = 0;
	}
	
	public void stay() {
		speedx_ = 0;
		speedy_ = 0;
	}

	public void moveLeft() {
		speedx_ = -normalSpeed_;
	}

	public void moveRight() {
		speedx_ = normalSpeed_;
	}

	public void moveFastUp() {
		speedy_ = -JUMPSPEED;
	}
	
	public void moveUp() {
		speedy_ = -MOVESPEED;
	}
	
	public void moveDown() {
		speedy_ = ATTACKSPEED;
	}

	private void updateNormalSpeed() {
		speedy_ += GRAVITY * Globals.getInstance().getGame().getDt();
	}
	
	public boolean hasDownCollision() {
		return Globals.getInstance().getGame().getMap().hasDownCollision(boundingBoxes_);
	}
	
	public boolean hasLeftCollision() {
		return Globals.getInstance().getGame().getMap().hasLeftCollision(boundingBoxes_);
	}
	
	public boolean hasRightCollision() {
		return Globals.getInstance().getGame().getMap().hasRightCollision(boundingBoxes_);
	}
	
	/**
	 * Getter
	 * @param c 
	 * @return True if and only if the cinematic collides with c
	 */
	public boolean collidesWith(Cinematic c) {
		for (Box b1: this.boundingBoxes()) {
			for (Box b2: c.boundingBoxes()) {
				if (b1.collideWith(b2)) {
					return true;
				}
			}
		}
		return false;
	}
}
