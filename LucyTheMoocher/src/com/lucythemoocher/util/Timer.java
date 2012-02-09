package com.lucythemoocher.util;


/**
 * Time and time elapsed between two last updates
 * Can be slowed or accelerated by a factor with setFactor
 * Unity is millisecond
 * You have two ways to update a timer:
 * - call addDt(float) to update Dt with a parameter you choose
 * - call update() and addDt will be called with Dt = current system time - system time in last update
 * Don't use both functions since update calls addDt
 */
public class Timer {
	float currentTime_;
	float dt_;
	float factor_;
	long previousSystemTime_;
	float initTime_;
	
	
	/**
	 * Constructor
	 * @param time in ms
	 */
	public Timer(float time) {
		dt_ = 0.0000001f; // avoid null divisions
		currentTime_ = time;
		factor_ = 1.0f; // no factor by default
		previousSystemTime_ = System.currentTimeMillis();
		initTime_ = currentTime_;
	}
	
	/**
	 * Update dt and the current time with the system clock.
	 * Don't call it if you also use addDt().
	 */
	public void update() {
		long current = System.currentTimeMillis();
		addDt(current - previousSystemTime_);
		previousSystemTime_ = current;
	}
	
	/**
	 * Update time. 
	 * Don't call it if you use update().
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
	
	/**
	 * Update the timer and return the time from it's creation
	 * @return time in ms
	 */
	public float timeFromCreation() {
		update();
		return currentTime_ - initTime_;
	}
}
