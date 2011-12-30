package com.lucythemoocher.sounds;

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
import android.util.Log;

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
	
	public SoundManager() {
		sounds_ = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		loaded_ = new HashMap<Integer, Integer>();
		playing_ = new HashMap<Integer, Integer>();
		soundsBackground_ = new Vector<ArrayList<Integer>>(4);
		started_ = false;

		//Calculate the sample duration
		MediaPlayer mp = MediaPlayer.create(Resources.getActivity(), R.raw.theme1);
		sampleDuration_ = mp.getDuration();

		//Allocation
		for ( int i = 0; i<soundsBackground_.capacity(); i++) {
			ArrayList<Integer> l = new ArrayList<Integer>();
			soundsBackground_.add(i, l);
		}

		//Chemins des fichiers de son
		soundsBackground_.get(BACKGROUND_THEME).add(R.raw.theme1);
		soundsBackground_.get(BACKGROUND_THEME).add(R.raw.theme2);
		soundsBackground_.get(BACKGROUND_LVL3).add(R.raw.lvl1_1);
		soundsBackground_.get(BACKGROUND_LVL3).add(R.raw.lvl2_1);
		soundsBackground_.get(BACKGROUND_LVL3).add(R.raw.lvl3_1);

		//Ici on charge les sons
		for ( ArrayList<Integer> l : soundsBackground_ ) {
			for ( int i : l ) {
				addSound(i);
			}
		}
		
		state_ = new StateNormal(this, SoundsState.MTL_NORMAL);
	}

	public void update() {
		state_.increaseNormal();
		if ( started_ ) {
			if ( Globals.getInstance().getTimer().getTime()-lastPlay_ > sampleDuration_- 50 ) {
				lastPlay_ = Globals.getInstance().getTimer().getTime();
				state_.changeMesure();
			}
		}
	}

	public void start() {
		lastPlay_ = -sampleDuration_;
		started_ = true;
	}

	private void addSound(int resource) {
		loaded_.put(resource, sounds_.load(Resources.getActivity(), resource, 1));
	}

	void playSound(int resource) {
		AudioManager mgr = (AudioManager)Resources.getActivity().getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		//TODO dernier arg => vitesse du jeu
		playing_.put(resource, sounds_.play(loaded_.get(resource), volume, volume, 1, 0, 1f));
	}
	
	void changeState(SoundsState newState) {
		state_ = newState;
	}
}
