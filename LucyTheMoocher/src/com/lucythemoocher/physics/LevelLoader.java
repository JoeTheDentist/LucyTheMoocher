package com.lucythemoocher.physics;

import java.io.InputStream;
import java.util.Scanner;


import com.lucythemoocher.util.Resources;

public class LevelLoader {
	/**
	 * Constructor
	 * Generate a map according to the raw file
	 * It is supposed that the thickness is more than 1
	 * @param mapName
	 */
	public LevelLoader(int mapName) {
		InputStream inputStream = Resources.openRawRessources(mapName);
		Scanner s = new Scanner(inputStream);
		
		//dimensions
		String cur = s.next();
		h_ = Integer.parseInt(cur) + 4;
		cur = s.next();
		w_ = Integer.parseInt(cur) + 4;
		
		//load map
		map_ = new int[h_][w_];
		//set contour
		for (int i=0; i<h_; i++) {
			map_[i][0] = 1;
			map_[i][1] = 1;
			map_[i][w_-2] = 1;
			map_[i][w_-1] = 1;
		}
		for (int j=0; j<w_; j++) {
			map_[0][j] = 1;
			map_[1][j] = 1;
			map_[h_-2][j] = 1;
			map_[h_-1][j] = 1;
		}
		//set level
		for (int i=2; i<h_-2; i++) {
			for (int j=2; j<w_-2; j++) {
				cur = s.next();
				map_[i][j] = Integer.parseInt(cur);
			}
		}
		
		//chose good pic !
		//note: it is supposed that thickness is more than 1
		for (int i=0; i<h_; i++) {
			for (int j=0; j<w_; j++) {
				if ( map_[i][j] != 0 ) {
					int pic = 0;
					if ( i==0 ) { //top
						if ( j==0 ) { //left corner
							pic = 1;
						} else if ( j==w_-1 ) { //right corner
							pic = 3;
						} else { //middle
							if ( map_[i][j-1] != 0 && map_[i][j+1] != 0 ) {
								pic = 2;
							} else if ( map_[i][j-1] != 0 ) {
								pic = 3;
							} else if ( map_[i][j+1] != 0 ) {
								pic = 1;
							}
						}
					} else if ( i==h_-1 ) { //bottom
						if ( j==0 ) { //left corner
							pic = 7;
						} else if ( j==w_-1 ) { //right corner
							pic = 9;
						} else { //middle
							if ( map_[i][j-1] != 0 && map_[i][j+1] != 0 ) {
								pic = 8;
							} else if ( map_[i][j-1] != 0 ) {
								pic = 9;
							} else if ( map_[i][j+1] != 0 ) {
								pic = 7;
							}
						}
					} else { //middle
						if ( j==0 ) { //left colomn
							if ( map_[i-1][j] != 0 && map_[i+1][j] != 0 ) {
								pic = 4;
							} else if ( map_[i-1][j] != 0 ) {
								pic = 7;
							} else if ( map_[i+1][j] != 0) {
								pic = 1;
							}
						} else if ( j==w_-1 ) { //right colomn
							if ( map_[i-1][j] != 0 && map_[i+1][j] != 0 ) {
								pic = 6;
							} else if ( map_[i-1][j] != 0 ) {
								pic = 9;
							} else if ( map_[i+1][j] != 0) {
								pic = 3;
							}
						} else { //middle
							if ( map_[i-1][j] != 0 && map_[i+1][j] != 0 &&
									map_[i][j-1] != 0 && map_[i][j+1] != 0 ) { //if there is a hole
								if ( map_[i-1][j-1] == 0 ) {
									pic = 18;
								} else if ( map_[i-1][j+1] == 0 ) {
									pic = 16;
								} else if ( map_[i+1][j-1] == 0 ) {
									pic = 12;
								} else if ( map_[i+1][j+1] == 0 ) {
									pic = 10;
								} else {
									pic = 5;
								}
							} else if ( map_[i-1][j] != 0 && map_[i+1][j] != 0 &&
									map_[i][j-1] != 0 ) {
								pic = 6;
							} else if ( map_[i-1][j] != 0 && map_[i+1][j] != 0 &&
									map_[i][j+1] != 0 ) {
								pic = 4;
							} else if ( map_[i-1][j] != 0 &&
									map_[i][j-1] != 0 && map_[i][j+1] != 0 ) {
								pic = 8;
							} else if ( map_[i+1][j] != 0 &&
									map_[i][j-1] != 0 && map_[i][j+1] != 0 ) {
								pic = 2;
							} else if ( map_[i-1][j] != 0 && map_[i][j-1] != 0 ) {
								pic = 9;
							} else if ( map_[i][j-1] != 0 && map_[i+1][j] != 0 ) {
								pic = 3;
							} else if ( map_[i+1][j] != 0 && map_[i][j+1] != 0 ) {
								pic = 1;
							} else if ( map_[i-1][j] != 0 && map_[i][j+1] != 0 ) {
								pic = 7;
							}
						}
					}
					map_[i][j] = pic;
				}
			}
		}
		
	}
	
	public int getH() {
		return h_;
	}
	
	public int getW() {
		return w_;
	}
	
	public int[][] getMap() {
		return map_;
	}
	
	private int h_;
	private int w_;
	private int[][] map_;
}
