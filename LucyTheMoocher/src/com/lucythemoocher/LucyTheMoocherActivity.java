package com.lucythemoocher;

import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.util.Ressources;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class LucyTheMoocherActivity extends Activity {
	private static final String TAG = LucyTheMoocherActivity.class.getSimpleName();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(	WindowManager.LayoutParams.FLAG_FULLSCREEN,
        						WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Ressources.setActivity(this);
        Camera cam = new Camera(this, getWindowManager().getDefaultDisplay().getHeight(), 
        		getWindowManager().getDefaultDisplay().getWidth());
        setContentView(cam);
        Game.launchGame(cam);
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
    	Game.stop();
    	Log.d(TAG, "!!! Stopping...");
    }
    
    protected void onPause() {
    	super.onPause();
    	Log.d(TAG, "!!! Pausing...");
    }

}