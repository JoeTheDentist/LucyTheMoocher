package com.lucythemoocher.sounds;

public abstract class SoundsState {

	static final int MTL_NORMAL = 4;
	static final int MTL_QUICK = 2;
	
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
	void increaseNormal() {
		if ( mesuresToLive_ > MTL_NORMAL ) {
			mesuresToLive_ = MTL_NORMAL;
		}
	}
	
	/**
	 * When the ambiance is quickly going more and more active
	 */
	void increaseQuick() {
		if ( mesuresToLive_ > MTL_QUICK ) {
			mesuresToLive_ = MTL_QUICK;
		}
	}
	
	/**
	 * When the ambiance is slowly going less and less active
	 */
	void decreaseNormal() {
		if ( mesuresToLive_ > MTL_NORMAL ) {
			mesuresToLive_ = MTL_NORMAL;
		}
	}
	
	/**
	 * When the ambiance is quickly going less and less active
	 */
	void decreaseQuickly() {
		if ( mesuresToLive_ > MTL_QUICK ) {
			mesuresToLive_ = MTL_QUICK;
		}
	}
}
