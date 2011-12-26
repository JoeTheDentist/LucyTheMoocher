package com.lucythemoocher.graphics;


import com.lucythemoocher.controls.GlobalController;
import com.lucythemoocher.game.Game;
import com.lucythemoocher.physics.Box;
import com.lucythemoocher.util.Resources;

import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Render Drawables and Background
 * Handle scrolling
 * Can also support effects (black and white for now)
 * Camera's system is used in MasterLoop
 * @see MasterLoop
 */
public class Camera extends SurfaceView implements SurfaceHolder.Callback {

	/**
	 * Constructor
	 * @param context
	 * @param h Height in pixels
	 * @param w Width in pixels
	 */
	public Camera() {
		super(Resources.getActivity());
		float h = Resources.getActivity().getWindowManager().getDefaultDisplay().getHeight();
		float w = Resources.getActivity().getWindowManager().getDefaultDisplay().getWidth();
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
		setSpeed(1);
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


	/**
	 * Must be called before renderings
	 * This locking is currently handled in the MasterLoop
	 * @see #unlockScreen()
	 * @see MasterLoop
	 */
	public void lockScreen() {
		canvas_ = getHolder().lockCanvas();
		canvas_.scale(scale_, scale_);
	}
	
	/**
	 * Must be called after renderings, to unlock canvas
	 * @see #lockScreen()
	 */
	public void unlockScreen() {
		if ( canvas_ != null ) {
			canvas_.scale(1.0f, 1.0f);
			getHolder().unlockCanvasAndPost(canvas_);
		}
	}
	
	/**
	 * Getter
	 * @return True when Camera is ready for rendering
	 */
	public boolean canDraw() {
		return canDraw_;
	}

	/**
	 * Getter
	 * @return Camera's speed
	 */
	private float camSpeed() {
		return CAMERASPEED*DT_;
	}



	/**
	 * Getter
	 * @return Screen's height
	 */
	public float h() {
		return screen_.getH();
	}
	
	/**
	 * Getter
	 * @return Screen's width
	 */
	public float w() {
		return screen_.getW();
	}

	/**
	 * Draw the image at the position x y
	 * Screen must be locked
	 * 
	 * @param x x position in pixels
	 * @param y y position in pixels
	 * @param image
	 * @see #lockScreen()
	 */
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
	
	/**
	 * Draw the background according to the camera position
	 * Screen must be locked
	 * @param background
	 * @see #lockScreen()
	 */
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

	/**
	 * Nal's shit
	 */
	public void setNormalDrawing() {
		drawing_ = NORMAL;
	}

	/**
	 * Nal's shit
	 */
	public void setBnWDrawing() {
		drawing_ = BnW;
	}
	

	/**
	 * Mutator 
	 * @param dt New dt speed
	 */
	public static void setSpeed(float dt) {
		DT_ = dt;
	}


	public boolean onTouchEvent(MotionEvent event) {
		GlobalController.getInstance().process(event);
		return true;
	}

	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {}

	public void surfaceCreated(SurfaceHolder arg0) {
		canDraw_ = true;
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		canDraw_ = false;
	}
	
	public float offsetx() {
		return screen_.getX();
	}

	public float offsety() {
		return screen_.getY();
	}
	
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

}
