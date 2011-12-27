package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public abstract class State {
	protected Cinematic pos_;
	protected Animation anim_;
	protected PlayerCharacter pc_;
	protected final static float ANIMATION_SPEED = 100;
	protected final static int WALL_WALKING_TIME = 500;
	protected final static int WALL_WALKING_PAUSE = 400;

	
	public State(PlayerCharacter pc, Cinematic pos, Animation anim) {
		pos_ = pos;
		anim_ = anim;
		pc_ = pc;
	}
	
	public void update() {
		pos_.update();
	}
	
	public void moveUp() {}
	
	public void moveDown() {}
	
	public void moveLeft() {
		pos_.moveLeft();
	}
	
	public void moveRight() {
		pos_.moveRight();
	}
	
	public void moveStop() {}

	public void moveFastLeft() {
		moveLeft();
	}
	
	public void moveFastRight() {
		moveRight();
	}
}
