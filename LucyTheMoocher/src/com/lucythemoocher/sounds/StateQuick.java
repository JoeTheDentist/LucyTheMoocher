package com.lucythemoocher.sounds;

import com.lucythemoocher.R;

public class StateQuick extends SoundsState {

	public StateQuick(SoundManager context, int mesuresToLive) {
		super(context, mesuresToLive);
	}

	@Override
	void changeMesure() {
		super.changeMesure();
		if ( mesures_%2 == 1 ) {
			context_.playSound(R.raw.theme1);
		} else {
			context_.playSound(R.raw.theme2);
		}
		context_.playSound(R.raw.lvl3_1);
	}
	
	@Override
	void increaseNormal() {
		if ( mesuresToLive_ <= 0 ) {
			mesuresToLive_ = 0;
		}
	}

	@Override
	void increaseQuick() {
		if ( mesuresToLive_ <= 0 ) {
			mesuresToLive_ = 0;
		}
	}

	@Override
	void decreaseNormal() {
		if ( mesuresToLive_ > MTL_NORMAL ) {
			mesuresToLive_ = MTL_NORMAL;
		}
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateNormal(context_, MTL_NORMAL));
		}
	}

	@Override
	void decreaseQuickly() {
		if ( mesuresToLive_ > MTL_QUICK ) {
			mesuresToLive_ = MTL_QUICK;
		}
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateNormal(context_, MTL_NORMAL));
		}
	}

}
