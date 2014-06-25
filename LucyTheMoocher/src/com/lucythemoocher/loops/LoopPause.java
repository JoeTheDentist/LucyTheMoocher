package com.lucythemoocher.loops;

import android.view.KeyEvent;

import com.lucythemoocher.R;
import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.controls.GlobalController;
import com.lucythemoocher.controls.KeysListener;
import com.lucythemoocher.gui.MenuButton;
import com.lucythemoocher.gui.MenuButtonListener;
import com.lucythemoocher.util.Timer;

public class LoopPause extends Loop implements MenuButtonListener, KeysListener  {
	private MenuButton buttonResume_;
	private MenuButton buttonLeaveGame_;
	private Timer lifeTime_;
	
	private final static int RESUMEINDEX = 1;
	private final static int LEAVEGAMEINDEX = 4;
	
	private final static float RESUMEPERCENT_Y = 20;
	private final static float LEAVEGAMEPERCENT_Y = 40;

	
	public LoopPause() {
		buttonResume_ = new MenuButton(30, 30, RESUMEINDEX, R.drawable.mainmenu_button_newgame_normal, 
				R.drawable.mainmenu_button_newgame_focussed, R.drawable.mainmenu_button_newgame_clicked, this);
		buttonLeaveGame_ = new MenuButton(30, 30, LEAVEGAMEINDEX, R.drawable.mainmenu_button_leave_normal, 
				R.drawable.mainmenu_button_leave_focussed, R.drawable.mainmenu_button_leave_clicked, this);
		registerKeys();
		lifeTime_ = new Timer(0);
	}
	
	public void update() {
		buttonResume_.centerOn(Globals.getInstance().getCamera().w() / 2, 
				(Globals.getInstance().getCamera().h() / 100) *  RESUMEPERCENT_Y);
		buttonLeaveGame_.centerOn(Globals.getInstance().getCamera().w() / 2, 
				(Globals.getInstance().getCamera().h() / 100) *  LEAVEGAMEPERCENT_Y);
	}


	public void render() {
		Globals.getInstance().getGame().render();
		buttonResume_.draw();
		buttonLeaveGame_.draw();
	}

	
	public void onButtonClicked(int buttonIndex) {
		switch (buttonIndex) {
		case RESUMEINDEX:
			changeCurrentLoop(new LoopGame());
			break;
		case LEAVEGAMEINDEX:
			changeCurrentLoop(new InitMenuLoop());
			break;
		}
		
	}
	
	public void onLeaveLoop() {
		buttonLeaveGame_.destroy();
		buttonResume_.destroy();
		unregisterKeys();
	}

	public void registerKeys() {
		GlobalController.getInstance().registerKey(this);
	}

	public void unregisterKeys() {
		GlobalController.getInstance().unregisterKey(this);
	}

	public void onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK && lifeTime_.timeFromCreation() > 100 &&  event.getRepeatCount() == 0) {
			changeCurrentLoop(new LoopGame());
		}
	}

	
}
