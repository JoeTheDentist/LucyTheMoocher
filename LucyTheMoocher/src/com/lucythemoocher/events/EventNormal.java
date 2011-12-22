package com.lucythemoocher.events;

import com.lucythemoocher.game.Game;

public class EventNormal extends Event{

	public EventNormal() {
		speed_ = (float) 0.75;
		Game.setSpeed(speed_);
		Game.getCam().setNormalDrawing();
	}
}
