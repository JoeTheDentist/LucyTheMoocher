package com.lucythemoocher.sounds;

import com.lucythemoocher.R;

public class StateNormal extends SoundsState {

	public StateNormal(SoundManager context, int mesuresToLive) {
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
	}

	@Override
	void increaseNormal() {
		super.increaseNormal();
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateLevel1(context_, MTL_NORMAL));
		}
	}

	@Override
	void increaseQuick() {
		super.increaseQuick();
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateLevel1(context_, MTL_QUICK));
		}
	}

	@Override
	void decreaseNormal() {
		super.decreaseNormal();
	}

	@Override
	void decreaseQuickly() {
		super.decreaseQuickly();
	}

}
