package com.lucythemoocher.graphics;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.game.Game;

public class Animation {
	private Grid grid_;
	private int tab_[];
	private int nbCycles_;
	private int curr_;
	
	public Animation(int ressource, int picH, int picW) {
		grid_ = new Grid(ressource, picH, picW);
		int t[] = {0};
		setAnimation(t, 1);
	}
	
	public void setAnimation(int tab[], int nbCycles) {
		tab_ = new int[tab.length];
		for (int i=0; i<tab.length; i++) {
			tab_[i] = tab[i];
		}
		nbCycles_ = nbCycles;
		curr_ = 0;
	}
	
	public float getH() {
		return grid_.getImage(0).h();
	}
	
	public float getW() {
		return grid_.getImage(0).w();
	}
	
	public void draw(float x, float y) {
		if ( Game.getTime()%nbCycles_ == 0) {
			curr_++;
			curr_ %= tab_.length;
		}
		Globals.getInstance().getCamera().drawImage(x, y, grid_.getImage(tab_[curr_]));
	}
}
