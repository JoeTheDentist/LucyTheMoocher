package com.lucythemoocher.physics;

import java.util.ArrayList;

public class Box {
	private float x_;
	private float y_;
	private float h_;
	private float w_;
	private Cinematic cin_ = null;
	
	public Box(Box b) {
		x_ = b.x_;
		y_ = b.y_;
		h_ = b.h_;
		w_ = b.w_;
	}
	
	public Box(float x, float y, float h, float w) {
		x_ = x;
		y_ = y;
		h_ = h;
		w_ = w;
	}
	
	public Box(Cinematic cin, float h, float w) {
		cin_ = cin;
		h_ = h;
		w_ = w;
	}
	
	public void setCin(Cinematic cin) {
		cin_ = cin;
	}
	
	public float getX() {
		if ( cin_ == null ) {
			return x_;
		} else {
			return cin_.x();
		}
	}

	public void setX(float x_) {
		this.x_ = x_;
	}

	public float getY() {
		if ( cin_ == null ) {
			return y_;
		} else {
			return cin_.y();
		}
	}

	public void setY(float y_) {
		this.y_ = y_;
	}

	public float getH() {
		return h_;
	}

	public void setH(float h_) {
		this.h_ = h_;
	}

	public float getW() {
		return w_;
	}

	public void setW(float w_) {
		this.w_ = w_;
	}

	public boolean isIn(float x, float y) {
		if ( x < getX() || x > getX()+getW() ) {
			return false;
		}
		if ( y < getY() || y > getY()+getH() ) {
			return false;
		}
		return true;
	}
	
	public boolean collideWith(Box b) {
		boolean in = isIn(b.getX(),b.getY()) ||
			isIn(b.getX()+b.getW(),b.getY()) ||
			isIn(b.getX(),b.getY()+b.getH()) ||
			isIn(b.getX()+b.getW(),b.getY()+b.getH());
		
		in |= b.isIn(getX(),getY()) ||
			b.isIn(getX()+getW(),getY()) ||
			b.isIn(getX(),getY()+getH()) ||
			b.isIn(getX()+getW(),getY()+getH());
		
		return in;
	}
	
	public static boolean collideWith(Box a, Box b) {
		return a.collideWith(b);
	}
	
	public boolean collideWith(ArrayList<Box> boxes) {
		for ( Box b : boxes ) {
			if ( collideWith(b) ) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean collideWith(ArrayList<Box> boxes1, ArrayList<Box> boxes2) {
		for ( Box box : boxes1 ) {
			if ( box.collideWith(boxes2) ) {
				return true;
			}
		}
		return false;
	}
}
