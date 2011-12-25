package com.lucythemoocher.util;

/**
 * Time and time elapsed between two last updates
 * Can be slowed or accelerated by a factor with setFactor
 * Unity is millisecond
 */
public class Timer {
	float currentTime_;
	float dt_;
	float factor_;
	
	/**
	 * Constructor
	 * @param time in ms
	 */
	public Timer(float time) {
		dt_ = 0.0000001f; // avoid null divisions
		currentTime_ = time;
		factor_ = 1.0f; // no factor by default
	}
	
	
	/**
	 * Update time
	 * @param dt Time elapsed in ms
	 */
	public void addDt(float dt) {
		dt_ = dt * factor_;
		currentTime_ += dt_;
	}
	
	/**
	 * Getter
	 * @return current time
	 */
	public float getTime() {
		return currentTime_;
	}
	
	/**
	 * Getter
	 * @return Time between the two last setTime(float)
	 * @see Timer#setTime(float)
	 */
	public float getDt() {
		return dt_;
	}
	
	/**
	 * Mutator
	 * @param factor
	 */
	public void setFactor(float factor) {
		factor_ = factor;
	}
}
