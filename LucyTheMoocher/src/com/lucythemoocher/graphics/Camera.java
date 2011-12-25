package com.lucythemoocher.graphics;

import java.io.InputStream;

import com.lucythemoocher.controls.PlayerController;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.physics.Box;
import com.lucythemoocher.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Camera extends SurfaceView implements SurfaceHolder.Callback {
	private static final int NORMAL = 0;
	private static final int BnW = 1;

	private static final float CAMERASPEED = (float) 0.3;
	private static final float BACKGROUNDSPEED = (float) 0.5;

	private static float DT_ = 1;

	private Box screen_;
	private Image background_;
	private Canvas canvas_;
	private float currX_;
	private float currY_;

	private int drawing_ = NORMAL;
	private Paint bnwFilter_;

	private boolean canDraw_ = false;

	public Camera(Context context, float h, float w) {
		super(context);
		currX_ = 1;
		currY_ = 1;
		screen_ = new Box(currX_,currY_,h,w);

		//Background
		InputStream is = getResources().openRawResource(R.drawable.background1);
		BitmapDrawable bd = new BitmapDrawable(is);
		background_ = new Image(bd);

		//Black n white filter
		bnwFilter_ = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		bnwFilter_.setColorFilter(f);

		getHolder().addCallback(this);
		setFocusable(true);
	}

	/**
	 * Update position of the camera
	 */
	public void update() {
		//Camera follows the player
		currX_ += (Game.getCharacter().getX()-currX_)*camSpeed();
		currY_ += (Game.getCharacter().getY()-currY_)*camSpeed();
		screen_.setX(currX_-screen_.getW()/2);
		screen_.setY(currY_-screen_.getH()/2);
	}

	public void refreshScreen() {
	
	}
	
	public void lockScreen() {
		update();
		canvas_ = getHolder().lockCanvas();
		onDraw();
	}
	
	public void unlockScreen() {
		if ( canvas_ != null ) {
			getHolder().unlockCanvasAndPost(canvas_);
		}
	}
	
	public boolean canDraw() {
		return canDraw_;
	}

	private float camSpeed() {
		return CAMERASPEED*DT_;
	}

	public void onDraw() {
		// background
		float x = screen_.getW()/2-Game.getCharacter().getX()*BACKGROUNDSPEED;
		float y = screen_.getH()/2-Game.getCharacter().getY()*BACKGROUNDSPEED;
		float offsetX = screen_.getW()*BACKGROUNDSPEED;
		float offsetY = screen_.getH()*BACKGROUNDSPEED;

		canvas_.drawBitmap(background_.getBitmap().getBitmap(), 
				x-offsetX, 
				y-offsetY, 
				null);

		//decors
		Game.getMap().draw();

		// player
		Game.getCharacter().draw();
	}

	public boolean onTouchEvent(MotionEvent event) {
		PlayerController.process(event);
		return true;
	}

	public float offsetx() {
		return screen_.getX();
	}

	public float offsety() {
		return screen_.getY();
	}

	public float h() {
		return screen_.getH();
	}

	public float w() {
		return screen_.getW();
	}


	public void drawImage(float x, float y, Image image) {
		switch ( drawing_ ) {
		case NORMAL:
			canvas_.drawBitmap(image.getBitmap().getBitmap(), x-offsetx(), y-offsety(), null);
			break;
		case BnW:
			canvas_.drawBitmap(image.getBitmap().getBitmap(), x-offsetx(), y-offsety(), bnwFilter_);
			break;
		default:
			canvas_.drawBitmap(image.getBitmap().getBitmap(), x-offsetx(), y-offsety(), null);
			break;	
		}
	}

	public void setNormalDrawing() {
		drawing_ = NORMAL;
	}

	public void setBnWDrawing() {
		drawing_ = BnW;
	}

	public static void setSpeed(float dt) {
		DT_ = dt;
	}


	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	public void surfaceCreated(SurfaceHolder arg0) {
		canDraw_ = true;
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		canDraw_ = false;
	}
	
}
