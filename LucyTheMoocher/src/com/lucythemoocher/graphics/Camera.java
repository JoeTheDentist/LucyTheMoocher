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
		if ( canDraw_ ) {
			update();
			Canvas canvas = null;
			try {
				canvas = getHolder().lockCanvas();
				onDraw(canvas);
			} finally {
				if ( canvas != null ) {
					getHolder().unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	private float camSpeed() {
		return CAMERASPEED*DT_;
	}

	public void onDraw(Canvas canvas) {
		// background
		float x = screen_.getW()/2-Game.getCharacter().getX()*BACKGROUNDSPEED;
		float y = screen_.getH()/2-Game.getCharacter().getY()*BACKGROUNDSPEED;
		float offsetX = screen_.getW()*BACKGROUNDSPEED;
		float offsetY = screen_.getH()*BACKGROUNDSPEED;

		canvas.drawBitmap(background_.getBitmap().getBitmap(), 
				x-offsetX, 
				y-offsetY, 
				null);

		//decors
		Game.getMap().draw(canvas);

		// player
		Game.getCharacter().draw(canvas);
	}

	public void drawBox(Box box, Canvas canvas, Paint color) {
		canvas.drawRect(box.getX()-offsetx(), box.getY()-offsety(),
				box.getX()+box.getW()-offsetx(), box.getY()+box.getH()-offsety(),
				color);
	}

	public void drawRectangle(float x, float y, float h, float w, int color) {
		//Paint col = new Paint();
		//col.setColor(color);
		//canvas.drawRect(x, y, x+w, y+h, col);
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

	public void drawImage(Box box, Image image, Canvas canvas) {
		switch ( drawing_ ) {
		case NORMAL:
			canvas.drawBitmap(image.getBitmap().getBitmap(), box.getX()-offsetx(), box.getY()-offsety(), null);
			break;
		case BnW:
			canvas.drawBitmap(image.getBitmap().getBitmap(), box.getX()-offsetx(), box.getY()-offsety(), bnwFilter_);
			break;
		default:
			canvas.drawBitmap(image.getBitmap().getBitmap(), box.getX()-offsetx(), box.getY()-offsety(), null);
			break;	
		}

	}

	public void drawImage(float x, float y, Image image, Canvas canvas) {
		switch ( drawing_ ) {
		case NORMAL:
			canvas.drawBitmap(image.getBitmap().getBitmap(), x-offsetx(), y-offsety(), null);
			break;
		case BnW:
			canvas.drawBitmap(image.getBitmap().getBitmap(), x-offsetx(), y-offsety(), bnwFilter_);
			break;
		default:
			canvas.drawBitmap(image.getBitmap().getBitmap(), x-offsetx(), y-offsety(), null);
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
