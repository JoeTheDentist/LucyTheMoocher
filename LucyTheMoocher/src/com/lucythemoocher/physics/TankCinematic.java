package com.lucythemoocher.physics;

public class TankCinematic extends Cinematic {
	
	public TankCinematic() {
		super();
	}
	
	public TankCinematic(Box box) {
		super(box);
	}
	
	public TankCinematic(float x, float y, float h, float w) {
		super(x,y,h,w);
	}
	
	public void moveLeft() {
		speedx_ = -MOVESPEED/2;
	}

	public void moveRight() {
		speedx_ = MOVESPEED/2;
	}
}
