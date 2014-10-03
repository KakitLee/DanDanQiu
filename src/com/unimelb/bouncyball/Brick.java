package com.unimelb.bouncyball;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Brick {


	private float x;
	private float y;
	
	private float length;
	private float width;
	
    private int screenWidth;
    private int screenHeight;
	
	private boolean live; 
	private int hitTimes;
	
    private WorldView worldView;
    
	
	public Brick (WorldView worldView,float x, float y,float length, float width){
		
		this.x = x;
		this.y = y;
		this.live = true;
		Random rn = new Random();
		this.hitTimes = rn.nextInt(3) + 1;
		this.worldView = worldView;
		this.screenWidth = worldView.getWidth();
		this.screenHeight = worldView.getHeight();
		this.length = length;
		this.width = width; 
	}
	
	public Brick (WorldView worldView)
	{
		this.screenWidth=worldView.getWidth();
		this.screenHeight=worldView.getHeight();
		this.worldView = worldView;
		live = true;
	}
	
	public float getLength()
	{
		return length;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY()
	{
		return y;
	}

	public boolean getLive()
	{
		return live;
	}

	public Object getHitTimes() {
		// TODO Auto-generated method stub
		return hitTimes;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void setLength(float length)
	{
		this.length = length;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
	public void setHitTimes(int times)
	{
		hitTimes = times;
	}
	
	public void setLive(Boolean live)
	{
		this.live = live;
	}
	
	public void setWorldView(WorldView worldView)
	{
		this.worldView = worldView;
	}
	
	public void eliminateBrick(){
		hitTimes=hitTimes-1;
		WorldView.score = WorldView.score + 100;
		if(hitTimes == 0)
			live = false;
	}
	
	//return value for counting how many bricks are still alive.
    @SuppressLint("DrawAllocation")
	public int onDraw(Canvas canvas) {
    	if(live == true){
    	
    		Paint paint = new Paint();
    		paint.setAntiAlias(true);
    		if (hitTimes == 3)
    			paint.setColor(Color.YELLOW);
    		else if (hitTimes == 2)
    			paint.setColor(Color.CYAN);
    		else 
    			paint.setColor(Color.RED);
    		if(worldView.onScreen) 
    		{
    			RectF r = new RectF(x-length/2,y-width/2,x+length/2,y+width/2);
    			canvas.drawRect(r, paint);
    		}
    		return 1;
    	}
    	return 0;
    }


}
