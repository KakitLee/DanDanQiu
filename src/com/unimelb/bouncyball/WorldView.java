package com.unimelb.bouncyball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


public class WorldView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private SurfaceHolder surfaceHolder;
	private boolean running = false;
	public static boolean pause = false;
	private Bitmap bitmap;
	private Context mContext;
	
	public Ball ball;
	public Bar bar;
	public Bricks bricks;

	public boolean onScreen = true;
	public boolean connected = false;
	
	private static float speed = 10;
	public  static int score = 0;
	public String highScore;
	public static int level;
	
	public static int width;
	public static int height;
	
	public static float oldTouchX = 0;
	public static float newTouchX = 0;	
	public static float movingSpeed = 0;
	private static final String TAG="Main";
	//private static boolean firstTimeCreate=true;
	
	public WorldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		getHolder().addCallback(this);
		setFocusable(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(pause==true) 
			return true;
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
		Canvas canvas = null;
		initialInterface();
		while(running) {
			try 
			{
				canvas = surfaceHolder.lockCanvas(null);
				synchronized(surfaceHolder) 
				{
					if (canvas != null )
					{
						onDraw(canvas);
						ball.onDraw(canvas);
						bar.onDraw(canvas);
						bricks.onDraw(canvas);
					}
				}
			}
				catch (Exception e) //this is an exception because of bricks.onDraw. 
				//I make it when bricks is all eliminate then throw an exception then we can load a new map
				{
					surfaceHolder.unlockCanvasAndPost(canvas);
					switchLevel(level+1); 
					initialInterface();
					canvas = surfaceHolder.lockCanvas(null);
				}
				
                finally {
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
		Log.i(TAG,"creating");
		width = getWidth();
		height = getHeight();
		ball = new Ball(this, null, width, height);
		ball.setXSpeed(speed-5);
		ball.setYSpeed(speed);
		bar = new Bar(this, null, width,height);
		bricks = new Bricks(this);
		//Here we can assign a string calling FromJSON to the following variable bricks,
		//so that FromJSON should turn string to a object of type Bricks. 
		
		switchLevel(level);
		
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
    	Paint paint = new Paint();
    	paint.setAntiAlias(true);
    	canvas.drawColor(Color.BLACK);
    	//paint.setFilterBitmap(false);
    	//Rect dest = new Rect(0, 0, width, height);
    	//canvas.drawBitmap(bitmap,null, dest, null);
    	paint.setColor(Color.WHITE);
    	paint.setTextSize(this.height/25);
    	canvas.drawText("Level: "+Integer.toString(level),0,this.height/25,paint);
    	canvas.drawText("Score: " + Integer.toString(score),0,(float) (2.1*this.height/25),paint);
    }
    
    /*
    public void setWallPaper(Bitmap bitmap)
    {
    	this.bitmap=bitmap;
    }
    */
    
    public void setLevel(int level)
    {
    	this.level = level;
    }
    
    public int getLevel()
    {
    	return level;
    }
    
    public Bricks getBricks()
    {
    	return bricks;
    }
    
    public Ball getBall()
    {
    	return ball;
    }
    
    public Bar getBar(){
    	return bar;
    }
    
    public void switchLevel(int level)
    {
    	this.level = level;
    	String levelFileName = "level-"+Integer.toString(this.level)+".map";
	    File levelFile = new File("/sdcard/Breakout/Maps/" + levelFileName);
	    if(levelFile.exists())
	    {
	    	try {
	    		BufferedReader br = new BufferedReader(new FileReader(levelFile));
	    		String theBricks = br.readLine();
	    		bricks.FromJSON(theBricks);
	    		ball.resetCoordsAndSpeed();
	    		bar.resetCoords();
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
	    /**else ask the user to download the new maps.**/
	    else
	    {
	    	 Intent intent = new Intent(mContext, downLoadLevel.class);
	    	    mContext.startActivity(intent);;
	    }
    }

    @SuppressLint("WrongCall")
	public void initialInterface()
    {
    	Canvas canvas = null;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);
    	for (int i = 0 ; i < 2; i++)
    	{
    		try {
    			canvas = surfaceHolder.lockCanvas(null);
    			synchronized(surfaceHolder) 
    			{
    				if (canvas != null) 
    				{
    					this.onDraw(canvas);
    					ball.onDraw(canvas);
    					bar.onDraw(canvas);
    					bricks.onDraw(canvas);
    					paint.setTextSize(this.height/10);
    					if(i==0)
    						canvas.drawText("Ready?",(float) (this.width/3.5),2*this.height/3,paint);
    					else
    						canvas.drawText("Go!",(float) (this.width/2.8),2*this.height/3,paint);
    				}
    			}
    		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		finally 
    		{
    			if (canvas != null) 
    			{
    				surfaceHolder.unlockCanvasAndPost(canvas);
    				try{
    					Thread.sleep(500);}
    				catch (InterruptedException e) 
    				{
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} 
    			}	
    		}
    	}
    }
    
}
