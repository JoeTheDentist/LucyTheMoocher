package com.lucythemoocher.events;

import com.lucythemoocher.Globals.Globals;

public class EventSlow extends Event{
	public EventSlow() {
		speed_ = (float) 0.5;
		Globals.getInstance().getGame().setSpeed(speed_);
	}
}
