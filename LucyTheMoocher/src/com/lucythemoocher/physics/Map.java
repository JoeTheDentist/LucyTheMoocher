package com.lucythemoocher.physics;

import java.util.ArrayList;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.graphics.Camera;
import com.lucythemoocher.graphics.Grid;
import com.lucythemoocher.R;

public class Map {
	
	private int h_;
	private int w_;
	
	private Grid grid_;
	private int map_[][];
	
	public Map(int mapName) {
		LevelLoader ll = new LevelLoader(mapName);

		h_ = ll.getH();
		w_ = ll.getW();
		map_ = ll.getMap();
		
		grid_ = new Grid(R.drawable.sets);
	}
	
	/**
	 * Level dimension in pixels
	 * @return height
	 */
	public float pxH() {
		return (h_*grid_.boxH());
	}
	
	/**
	 * Level dimension in pixels
	 * @return width
	 */
	public float pxW() {
		return (w_*grid_.boxW());
	}
	
	/**
	 * Precision en y
	 * @return precision en y
	 */
	public float prcH() {
		return grid_.boxH();
	}
	
	/**
	 * Precision en x
	 * @return precision en x
	 */
	public float prcW() {
		return grid_.boxW();
	}
	
	public boolean hasCollision(ArrayList<Box> boxes) {
		//Log.d("Map","boxes "+boxes+" boxes_ "+boxes_);
		boolean b = false;
		for (Box box : boxes) {
			b |= hasCollision(box);
		}
		//boolean b = Box.collideWith(boxes, boxes_);
		return b;
	}
	
	public boolean hasCollision(Box b) {
		boolean br = false;
		for (int i=(int) (b.getX()/prcW()); i<(b.getW()+b.getX())/prcW(); i++) {
			br |= map_[(int) ((b.getY())/prcH())][i] > 0; 
			br |= map_[(int) ((b.getY()+b.getH())/prcH())][i] > 0;
		}
		for (int i=(int) (b.getY()/prcH()); i<(b.getH()+b.getY())/prcH(); i++) {
			br |= map_[i][(int)((b.getX())/prcW())] > 0; 
			br |= map_[i][(int)((b.getX()+b.getW())/prcW())] > 0;
		}
		return br;
	}
	
	public boolean hasDownCollision(ArrayList<Box> boxes) {
		boolean b = false;
		for (Box box : boxes) {
			b |= hasDownCollision(box);
		}
		return b;
	}
	
	public boolean hasDownCollision(Box b) {
		if ( b.getY()+1+b.getH() >= pxH() ) {
			return true;
		}
		for (int i=(int) (b.getX()/prcW()); i<(b.getW()+b.getX())/prcW(); i++) {
			if ( map_[(int) ((b.getY()+1+b.getH())/prcH())][i] != 0 ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasLeftCollision(ArrayList<Box> boxes) {
		boolean b = false;
		for (Box box : boxes) {
			b |= hasLeftCollision(box);
		}
		return b;
	}
	
	public boolean hasLeftCollision(Box b) {
		if ( b.getX()-1 <= 0) {
			return true;
		}
		for (int i=(int) (b.getY()/prcH()); i<(b.getH()+b.getY())/prcH(); i++) {
			if ( map_[i][(int) ((b.getX()-1)/prcW())] != 0 ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRightCollision(ArrayList<Box> boxes) {
		boolean b = false;
		for (Box box : boxes) {
			b |= hasRightCollision(box);
		}
		return b;
	}
	
	public boolean hasRightCollision(Box b) {
		if ( b.getX()+b.getW()+1 >= pxW()) {
			return true;
		}
		for (int i=(int) (b.getY()/prcH()); i<(b.getH()+b.getY())/prcH(); i++) {
			if ( map_[i][(int) ((b.getX()+b.getW()+1)/prcW())] != 0 ) {
				return true;
			}
		}
		return false;
	}

	public void draw() {
		Camera cam = Globals.getInstance().getCamera();
		int iStart = (int) (cam.getScreen().getY() / grid_.boxH());
		int iEnd = (int) (iStart + cam.getScreen().getH() / grid_.boxH());
		int jStart = (int) (cam.getScreen().getX() / grid_.boxW());
		int jEnd = (int) (jStart + cam.getScreen().getW() / grid_.boxW());
		iStart = Math.max(iStart, 0);
		iEnd = Math.min(iEnd+1, h_);
		jStart = Math.max(jStart, 0);
		jEnd = Math.min(jEnd+1, w_);
		for (int i=iStart; i<iEnd; i++) {
			for (int j=jStart; j<jEnd; j++) {
				if ( map_[i][j] != 0 ) {
					cam.drawImage(j*grid_.boxW(),i*grid_.boxH(), grid_.getImage(map_[i][j]-1));
				}
			}
		}
	}
}
