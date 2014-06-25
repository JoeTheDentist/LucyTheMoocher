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

public class LucyTheMoocherActivity extends Activity {
	private static final String TAG = LucyTheMoocherActivity.class.getSimpleName();
	private static GameThread gameThread_;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(	WindowManager.LayoutParams.FLAG_FULLSCREEN,
        						WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
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
    }
    
    protected void onStop() {
    	super.onStop();
		gameThread_.setRunning(false);
    	Log.d(TAG, "!!! Stopping...");
    }
    
    protected void onPause() {
    	super.onPause();
    	Globals.getInstance().leave(); // TODO: only FOR DEVELOPMENT
    	Log.d(TAG, "!!! Pausing...");
    }
    

}