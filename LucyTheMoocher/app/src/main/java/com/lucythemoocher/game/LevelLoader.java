package com.lucythemoocher.game;

import java.io.InputStream;
import java.util.Scanner;


import android.util.Log;

import com.lucythemoocher.R;
import com.lucythemoocher.actors.MonstersManager;
import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.actors.Tank;
import com.lucythemoocher.actors.TargetCharacter;
import com.lucythemoocher.controls.AIController;
import com.lucythemoocher.controls.ActionController;
import com.lucythemoocher.graphics.Grid;
import com.lucythemoocher.util.Direction;
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
		
		// temporary grid to have box dimensions
		Grid grid = new Grid(R.drawable.sets);
		
		monsters_ = new MonstersManager();
		
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
				char curChar = cur.charAt(0);
				if (Character.isDigit(curChar)) {
					map_[i][j] = Integer.parseInt(cur);
				} else {
					switch (curChar) {
					case 'L':
						character_ = new PlayerCharacter(new ActionController(), j * grid.boxW(), i * grid.boxH());
						break;
					case 'F':
						target_ = new TargetCharacter(new AIController(), j * grid.boxW(), i * grid.boxH());
						break;
					case 'T':
						monsters_.addMonster(new Tank(j * grid.boxW(), i * grid.boxH(), Direction.LEFT));
						break;
					default:
						Log.w("LevelLoader", "Bad code: "+curChar+" skipping...");
						break;
					}
				}
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
	
	public PlayerCharacter getCharacter() {
		return character_;
	}
	
	public TargetCharacter getTarget() {
		return target_;
	}
	
	public MonstersManager getMonsters() {
		return monsters_;
	}
	
	private int h_;
	private int w_;
	private int[][] map_;
	private PlayerCharacter character_;
	private TargetCharacter target_;
	private MonstersManager monsters_;
}
