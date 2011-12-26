package com.lucythemoocher.physics;

import java.util.ArrayList;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.util.MathUtil;

public class Cinematic {
	private static final float GRAVITY = 0.007f;
	private static final float MOVESPEED = 0.8f;
	private static final float JUMPSPEED = 2f;
	private static final float ATTACKSPEED = 3f;
	
	
	private float posx_;
	private float posy_;
	private float speedx_;
	private float speedy_;
	private float offsetx_;
	private float offsety_;
	
	private ArrayList<Box> boundingBoxes_;
	
	public Cinematic() {
		boundingBoxes_ = new ArrayList<Box>();
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
		posx_ = x;
		posy_ = y;
		Box box = new Box(x,y,h,w);
		box.setCin(this);
		boundingBoxes_.add(box);
	}
	
	public void update() {
		updateNormalSpeed();
		updatePos();
	}
	

	private void updatePos() {
		float tempx = speedx() * Game.getDt() + offsetx_;
		float tempy = speedy() * Game.getDt() + offsety_;
		int movex = (int)tempx;
		int movey = (int)tempy;
		if (tempx > 0)
			offsetx_ = tempx - (float)Math.floor((double)tempx);
		else 
			offsetx_ = tempx - (float)Math.ceil((double)tempx); 

		for ( int i=0; i< Math.abs(movex) && !Game.getMap().hasCollision(boundingBoxes_) ; i++ ) {
			posx_ += MathUtil.sign(movex);
		}
		if ( Game.getMap().hasCollision(boundingBoxes_) ) {
			posx_ -= MathUtil.sign(movex);
			speedx_ *= 0;
		}
		for ( int i=0; i< (int)Math.abs(movey) && !Game.getMap().hasCollision(boundingBoxes_) ; i++ ) {
			posy_ += MathUtil.sign(movey);
		}
		if ( Game.getMap().hasCollision(boundingBoxes_) ) {
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
		speedx_ = -MOVESPEED;
	}

	public void moveRight() {
		speedx_ = MOVESPEED;
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
		speedy_ += GRAVITY * Game.getDt();
	}
	
	public boolean hasDownCollision() {
		return Game.getMap().hasDownCollision(boundingBoxes_);
	}
	
	public boolean hasLeftCollision() {
		return Game.getMap().hasLeftCollision(boundingBoxes_);
	}
	
	public boolean hasRightCollision() {
		return Game.getMap().hasRightCollision(boundingBoxes_);
	}
}
