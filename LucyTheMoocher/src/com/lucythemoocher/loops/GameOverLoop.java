package com.lucythemoocher.loops;

import android.graphics.Color;

import com.lucythemoocher.Globals.Globals;

public class GameOverLoop extends Loop {

	private float currMs_;
	
	/**
	 * Show "lives" lives for "msToLive" ms and then change to "next" loop
	 * @param lives
	 * @param msToLive
	 * @param next
	 */
	public GameOverLoop(float msToLive) {
		super();
		currMs_ = msToLive;
		Globals.getInstance().getGame().stop();
	}
	
	@Override
	public void update() {
		currMs_ -= Globals.getInstance().getTimer().getDt();
		if (currMs_ <= 0) {
			changeCurrentLoop(new InitMenuLoop());
		}
	}

	@Override
	public void render() {
		Globals.getInstance().getCamera().drawFullColor(0xFF00FFCC);
		Globals.getInstance().getCamera().drawCenterText("GAME OVER...", Color.WHITE);
		drawn_ = true;
	}
	
	@Override
	public boolean doRender() {
		return !drawn_;
	}

}
