package com.lucythemoocher.graphics;

import com.lucythemoocher.Globals.Globals;
import com.lucythemoocher.actors.PlayerCharacter;
import com.lucythemoocher.controls.GlobalController;
import com.lucythemoocher.physics.Box;
import com.lucythemoocher.util.Direction;
import com.lucythemoocher.util.Resources;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Render Drawables and Background
 * Handle scrolling
 * Camera's system is used in MasterLoop
 * Camera is not the physical screen, but a representation of the screen, so w() et h() are independent from the
 * hardware screen's size.
 * @see MasterLoop
 */
public class Camera extends SurfaceView implements SurfaceHolder.Callback {

	private static final float CAMERASPEED = 2f;
	static final float BACKGROUNDSPEED = 0.5f;

	private static float DT_ = 1;

	private Box screen_; // hardware screen's size
	private Canvas canvas_;
	private float currX_;
	private float currY_;
	private float scale_; // coefficient depending on hardware screen's size
	private boolean canDraw_ = false;
	
	private SparseArray<Paint> hudPaints_;
	private RectF hudOval_;
	
	/**
	 * Constructor
	 */
	public Camera() {
		super(Resources.getActivity());
		float h = Resources.getActivity().getWindowManager().getDefaultDisplay().getHeight();
		float w = Resources.getActivity().getWindowManager().getDefaultDisplay().getWidth();
		currX_ = 1;
		currY_ = 1;
		screen_ = new Box(currX_,currY_,h,w);
		
		hudPaints_ = new SparseArray<Paint>();
		initHudColors(new int[] {Color.GRAY, Color.BLACK});
		hudOval_ = new RectF(-100, -100, w + 100, h + 100);
		
		getHolder().addCallback(this);
		setFocusable(true);
		scale_ = screen_.getW() / 1000f;
		setSpeed(1.0f / 30.0f);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
	}
	
	/**
	 * Target a position at the middle of the screen
	 * @param x coor
	 * @param y coor
	 */
	public void moveTo(float x, float y) {
		currX_ = x;
		currY_ = y;
		screen_.setX((currX_ - w() / 2));
		screen_.setY((currY_ - h() / 2));
	}
	
	/**
	 * Initialize the HUD colors
	 * @param colorArray colors to initialize
	 */
	private void initHudColors(int colorArray[]) {
		for (int color : colorArray) {
			Paint currPaint = new Paint();
			currPaint.setStrokeWidth(250);
			currPaint.setStyle(Paint.Style.STROKE);
			currPaint.setColor(color);
			currPaint.setAlpha(50);
			hudPaints_.put(color, currPaint);
		}
	}

	/**
	 * Update position of the camera
	 */
	public void update() {
		PlayerCharacter pc = Globals.getInstance().getGame().getCharacter();
		// follow player if exists
		if (pc != null) {
			followPoint( pc.getCinematic().getTargetX(), pc.getCinematic().getTargetY());
		}
	}
	
	/**
	 * Getter
	 */
	public Box getScreen() {
		Box scaledScreen = new Box(screen_);
		scaledScreen.setH(scaledScreen.getH() / scale_);
		scaledScreen.setW(scaledScreen.getW() / scale_);
		return scaledScreen;
	}
	
	/**
	 * Follow the point (x, y) without exceeding camera's speed
	 * @param x
	 * @param y
	 */
	public void followPoint(float x, float y) {
		float coeff = camSpeed() * Globals.getInstance().getGame().getDt();
		float diffX = x - currX_;
		float diffY = y - currY_;
		if (diffX == 0 && diffY == 0) {
			return;
		}
		float ratio = (float) Math.sqrt(Math.abs(diffX) + Math.abs(diffY));
		if (coeff / ratio < 1) {
			currX_ += diffX / ratio * coeff;
			currY_ += diffY / ratio * coeff;
		} else {
			currX_ += diffX;
			currY_ += diffY;
		}
		screen_.setX((currX_ - w() / 2));
		screen_.setY((currY_ - h() / 2));
	}

	/**
	 * Must be called before renderings
	 * This locking is currently handled in the MasterLoop
	 * @return true if managed to create the canvas
	 * @see unlockScreen
	 * @see MasterLoop
	 */
	public boolean lockScreen() {
		canvas_ = getHolder().lockCanvas();
		return canvas_ != null;
	}
	
	/**
	 * Graphical operation to perform before drawing things
	 * Assume the canvas is not null
	 */
	public void prepare() {
		canvas_.scale(scale_, scale_);
	}
	
	/**
	 * Must be called after renderings, to unlock canvas
	 * Assumes the canvas is not null
	 * @see lockScreen
	 */
	public void unlockScreen() {
		getHolder().unlockCanvasAndPost(canvas_);
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
	
	/**
	 * Getter
	 * @return height of the physical screen (real resolution)
	 */
	public float physicalH() {
		return screen_.getH();
	}
	
	/**
	 * Getter
	 * @return width of the physical screen (real resolution)
	 */
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
		drawImageOnHud(x, y, image, false);
	}
	
	/**
	 * Draw the image without using the scrolling
	 * @param  x position in dp pixels
	 * @param y y position in dp pixels
	 * @param image
	 * @param scale does the image need to be scaled or not
	 * @see #lockScreen()
	 */
	public void drawImageOnHud(float x, float y, Image image, boolean scale) {
		if (scale) canvas_.scale(1/scale_, 1/scale_);
		canvas_.drawBitmap(image.getBitmap().getBitmap(), x, y, image.getBitmap().getPaint());
		if (scale) canvas_.scale(scale_, scale_);
	}
	
	/**
	 * Draw the HUD rect, insensitive to scale
	 * @param dir
	 * @param c
	 */
	public void drawRectOnHud(int dir, int c) {
		
		int start_radix = 0;
		int radix_range = 90;
		int big_radix = 90;
		int small_radix = 180 - big_radix;
		if (dir == Direction.UP) {
			start_radix = -90 - big_radix / 2;
			radix_range = big_radix;
		} else if (dir == Direction.RIGHT) {
			start_radix = 0 - small_radix / 2;
			radix_range = small_radix;
		} else if (dir == Direction.DOWN) {
			start_radix = 90 - big_radix / 2;
			radix_range = big_radix;
		} else if (dir == Direction.LEFT) {
			start_radix = 180 - small_radix / 2;
			radix_range = small_radix;
		}

		// Insensitive to scale (quite dirty :s)
		canvas_.scale(1/scale_, 1/scale_);
		canvas_.drawArc(hudOval_, start_radix, radix_range, false, hudPaints_.get(c));
		canvas_.scale(scale_, scale_);
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
	
	/**
	 * Getter
	 * @return current scale
	 */
	public float getScale() {
		return scale_;
	}
}
