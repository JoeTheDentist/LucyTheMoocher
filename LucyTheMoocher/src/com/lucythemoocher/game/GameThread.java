package com.lucythemoocher.game;

import android.util.Log;

public class GameThread extends Thread {
	private static final String TAG = GameThread.class.getSimpleName();
	private boolean running = true;
	

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void run() {
		Log.d(TAG, "Starting game loop");
		
		final int FRAMES_PER_SECOND = 25;
	    final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

	    long nextGameTick = System.currentTimeMillis();

	    long sleepTime = 0;

	    while( running ) {
	        Game.update();
	        Game.getCam().refreshScreen();

	        nextGameTick += SKIP_TICKS;
	        sleepTime = nextGameTick - System.currentTimeMillis();
	        if( sleepTime >= 0 ) {
	            try {
					sleep( sleepTime );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	        else {
	        	//Log.d(TAG, "###SKIP");
	        }
	    }
	}
}
