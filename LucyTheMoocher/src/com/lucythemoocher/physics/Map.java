package com.lucythemoocher.physics;

import java.util.ArrayList;

import com.lucythemoocher.game.Game;
import com.lucythemoocher.graphics.Grid;
import com.schzookwarz.R;

import android.graphics.Canvas;
import android.util.Log;

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
		
		grid_ = new Grid(R.drawable.sets,32,32);
	}
	
	public float pxH() {
		return (h_*grid_.boxH());
	}
	
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
		if ( (0 >= b.getX()) || (b.getX()+b.getW() >= pxW()) 
				|| (0 >= b.getY()) || (b.getY()+b.getH() >= pxH()) ) {
			return true;
		}
		
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

	public void draw(Canvas canvas) {
		drawContour(canvas);
		for (int i=0; i<h_; i++) {
			for (int j=0; j<w_; j++) {
				if ( map_[i][j] != 0 ) {
					Game.getCam().drawImage(j*grid_.boxW(),i*grid_.boxH(), grid_.getImage(map_[i][j]-1), canvas);
				}
			}
		}
	}
	
	private void drawContour(Canvas canvas) {
		//Horizontal
		for (int j=2; j<w_+2; j++) {
			Game.getCam().drawImage((j-2)*grid_.boxW(),-2*grid_.boxH(), grid_.getImage(16), canvas);
			Game.getCam().drawImage((j-2)*grid_.boxW(),-grid_.boxH(), grid_.getImage(10), canvas);
			
			Game.getCam().drawImage((j-2)*grid_.boxW(),(h_)*grid_.boxH(), grid_.getImage(16), canvas);
			Game.getCam().drawImage((j-2)*grid_.boxW(),(h_+1)*grid_.boxH(), grid_.getImage(10), canvas);
		}
		
		//Vertical
		for (int i=2; i<h_+2; i++) {
			Game.getCam().drawImage(-2*grid_.boxW(),(i-2)*grid_.boxH(), grid_.getImage(14), canvas);
			Game.getCam().drawImage(-grid_.boxW(),(i-2)*grid_.boxH(), grid_.getImage(12), canvas);
			
			Game.getCam().drawImage((w_)*grid_.boxW(),(i-2)*grid_.boxH(), grid_.getImage(14), canvas);
			Game.getCam().drawImage((w_+1)*grid_.boxW(),(i-2)*grid_.boxH(), grid_.getImage(12), canvas);
		}
		
		//Top left
		Game.getCam().drawImage(-2*grid_.boxW(),-2*grid_.boxH(), grid_.getImage(0), canvas);
		Game.getCam().drawImage(-2*grid_.boxW(),-1*grid_.boxH(), grid_.getImage(14), canvas);
		Game.getCam().drawImage(-1*grid_.boxW(),-2*grid_.boxH(), grid_.getImage(16), canvas);
		Game.getCam().drawImage(-1*grid_.boxW(),-1*grid_.boxH(), grid_.getImage(9), canvas);
		
		//Top right
		Game.getCam().drawImage((w_+1)*grid_.boxW(),-2*grid_.boxH(), grid_.getImage(2), canvas);
		Game.getCam().drawImage((w_+1)*grid_.boxW(),-1*grid_.boxH(), grid_.getImage(12), canvas);
		Game.getCam().drawImage((w_)*grid_.boxW(),-2*grid_.boxH(), grid_.getImage(16), canvas);
		Game.getCam().drawImage((w_)*grid_.boxW(),-1*grid_.boxH(), grid_.getImage(11), canvas);
		
		//Bottom left
		Game.getCam().drawImage(-2*grid_.boxW(),(h_+1)*grid_.boxH(), grid_.getImage(6), canvas);
		Game.getCam().drawImage(-1*grid_.boxW(),(h_+1)*grid_.boxH(), grid_.getImage(10), canvas);
		Game.getCam().drawImage(-2*grid_.boxW(),(h_)*grid_.boxH(), grid_.getImage(14), canvas);
		Game.getCam().drawImage(-1*grid_.boxW(),(h_)*grid_.boxH(), grid_.getImage(15), canvas);
		
		//Top right
		Game.getCam().drawImage((w_+1)*grid_.boxW(),(h_+1)*grid_.boxH(), grid_.getImage(8), canvas);
		Game.getCam().drawImage((w_)*grid_.boxW(),(h_+1)*grid_.boxH(), grid_.getImage(10), canvas);
		Game.getCam().drawImage((w_+1)*grid_.boxW(),(h_)*grid_.boxH(), grid_.getImage(12), canvas);
		Game.getCam().drawImage((w_)*grid_.boxW(),(h_)*grid_.boxH(), grid_.getImage(17), canvas);
	}
}
