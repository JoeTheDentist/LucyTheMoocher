package com.lucythemoocher.graphics;


import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.controls.GlobalController;
import com.lucythemoocher.physics.Box;
import com.lucythemoocher.util.Resources;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Render Drawables and Background
 * Handle scrolling
 * Can also support effects (black and white for now)
 * Camera's system is used in MasterLoop
 * Camera is not the physical screen, but a representation of the screen, so w() et h() are independent from the
 * hardware screen's size.
 * @see MasterLoop
 */
public class Camera extends SurfaceView implements SurfaceHolder.Callback {

	private static final float CAMERASPEED = (float) 0.15;
	static final float BACKGROUNDSPEED = (float) 0.5;

	private static float DT_ = 1;

	private Box screen_; // hardware screen's size
	private Canvas canvas_;
	private float currX_;
	private float currY_;
	private float scale_; // coefficient depending on hardware screen's size


	private boolean canDraw_ = false;
	/**
	 * Constructor
	 * @param context
	 * @param h Height in dp pixels
	 * @param w Width in dp pixels
	 */
	public Camera() {
		super(Resources.getActivity());
		float h = Resources.getActivity().getWindowManager().getDefaultDisplay().getHeight();
		float w = Resources.getActivity().getWindowManager().getDefaultDisplay().getWidth();
		currX_ = 1;
		currY_ = 1;
		screen_ = new Box(currX_,currY_,h,w);

		getHolder().addCallback(this);
		setFocusable(true);
		scale_ = screen_.getW() / 800f;
		setSpeed(1.0f / 30.0f);
		this.requestFocus();
		this.setFocusableInTouchMode(true);


	}

	/**
	 * Update position of the camera
	 */
	public void update() {

	}
	
	/**
	 * Follow the point (x, y) without exceeding camera's speed
	 * @param x
	 * @param y
	 */
	public void followPoint(float x, float y) {
		//Camera follows the player
		
		PointF direction = new PointF(Globals.getInstance().getGame().getCharacter().getX()-currX_,
				Globals.getInstance().getGame().getCharacter().getY()-currY_);
		float distance = direction.length();
		if (distance == 0)
			return;
		float coeff = camSpeed() * distance * Globals.getInstance().getGame().getDt();
		direction.x /= distance; // normalize
		direction.y /= distance; // normalize
		if (Math.abs(coeff) > distance) {
			coeff = distance; // do not exceed the point
		}
		currX_ += direction.x * coeff;
		currY_ += direction.y * coeff;
		screen_.setX((currX_ - w() / 2));
		screen_.setY((currY_ - h() / 2));
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
	 * 
	 * This is just a coeff used, the speed also depends on the distance with 
	 * the target point
	 */
	private float camSpeed() {
		return CAMERASPEED*DT_;
	}



	/**
	 * Getter
	 * @return Camera's height in dp pixels
	 * 
	 * Doesn't depend on the hardware screen's size
	 * @see #physicalH()
	 */
	public float h() {
		return screen_.getH() / scale_;
	}
	
	/**
	 * Getter
	 * @return Camera's width in dp pixels
	 * 
	 * Doesn't depend on the hardware screen's size
	 * @see #physicalW()
	 */
	public float w() {
		return screen_.getW() / scale_;
	}
	
	public float physicalH() {
		return screen_.getH();
	}
	
	public float physicalW() {
		return screen_.getW();
	}

	/**
	 * Draw the image at the position x y
	 * Screen must be locked
	 * 
	 * @param x x position in dp pixels
	 * @param y y position in dp pixels
	 * @param image
	 * @see #lockScreen()
	 */
	public void drawImage(float x, float y, Image image) {
		float xx = x  - offsetx() ;
		float yy = y  - offsety() ;
		canvas_.drawBitmap(image.getBitmap().getBitmap(), xx, yy, image.getBitmap().getPaint());
	}
	
	/**
	 * Draw the image without using the scrolling
	 * @param  x position in dp pixels
	 * @param y y position in dp pixels
	 * @param image
	 * @see #lockScreen()
	 */
	public void drawImageOnHud(float x, float y, Image image) {
		canvas_.drawBitmap(image.getBitmap().getBitmap(), x, y, image.getBitmap().getPaint());
	}
	
	/**
	 * Draw the background according to the camera position
	 * Screen must be locked
	 * @param background
	 * @see #lockScreen()
	 */
	public void drawBackground(Background background)  {
		float x = -(currX_ - screen_.getW() / 2) * BACKGROUNDSPEED;
		float y = -(currY_ - screen_.getH() / 2) * BACKGROUNDSPEED;
		float offsetX = screen_.getW() / 2;
		float offsetY = screen_.getH() / 2;
		background.draw(Math.abs(offsetX-x), Math.abs(offsetY-y));
	}
	
	/**
	 * Draw the background according to the camera position
	 * Screen must be locked
	 * @param 
	 * @param 
	 * @param 
	 * @see #lockScreen()
	 */
	void drawBackground(Image im, float x, float y)  {
		canvas_.drawBitmap(im.getBitmap().getBitmap(), 
				x, 
				y, 
				null);
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
	
	public boolean onKeyDown (int keyCode, KeyEvent event) {
		GlobalController.getInstance().processKey(keyCode, event);
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
	
	public float getScale() {
		return scale_;
	}
	


}
