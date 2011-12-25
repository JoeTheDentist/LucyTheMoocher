package com.lucythemoocher.graphics;

import com.lucythemoocher.controls.PlayerController;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.physics.Box;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
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
	private Canvas canvas_;
	private float currX_;
	private float currY_;
	private float scale_;

	private int drawing_ = NORMAL;
	private Paint bnwFilter_;

	private boolean canDraw_ = false;

	public Camera(Context context, float h, float w) {
		super(context);
		currX_ = 1;
		currY_ = 1;
		screen_ = new Box(currX_,currY_,h,w);

		//Black n white filter
		bnwFilter_ = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		bnwFilter_.setColorFilter(f);

		getHolder().addCallback(this);
		setFocusable(true);
		scale_ = screen_.getW() / 800f;
	}

	/**
	 * Update position of the camera
	 */
	public void update() {
		//Camera follows the player
		currX_ += (Game.getCharacter().getX()-currX_)*camSpeed() / scale_;
		currY_ += (Game.getCharacter().getY()-currY_)*camSpeed() / scale_;
		screen_.setX((currX_ - screen_.getW() / scale_ / 2));
		screen_.setY((currY_ - screen_.getH() / scale_ / 2));
	}


	public void lockScreen() {
		update();
		canvas_ = getHolder().lockCanvas();
		canvas_.scale(scale_, scale_);
	}
	
	public void unlockScreen() {
		if ( canvas_ != null ) {
			canvas_.scale(1.0f, 1.0f);
			getHolder().unlockCanvasAndPost(canvas_);
		}
	}
	
	public boolean canDraw() {
		return canDraw_;
	}

	private float camSpeed() {
		return CAMERASPEED*DT_;
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
		float xx = x  - offsetx() ;
		float yy = y  - offsety() ;
		switch ( drawing_ ) {
		case NORMAL:
			canvas_.drawBitmap(image.getBitmap().getBitmap(), xx, yy, null);
			break;
		case BnW:
			canvas_.drawBitmap(image.getBitmap().getBitmap(), xx, yy, bnwFilter_);
			break;
		default:
			canvas_.drawBitmap(image.getBitmap().getBitmap(), xx, yy, null);
			break;	
		}
	}
	
	public void drawBackground(Background background)  {
		// background
		float x = -(currX_ - screen_.getW() / 2) * BACKGROUNDSPEED;
		float y = -(currY_ - screen_.getH() / 2) * BACKGROUNDSPEED;
		float offsetX = screen_.getW() / 2;
		float offsetY = screen_.getH() / 2;

		canvas_.drawBitmap(background.getImage().getBitmap().getBitmap(), 
				x - offsetX, 
				y - offsetY, 
				null);
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
