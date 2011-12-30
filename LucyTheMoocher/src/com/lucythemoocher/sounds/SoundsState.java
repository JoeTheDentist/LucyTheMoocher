package com.lucythemoocher.sounds;

public abstract class SoundsState {

	protected static final int MTL_NORMAL = 2;
	protected static final int MTL_QUICK = 1;
	
	protected SoundManager context_;
	protected int mesuresToLive_;
	protected int mesures_;
	
	public SoundsState(SoundManager context, int mesuresToLive) {
		context_ = context;
		mesuresToLive_ = mesuresToLive;
		mesures_ = 0;
	}
	
	protected void changeState(SoundsState newState) {
		context_.changeState(newState);
	}
	
	void changeMesure() {
		mesures_++;
		mesuresToLive_--;
	}
	
	/**
	 * When the ambiance is slowly going more and more active
	 */
	abstract void increaseNormal();
	
	/**
	 * When the ambiance is quickly going more and more active
	 */
	abstract void increaseQuick();
	
	/**
	 * When the ambiance is slowly going less and less active
	 */
	abstract void decreaseNormal();
	
	/**
	 * When the ambiance is quickly going less and less active
	 */
	abstract void decreaseQuickly();
}
