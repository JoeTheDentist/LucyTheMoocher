package com.lucythemoocher.loops;

import android.graphics.Color;

import com.lucythemoocher.Globals.Globals;

public class CreditsLoop extends Loop {

	private float printPos_;
	private String[] credits_;
	private float textSpace_;
	private float textSpeed_;
	
	public CreditsLoop() {
		super();
		printPos_ = Globals.getInstance().getCamera().physicalH();
		credits_ = new String[]{
				"It was...",
				"",
				"... Lucy The Moocher",
				"",
				"I small video game, for a bit of fun.",
				"",
				"",
				"Here are some thanks...",
				"... because I don't have",
				"    much to put into these credits",
				"",
				"",
				"~~Special thanks to~~",
				"",
				"Super Gloupsy",
				"JunioR",
				"Mister Popop",
				"My",
				"Polymorf",
				""};
		Globals.getInstance().getGame().stop();
		textSpace_ = Globals.getInstance().getCamera().physicalH() / 6;
		textSpeed_ = textSpace_/500;
	}
	
	@Override
	public void update() {
		printPos_ -= textSpeed_ * Globals.getInstance().getTimer().getDt();
		if (printPos_ + credits_.length * textSpace_ < 0) { changeCurrentLoop(new InitMenuLoop()); }
	}

	@Override
	public void render() {
		Globals.getInstance().getCamera().drawFullColor(0xFF00FFCC);
		int i=0;
		for (String s : credits_) {
			Globals.getInstance().getCamera().drawText(s, Color.WHITE, printPos_+i);
			i += textSpace_;
		}
	}
}
