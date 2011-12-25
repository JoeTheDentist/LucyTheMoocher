package com.lucythemoocher.util;

import java.io.InputStream;

import android.app.Activity;

public class Resources {
	private static Activity Activity_;
	
	public static void setActivity(Activity a) {
		Activity_ = a;
	}
	
	public static InputStream openRawRessources(int res) {
		return Activity_.getResources().openRawResource(res);
	}
	
	public static Activity getActivity() {
		return Activity_;
	}
}
