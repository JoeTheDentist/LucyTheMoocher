package com.lucythemoocher.sounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.lucythemoocher.R;
import com.lucythemoocher.util.Resources;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	
	private static final int BACKGROUND_THEME = 0;
	private static final int BACKGROUND_LVL1 = 1;
	private static final int BACKGROUND_LVL2 = 2;
	private static final int BACKGROUND_LVL3 = 3;
	
	private SoundPool sounds_ = null;
	private HashMap<Integer, Integer> loaded_ = null;
	private HashMap<Integer, Integer> playing_ = null;
	private Vector<ArrayList<Integer>> soundsBackground_;
	
	public SoundManager() {
		sounds_ = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
		loaded_ = new HashMap<Integer, Integer>();
		playing_ = new HashMap<Integer, Integer>();
		soundsBackground_ = new Vector<ArrayList<Integer>>(4);
		
		//Allocation
		for ( int i = 0; i<soundsBackground_.capacity(); i++) {
			ArrayList<Integer> l = new ArrayList<Integer>();
			soundsBackground_.add(i, l);
		}
		
		//Chemins des fichiers de son
		soundsBackground_.get(BACKGROUND_THEME).add(R.raw.firstbeat);
		soundsBackground_.get(BACKGROUND_THEME).add(R.raw.firstbeat2);
		
		//Ici on charge les sons
		for ( ArrayList<Integer> l : soundsBackground_ ) {
			for ( int i : l ) {
				addSound(i);
			}
		}
	}
	
	private void addSound(int resource) {
		loaded_.put(resource, sounds_.load(Resources.getActivity(), resource, 1));
	}
	
	private void playSound(int resource) {
		AudioManager mgr = (AudioManager)Resources.getActivity().getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		//TODO dernier arg => vitesse du jeu
		playing_.put(resource, sounds_.play(loaded_.get(resource), volume, volume, 1, 0, 1f));
	}
	
	public void test() {
		playSound(R.raw.firstbeat);
	}
}
