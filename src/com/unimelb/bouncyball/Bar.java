package com.unimelb.bouncyball;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;


public class Bar {
	private static float length = WorldView.width/5;
	private static float width = WorldView.height/102;
	
    private WorldView worldView;
    private Bitmap bmp;
   
    public float x;
    public float y;
    
    
    private int screenWidth;
    private int screenHeight;
	private String Main;
    
    public Bar(WorldView worldView, Bitmap bmp, int screenWidth, int screenHeight) {
    	  this.worldView = worldView;
          this.bmp = bmp;
          this.screenWidth = screenWidth;
          this.screenHeight = screenHeight;
          
          setX(screenWidth/2); 
          setY(9*screenHeight/10);
          updatePosition(x, y);
    }
    
    public float getLength()
    {
    	return length;
    }
    
    public float getWidth()
    {
    	return width;
    }
    
    public float getX() {
		return x;
	}

    public float getY() {
		return y;
	}
    
	public void setX(float x) {
		this.x = x;
		if(x>screenWidth-length/2)
			this.x=screenWidth-length/2;
		if(x<length/2)
			this.x=length/2;
	}


	public void setY(float d) {
		this.y = d;
	}


	public void updatePosition(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
	


   
    @SuppressLint("DrawAllocation")
	public void onDraw(Canvas canvas) {
    	Paint paint = new Paint();
    	paint.setAntiAlias(true);
    	paint.setColor(Color.BLUE);


    	if(worldView.onScreen) {
    		
    		RectF r = new RectF(x-length/2,y-width/2,x+length/2,y+width/2);
    		updatePosition(x, y);
    		canvas.drawRect(r, paint);
    	}
    }
}
