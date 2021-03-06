package com.lucythemoocher.sounds;

import com.lucythemoocher.R;

public class StateLevel1 extends SoundsState {

	public StateLevel1(SoundManager context, int mesuresToLive) {
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
		context_.playSound(R.raw.lvl1_1);
	}
	
	@Override
	void increaseNormal() {
		super.increaseNormal();
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateLevel2(context_, MTL_NORMAL));
		}
	}

	@Override
	void increaseQuick() {
		super.increaseQuick();
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateLevel2(context_, MTL_QUICK));
		}
	}

	@Override
	void decreaseNormal() {
		super.decreaseNormal();
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateNormal(context_, MTL_NORMAL));
		}
	}

	@Override
	void decreaseQuickly() {
		super.decreaseQuickly();
		if ( mesuresToLive_ <= 0 ) {
			changeState(new StateNormal(context_, MTL_NORMAL));
		}
	}

}
