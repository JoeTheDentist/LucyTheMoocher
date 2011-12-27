package com.lucythemoocher.game;

import android.util.Log;

import com.lucythemoocher.loops.MasterLoop;

public class GameThread extends Thread {
	private static final String TAG = GameThread.class.getSimpleName();
	private boolean running = true;

	private long beginTime_;
	private long timeDiff_;
	private int framesSkipped_;

	private static final int MAX_FPS = 50;
	private static final int MAX_FRAME_SKIPS = 5;
	private static final int FRAME_PERIOD = 1000 / MAX_FPS; 


	public void setRunning(boolean running) {
		this.running = running;
	}

	public void run() {
		Log.d(TAG, "Starting game loop");

		long sleepTime = 0;
		
		while( running ) {
			beginTime_ = System.currentTimeMillis();
			framesSkipped_ = 0;

			
			update();
			render();

			timeDiff_ = System.currentTimeMillis() - beginTime_;
			
			sleepTime = (int)(FRAME_PERIOD - timeDiff_);

			if (sleepTime > 0) {
				// if sleepTime > 0 we're OK
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {}
			}

			while (sleepTime < 0 && framesSkipped_ < MAX_FRAME_SKIPS) {
				update();
				sleepTime += FRAME_PERIOD;
				framesSkipped_++;
			}
			
		}
	}
	
	public void update() {
		MasterLoop.getInstance().update();
	}
	
	public void render() {
		MasterLoop.getInstance().render();
	}
}
