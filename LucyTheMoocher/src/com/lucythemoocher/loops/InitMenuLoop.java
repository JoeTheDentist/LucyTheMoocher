package com.lucythemoocher.loops;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Image;
import com.lucythemoocher.gui.MenuButton;
import com.lucythemoocher.gui.MenuButtonListener;
import com.lucythemoocher.R;

/**
 * Loop used for the menu
 */
public class InitMenuLoop extends Loop implements MenuButtonListener {

	Image menuBackground_;
	MenuButton buttonNewGame_;
	MenuButton buttonSound_;
	MenuButton buttonMusic_;
	MenuButton buttonLeave_;
	
	private final static int NEWGAMEINDEX = 1;
	private final static int LEAVEINDEX = 2;
	
	private final static float NEWGAMEPERCENT_Y = 20;
	private final static float LEAVEPERCENT_Y = 80;
	
	
	/**
	 * Public constructor
	 */
	public InitMenuLoop() {
		menuBackground_ = new Image(R.drawable.menu_background);
		buttonNewGame_ = new MenuButton(30, 30, NEWGAMEINDEX, R.drawable.mainmenu_button_newgame_normal, 
				R.drawable.mainmenu_button_newgame_focussed, R.drawable.mainmenu_button_newgame_clicked, this);
		buttonLeave_ = new MenuButton(30, 30, LEAVEINDEX, R.drawable.mainmenu_button_leave_normal, 
				R.drawable.mainmenu_button_leave_focussed, R.drawable.mainmenu_button_leave_clicked, this);
	}

	/**
	 * Called each frame if this is the current loop
	 * Implement here every computation
	 */
	public void update() {
		buttonNewGame_.centerOn(Globals.getInstance().getCamera().w() / 2, 
				(Globals.getInstance().getCamera().h() / 100) *  NEWGAMEPERCENT_Y);
		buttonLeave_.centerOn(Globals.getInstance().getCamera().w() / 2, 
				(Globals.getInstance().getCamera().h() / 100) *  LEAVEPERCENT_Y);
	}

	/**
	 * Called for each rendering if this is the current loop
	 * Don't compute anything here, prefer update
	 */
	public void render() {
		Globals.getInstance().getCamera().drawImageOnHud(0,  0, menuBackground_, true);
		buttonNewGame_.draw();
		buttonLeave_.draw();
	}


	public void onButtonClicked(int buttonIndex) {
		switch (buttonIndex) {
		case NEWGAMEINDEX:
			// TODO variable for lives (instead of 3)
			changeCurrentLoop(new LivesMenuLoop(3, 1000, new LoopGame(1)));
			break;
		case LEAVEINDEX:
			Globals.getInstance().leave();
			break;
		
		}
		
	}

	public void onLeaveLoop() {
		super.onLeaveLoop();
		buttonNewGame_.destroy();
		buttonLeave_.destroy();
	}
}
