package com.lucythemoocher.physics;

import java.util.ArrayList;

import com.lucythemoocher.game.Game;
import com.lucythemoocher.util.MathUtil;

import android.util.Log;


public class Cinematic {
	private static final float GRAVITY = 6;
	private static final float MOVESPEED = 25;
	private static final float JUMPSPEED = 54;
	private static final float ATTACKSPEED = 130;
	
	private static final int LEFT = -1;
	private static final int RIGHT = 1;
	private static final int UP = 1;
	private static final int DOWN = -1;
	
	private static float DT_ = 1;
	
	private float posx_;
	private float posy_;
	private float speedx_;
	private float speedy_;
	private float dt_ = 1;
	
	private ArrayList<Box> boundingBoxes_;
	
	public Cinematic() {
		boundingBoxes_ = new ArrayList<Box>();
	}
	
	public Cinematic(Box box) {
		boundingBoxes_ = new ArrayList<Box>();
		box.setCin(this);
		boundingBoxes_.add(box);
	}
	
	public Cinematic(float x, float y, float h, float w) {
		posx_ = x;
		posy_ = y;
		Box box = new Box(x,y,h,w);
		boundingBoxes_ = new ArrayList<Box>();
		box.setCin(this);
		boundingBoxes_.add(box);
	}
	
	public void update() {
		updateNormalSpeed();
		updatePos();
	}
	
	public static void setGeneralSpeed(float dt) {
		DT_ = dt;
	}
	
	public void setLocalSpeed(float dt) {
		dt_ = dt;
	}
	
	private void updateNormalSpeed() {
		speedy_ += GRAVITY*dt_*DT_;
	}
	
	private void updatePos() {
		int collision = 0;
		if ( speedy_ > 0 ) { //collision down
			collision = 1;
		} else { //collision up
			collision = 2;
		}
		for ( int i=0; i<Math.abs(speedx()) && !Game.getMap().hasCollision(boundingBoxes_) ; i++ ) {
			posx_ += MathUtil.sign(speedx());
		}
		if ( Game.getMap().hasCollision(boundingBoxes_) ) {
			posx_ -= MathUtil.sign(speedx());
			speedx_ *= 0;
		}
		for ( int i=0; i<Math.abs(speedy()) && !Game.getMap().hasCollision(boundingBoxes_) ; i++ ) {
			posy_ += MathUtil.sign(speedy());
		}
		if ( Game.getMap().hasCollision(boundingBoxes_) ) {
			posy_ -= MathUtil.sign(speedy());
			if ( collision == 1 ) { //down
				speedy_ = 0;
			} else { //up
				speedy_ *= -0.5;
			}
		}
	}
	
	public float x() {
		return posx_;
	}
	
	public float y() {
		return posy_;
	}
	
	public float speedx() {
		return speedx_*dt_*DT_;
	}
	
	public float speedy() {
		return speedy_*dt_*DT_;
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
}
