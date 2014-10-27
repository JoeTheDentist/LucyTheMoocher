		package com.lucythemoocher;

import com.lucythemoocher.game.GameThread;
import com.lucythemoocher.util.Resources;
import com.lucythemoocher.Globals.Globals;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class LucyTheMoocherActivity extends Activity {
	private static final String TAG = LucyTheMoocherActivity.class.getSimpleName();
	private static GameThread gameThread_;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
        					 LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
        Resources.setActivity(this);
        Globals.getInstance(); // call it once here to be sure the instance is created
        setContentView(Globals.getInstance().getCamera());
		gameThread_ = new GameThread();
		gameThread_.start();

    }
    
    public void onConfigurationChanged(Configuration newConfig) {
    	  super.onConfigurationChanged(newConfig);
    	  Log.d(TAG, "!!! Config changed...");
    }

    public void onBackPressed() {
    	//TODO must be treated (with menus for instance)
    	return;
    }
    
    protected void onStart() {
    	super.onStart();
    	Log.d(TAG, "!!! Starting...");
    }
    
    protected void onResume() {
    	super.onResume();
    	Log.d(TAG, "!!! Resuming...");
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	Log.d(TAG, "!!! Destroying...");
    	android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    protected void onStop() {
    	super.onStop();
    	Log.d(TAG, "!!! Stopping...");
		closure();
    }
    
    protected void onPause() {
    	super.onPause();
    	Log.d(TAG, "!!! Pausing...");
    	closure();
    }
    
    private void closure() {
    	Globals.getInstance().stopGame();
    	gameThread_.setRunning(false);
    	try {
			gameThread_.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	android.os.Process.killProcess(android.os.Process.myPid());
    }
}