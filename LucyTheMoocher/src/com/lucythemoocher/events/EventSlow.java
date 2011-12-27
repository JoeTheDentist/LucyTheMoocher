package com.lucythemoocher.events;

import com.lucythemoocher.game.Game;

public class EventSlow extends Event{
	public EventSlow() {
		speed_ = (float) 0.5;
		Game.setSpeed(speed_);
	}
}
