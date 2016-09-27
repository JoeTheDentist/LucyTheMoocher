package com.lucythemoocher.util;

public class MathUtil {
	public static float sign(float x) {
		if ( x >= 0 ) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static int uniform(int begin, int end) {
		return (int)((end-begin+1)*Math.random()+begin);
	}
}
