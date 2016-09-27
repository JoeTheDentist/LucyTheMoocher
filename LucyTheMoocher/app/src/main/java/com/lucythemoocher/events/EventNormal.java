package com.lucythemoocher.events;

import com.lucythemoocher.Globals.Globals;

public class EventNormal extends Event{

	public EventNormal() {
		speed_ = (float) 0.75;
		Globals.getInstance().getGame().setSpeed(speed_);
	}
}
