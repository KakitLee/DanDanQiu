package com.unimelb.bouncyball;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class Bricks {

	private int number;
	//private int rows;
	//private int columns;
	private Brick[] bricks;
    private WorldView worldView;
	
	public Bricks(WorldView worldView,int number){
		this.worldView = worldView;
		this.number = number;
		bricks = new Brick[number];
		float screenWidth = this.worldView.getWidth();
		float screenHeigth = this.worldView.getHeight();
		float length = screenWidth/11; //length of each brick;
		float width = screenHeigth/60; //width of each brick;
		float columnGap = length/11;
		float rowGap = (float) (1.8*width);
		float x = columnGap + length/2;
		float y = 5*rowGap;
		for(int i = 0; i< number; i++)
		{
			bricks[i]=new Brick(worldView,x,y,length,width);
			if((i+1)%10!=0) //means this row is not full
			{
				x = x + length + columnGap;
			}
			else
			{
				x = columnGap + length/2;
				y = y + rowGap + width;
			}
		}
	}

	public Brick[] getBricks(){
		return bricks;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	
	@SuppressLint("WrongCall")
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		for(int i = 0; i<bricks.length; i++)
		{
			bricks[i].onDraw(canvas);
		}
	}
}
