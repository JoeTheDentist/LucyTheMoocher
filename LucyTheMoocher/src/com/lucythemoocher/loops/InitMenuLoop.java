package com.lucythemoocher.loops;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Image;
import com.lucythemoocher.gui.MenuButton;
import com.lucythemoocher.gui.MenuButtonListener;
import com.lucythemoocher.util.Timer;

import com.lucythemoocher.R;

/**
 * Loop used for the menu
 */
public class InitMenuLoop extends Loop implements MenuButtonListener {

	Timer timer_;
	Image menuBackground_;
	MenuButton buttonTest_;
	
	private final static int NEWGAMEINDEX = 1;
	
	/**
	 * Public constructor
	 */
	public InitMenuLoop() {
		timer_ = new Timer(0);
		menuBackground_ = new Image(R.drawable.menu_background);
		buttonTest_ = new MenuButton(30, 30, NEWGAMEINDEX, R.drawable.mainmenu_button_newgame_normal, 
				R.drawable.mainmenu_button_newgame_focussed, R.drawable.mainmenu_button_newgame_clicked, this);
	}

	/**
	 * Called each frame if this is the current loop
	 * Implement here every computation
	 */
	public void update() {
	}

	/**
	 * Called for each rendering if this is the current loop
	 * Don't compute anything here, prefer update
	 */
	public void render() {
		Globals.getInstance().getCamera().drawImage(0,  0, menuBackground_);
		buttonTest_.draw();
	}

	@Override
	public void onButtonClicked(int buttonIndex) {
		switch (buttonIndex) {
		case NEWGAMEINDEX:
			changeCurrentLoop(new LoopGame());
			break;
		
		}
		
	}

}
