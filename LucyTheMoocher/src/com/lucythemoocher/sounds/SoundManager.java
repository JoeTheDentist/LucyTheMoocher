package com.lucythemoocher.sounds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.lucythemoocher.R;
import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.util.Resources;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Class aimed to play sounds\n
 * Warning : I use SoundPool to play the music, this is bad because SoundPool
 * uses audio stream uncompressed in memory, but this is the only way to have the power
 * to choice the speed of the sounds.\n
 * N.B. the music samples must have the exact same duration !
 */
public class SoundManager {

	private static final int BACKGROUND_THEME = 0;
	private static final int BACKGROUND_LVL1 = 1;
	private static final int BACKGROUND_LVL2 = 2;
	private static final int BACKGROUND_LVL3 = 3;

	private SoundPool sounds_ = null;
	private HashMap<Integer, Integer> loaded_ = null;
	private HashMap<Integer, Integer> playing_ = null;
	private Vector<ArrayList<Integer>> soundsBackground_;
	private int sampleDuration_;
	private float lastPlay_;
	private boolean started_;
	private SoundsState state_;
	private MediaPlayer player_;
	
	public SoundManager() {
		player_ = MediaPlayer.create(Resources.getActivity(), R.raw.theme_loop);
	}

	/**
	 * Update
	 */
	public void update() {}
	
	/**
	 * Load sound resources
	 */
	public void load() {}

	/**
	 * Start playing
	 */
	public void start() {
		player_.start();
		player_.setLooping(true);
	}
	
	/**
	 * Stop playing and rewind
	 */
	public void stop() {
		player_.pause();
		player_.seekTo(0);
	}
	
	/**
	 * Pause
	 */
	public void pause() {
		player_.pause();
	}
	
	/**
	 * Resume
	 */
	public void resume() {
		player_.start();
	}

	/**
	 * Play sound from resource id
	 * @param resource
	 */
	void playSound(int resource) {
		AudioManager mgr = (AudioManager)Resources.getActivity().getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		//TODO last argument should be the game speed
		playing_.put(resource, sounds_.play(loaded_.get(resource), volume, volume, 1, 0, 1f));
	}
	
	/**
	 * Change state
	 * @param newState
	 */
	void changeState(SoundsState newState) {
		state_ = newState;
	}
}
