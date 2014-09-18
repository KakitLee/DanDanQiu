package com.unimelb.bouncyball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.OutputStream;

public class WorldView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private SurfaceHolder surfaceHolder;
	private boolean running = false;
	public Ball ball;
	public Bar bar;
	public Bricks bricks;

	public boolean onScreen = true;
	public OutputStream outputStream;
	public boolean connected = false;
	
	private static float speed = 10;
	public  static int score = 0;
	public static int level;
	
	public static int width;
	public static int height;
	
	public static float oldTouchX = 0;
	public static float newTouchX = 0;	
	public static float movingSpeed = 0;
	private static final String TAG="Main";
	
	public WorldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		setFocusable(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			oldTouchX = event.getX();
		}
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			int x = (int)event.getX();
			newTouchX = x;
			movingSpeed = (newTouchX - oldTouchX)/10;
			oldTouchX = newTouchX;
			float left = bar.getX()-bar.getLength();
			float right = bar.getX()+bar.getLength();
			if(x>=left&&x<=right)    //for avoiding the bar can be moved faster than light speed.
				bar.setX((float)x);
			//Log.i(TAG,String.valueOf(k));

		}
		
		return true;
	}
	
	@SuppressLint("WrongCall")
	public void run() {
		while(running) {
			Canvas canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas(null);
				synchronized(surfaceHolder) {
					if (canvas != null) {
					onDraw(canvas);
					ball.onDraw(canvas);
					bar.onDraw(canvas);
					bricks.onDraw(canvas);
					}
				}
        	} finally {
        		if (canvas != null) {
        			surfaceHolder.unlockCanvasAndPost(canvas);
        		}
        	}
			try {
				Thread.sleep(5);
			} catch(Exception e) {}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
		this.running = true;
		
		width = getWidth();
		height = getHeight();
		ball = new Ball(this, null, width, height);
		ball.setXSpeed(speed-5);
		ball.setYSpeed(speed);
		bar = new Bar(this, null, width,height);
		
		//Here we can assign a string calling FromJSON to the following variable bricks,
		//so that FromJSON should turn string to a object of type Bricks. 
		bricks = new Bricks(this,45);
		
		/**Fot testing fromJSON and toJSON
		String map = bricks.ToJSON();
		Log.i(TAG,map);
		Bricks bricksNew = new Bricks(this);
		bricksNew.FromJSON(map);
		String mapNew = bricksNew.ToJSON();
		Log.i(TAG,mapNew);
		bricks = bricksNew;
		**/
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		// TODO Auto-generated method stub
		score = 0;
	}
	
	
    @Override
    protected void onDraw(Canvas canvas) {
    	//Draw the Background
    	/*
    	 * Paint testPaint = new Paint();
    	 * textPaint.setCoulr(GREEN);
    	 * textPaint.drawText("score : 50",x,y,textPaint);
    	 */
    	canvas.drawColor(Color.BLACK);
    	Paint paint = new Paint();
    	paint.setAntiAlias(true);
    	paint.setColor(Color.WHITE);
    	paint.setTextSize(this.height/25);
    	canvas.drawText("Score: " + Integer.toString(score),0,this.height/25,paint);
    	canvas.drawText("Level: 1",(float) (this.width*0.7),this.height/25,paint);
    }
}
