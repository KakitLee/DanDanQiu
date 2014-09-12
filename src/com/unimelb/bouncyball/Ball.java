package com.unimelb.bouncyball;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Ball {
	private final String TAG = "Main";
	private static float ballRadius = 10;
	
    private WorldView worldView;
    private Bitmap bmp;
   
    public float x;
    public float y;
    
    private float xSpeed;
    private float ySpeed;
    
    private int screenWidth;
    private int screenHeight;
    
    public Ball(WorldView worldView, Bitmap bmp, int screenWidth, int screenHeight) {
    	  this.worldView = worldView;
          this.bmp = bmp;
          this.screenWidth = screenWidth;
          this.screenHeight = screenHeight;
          
          setX(screenWidth/2);
          setY(screenHeight/10);
          setXSpeed(0);
          setYSpeed(0);
          
          updatePosition(x, y);
    }
    
    public void resetCoords(float screenWidth, float screenHeight, float x, float y, float xSpeed, float ySpeed) {
    	Log.i(TAG,"this is Ball 2"); 
    	this.x = (x/screenWidth)*this.screenWidth;
        this.y = ballRadius;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed*-1;
    }
    
    public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

    public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	public void updatePosition(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
	
	public void updatePhysics() {
		Log.i(TAG,"this is Ball 3"); 
		float left,right;
		left = worldView.bar.getX()-worldView.bar.getLength()/2;
		right = worldView.bar.getX()+worldView.bar.getLength()/2;
		if(x > screenWidth-ballRadius) {
    		xSpeed=-xSpeed;
    	}
		else if(x < ballRadius) {
    		xSpeed=-xSpeed;
    	}
    	if(y > screenHeight-ballRadius) {
    		ySpeed=-ySpeed;
    	}
    	else if(y < ballRadius) {
    		ySpeed=-ySpeed;
    	}
    	else if((y + ballRadius)>= (worldView.bar.getY()-worldView.bar.getWidth()/2))
    	{
    		if(x>=left&&x<=right&&((y - ballRadius)<(worldView.bar.getY()-worldView.bar.getWidth()/2)))
    			ySpeed=-ySpeed;
    	}
    		
	}
	
	public void moveBall() {
		x = x + xSpeed;
		y = y + ySpeed;	
	}
	

   
    public void onDraw(Canvas canvas) {
    	Log.i(TAG,"this is Ball 4"); 
    	Paint paint = new Paint();
    	paint.setAntiAlias(true);
    	paint.setColor(Color.WHITE);
    	
    	updatePhysics();

    	if(worldView.onScreen) {
    		moveBall();
    		updatePosition(x, y);
    		canvas.drawCircle(x, y, ballRadius, paint);
    	}
    }
}