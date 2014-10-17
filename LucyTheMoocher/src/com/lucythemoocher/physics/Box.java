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
	
	public boolean collideWith(Box b, float ratio) {
		return (getX() + (1-ratio)*getW() < b.getX() + ratio*b.getW() &&
				getX() + ratio*getW() > b.getX() + (1-ratio)*b.getW() &&
				getY() + (1-ratio)*getH() < b.getY() + ratio*b.getH() &&
				getY() + ratio*getH() > b.getY() + (1-ratio)*b.getH());
	}
	
	public static boolean collideWith(Box a, Box b, float ratio) {
		return a.collideWith(b, ratio);
	}
	
	public boolean collideWith(ArrayList<Box> boxes, float ratio) {
		for ( Box b : boxes ) {
			if ( collideWith(b, ratio) ) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean collideWith(ArrayList<Box> boxes1, ArrayList<Box> boxes2, float ratio) {
		for ( Box box : boxes1 ) {
			if ( box.collideWith(boxes2, ratio) ) {
				return true;
			}
		}
		return false;
	}
}
