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

	private Image menuBackground_;
	private MenuButton buttonNewGame_;
	private MenuButton buttonLeave_;
	
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
		
		buttonNewGame_.centerOn(Globals.getInstance().getCamera().w() / 2, 
				(Globals.getInstance().getCamera().h() / 100) *  NEWGAMEPERCENT_Y);
		buttonLeave_.centerOn(Globals.getInstance().getCamera().w() / 2, 
				(Globals.getInstance().getCamera().h() / 100) *  LEAVEPERCENT_Y);
	}

	@Override
	public void update() {}

	@Override
	public void render() {
		Globals.getInstance().getCamera().drawImageOnHud(0,  0, menuBackground_);
		buttonNewGame_.draw();
		buttonLeave_.draw();
		drawn_ = true;
	}

	@Override
	public boolean doRender() {
		return !drawn_;
	}
	
	/**
	 * When the user clicks
	 */
	public void onButtonClicked(int buttonIndex) {
		drawn_ = false;
		switch (buttonIndex) {
		case NEWGAMEINDEX:
			changeCurrentLoop(new LivesMenuLoop(1000, new LoopGame(Globals.getInstance().getLevel())));
			break;
		case LEAVEINDEX:
			Globals.getInstance().leave();
			break;
		}
	}

	/**
	 * Destroy loop
	 */
	public void onLeaveLoop() {
		super.onLeaveLoop();
		buttonNewGame_.destroy();
		buttonLeave_.destroy();
	}
}
