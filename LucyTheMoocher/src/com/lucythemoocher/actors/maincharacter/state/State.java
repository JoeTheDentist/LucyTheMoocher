package com.lucythemoocher.actors.maincharacter.state;

import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Animation;
import com.lucythemoocher.physics.Cinematic;

public abstract class State {
	protected Cinematic pos_;
	protected Animation anim_;
	protected PlayerCharacter pc_;
	protected final static int ANIMATION_NORMAL_SPEED = 3;
	protected static int ANIMATION_SPEED = ANIMATION_NORMAL_SPEED;
	protected final static int WALL_WALKING_DISTANCE = 300;
	protected final static int WALL_WALKING_PAUSE = 7;
	
	public static void changeAnimationSpeed(float speed) {
		ANIMATION_SPEED = (int)((float) ANIMATION_NORMAL_SPEED/speed);
	}
	
	public State(PlayerCharacter pc, Cinematic pos, Animation anim) {
		pos_ = pos;
		anim_ = anim;
		pc_ = pc;
	}
	
	public void update() {
		pos_.update();
	}
	
	abstract public void moveUp();
	
	abstract public void moveDown();
	
	abstract public void moveLeft();
	
	abstract public void moveRight();
	
	abstract public void moveStop();

	abstract public void moveFastLeft();
	
	abstract public void moveFastRight();
}
