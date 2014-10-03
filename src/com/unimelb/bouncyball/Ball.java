package com.unimelb.bouncyball;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Ball {
	private static float ballRadius = 10;
	
    private WorldView worldView;
    private Bitmap bmp;
   
    private float x;
    private float y;
    
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
          setY(5*screenHeight/10);
          setXSpeed(0);
          setYSpeed(0);
          updatePosition(x, y);
          ballRadius = (float) (worldView.getWidth()/72.0);
    }
    
    public void resetCoordsAndSpeed() { 
    	this.x = (x/screenWidth)*this.screenWidth;
        this.y = ballRadius;
        setX(screenWidth/2);
        setY(5*screenHeight/10);
        float increase = (float) (1 + worldView.getLevel()*0.1);
        setXSpeed(5*increase);
        setYSpeed(10*increase);
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
/*	
	public void updatePhysics() {
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
*/	
	

	
	public void updatePhysics() {
		detectBoundary();
		detectBarCollision();
		detectBricksCollision();
	}
	
	public void detectBoundary()
	{
		if(x > screenWidth-ballRadius) {
    		xSpeed=-Math.abs(xSpeed);
    	}
		else if(x < ballRadius) {
    		xSpeed=Math.abs(xSpeed);
    	}
    	if(y > screenHeight-ballRadius) {
    		//the player lose
    		//ySpeed=-ySpeed;
    		generateGameOverDialog();
    	}
    	else if(y < ballRadius) {
    		ySpeed=-ySpeed;
    	} 
	}
	

	public void detectBarCollision()
	{
		if(ySpeed<0) //improve efficiency
			return;
		else
		{
			float upperLine = worldView.bar.getY()-worldView.bar.getWidth()/2;
			float leftLine = worldView.bar.getX() - worldView.bar.getLength()/2;
			float rightLine = worldView.bar.getX() + worldView.bar.getLength()/2;
			if(x>=leftLine&&x<=rightLine)
			{
				if(y+ballRadius>=upperLine&&y<upperLine)
				{	
					ySpeed=-ySpeed;
					xSpeed=xSpeed+WorldView.movingSpeed;
				}
			}
			else if(x+0.707*ballRadius>=leftLine&&x<leftLine&&xSpeed>0) //0.707 = sqrt(2)/2
			{
				if(y<=upperLine&&y+ballRadius>=upperLine)
				{
					xSpeed=-xSpeed;
					ySpeed=-ySpeed;
				}
			}
			else if(x-0.707*ballRadius<=rightLine&&x>rightLine&&xSpeed<0)
			{
				if(y<=upperLine&&y+ballRadius>=upperLine)
				{
					xSpeed=-xSpeed;
					ySpeed=-ySpeed;
				}
			}
		}
	}
	
	public void detectBricksCollision()
	{
		Bricks wall = worldView.bricks;
		Brick[] bricks = wall.getBricks();
		float halfWidth = bricks[0].getWidth()/2;
		float halfLength = bricks[0].getLength()/2;
		float x,y,upperLine,leftLine,rightLine,bottomLine,rpi; //describe the bricks;
		for(int i = 0; i<wall.getNumber(); i++){
			if(bricks[i].getLive())
			{
				x = bricks[i].getX();
				y = bricks[i].getY();
				if((Math.abs(y-this.y)>halfWidth+this.ballRadius)||Math.abs(x-this.x)>halfLength+this.ballRadius)
					continue; //for improving the efficiency 
				upperLine = y - halfWidth;;
				leftLine = x - halfLength;
				rightLine = x + halfLength;
				bottomLine = y + halfWidth;
				rpi = (float) (0.707*ballRadius);
				if((this.x+rpi)>leftLine&&(this.x-rpi)<rightLine)
				{
					if(ySpeed>0&&this.y+ballRadius>=upperLine&&this.y-ballRadius<=upperLine)
					{	
						ySpeed=-ySpeed;
						bricks[i].eliminateBrick();
						continue;
					}
					if(ySpeed<0&&this.y-ballRadius<=bottomLine&&this.y+ballRadius>=bottomLine)
					{
						ySpeed=-ySpeed;
						bricks[i].eliminateBrick();
						continue;
					}
				}
				if(this.y+rpi>=upperLine&&this.y-rpi<=bottomLine)
				{
					if(xSpeed>0&&this.x+ballRadius>=leftLine&&this.x<=leftLine)
					{
						xSpeed=-xSpeed;
						bricks[i].eliminateBrick();
						continue;
					}
					if(xSpeed<0&&this.x-ballRadius>=rightLine&&this.x<=rightLine)
					{
						xSpeed=-xSpeed;
						bricks[i].eliminateBrick();
						continue;
					}
					
				}
			/*	else if(x+0.707*ballRadius>=leftLine&&x<leftLine&&xSpeed>0) //0.707 = sqrt(2)/2
				{
					if(y<=upperLine&&y+ballRadius>=upperLine)
					{
						xSpeed=-xSpeed;
						ySpeed=-ySpeed;
					}
				}
				else if(x-0.707*ballRadius<=rightLine&&x>rightLine&&xSpeed<0)
				{
					if(y<=upperLine&&y+ballRadius>=upperLine)
					{
						xSpeed=-xSpeed;
						ySpeed=-ySpeed;
					}
				}
			*/	
				
			}
		}
	}
	
	
	public void moveBall() {
		x = x + xSpeed;
		y = y + ySpeed;	
	}
   
    public void onDraw(Canvas canvas) {
    	Paint paint = new Paint();
    	paint.setAntiAlias(true);
    	paint.setColor(Color.WHITE);
    	if(WorldView.pause==false)
    		{
    			updatePhysics();
    			moveBall();
    		}

    	if(worldView.onScreen) {
    		updatePosition(x, y);
    		canvas.drawCircle(x, y, ballRadius, paint);
    	}
    }
    
	private void generateGameOverDialog() {
		
		Context context = worldView.getContext();
		final MainActivity mainActivity = worldView.getActivity();
  		final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
  	  	
  		// set title
  		dialog.setTitle("Game Over");
  	 
  		// set dialog message
  		dialog.setMessage("You Lose!");
  	    dialog.setCancelable(false);
  		dialog.setNeutralButton("OK",new DialogInterface.OnClickListener() 
  			{
  				public void onClick(DialogInterface dialog,int id) 
  					{
  					    // if this button is clicked, close
  					    // current activity
  					    	   
  					    dialog.dismiss(); 					  
  					    worldView.setRunning(false); //set the running to false 
  					    //in order to exit the while loop below and exit the while lopp in 
  					    //worldView and finish the activity.
  				    }
  			}
  	   );
  	 // create alert dialog
  	 //AlertDialog alertDialog = dgb.create();
  	 

  	 
  	mainActivity.runOnUiThread(new Runnable() {   // Use the context here
        public void run() {
        	dialog.show();
        }
    }
    );
  	while(worldView.getRunning())
  		;
	
}
    
}