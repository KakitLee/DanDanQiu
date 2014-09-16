package com.unimelb.bouncyball;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

	public Bricks(WorldView worldView)
	{
		this.worldView = worldView;
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

	public String ToJSON()
	{ 
		String MessageType="NewMap";
		JSONObject obj=new JSONObject();
		JSONArray bricksJSON=new JSONArray();
		try {
			for(int i = 0; i<bricks.length;i++)
			{
				JSONObject ABrick=new JSONObject();
				ABrick.put("xPosition", bricks[i].getX());
				ABrick.put("yPosition", bricks[i].getY());
				ABrick.put("HitTimes", bricks[i].getHitTimes());
				ABrick.put("Length",bricks[i].getLength() );
				ABrick.put("Width", bricks[i].getWidth());
				bricksJSON.put(ABrick);
			}
			obj.put("Type",MessageType);
			obj.put("number", bricks.length);
			obj.put("data", bricksJSON);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return obj.toString();
	}
	
	public void FromJSON(String jst)
	{
		try
		{
			JSONObject jsonRoot = new JSONObject(jst);
			JSONObject temp;
			JSONArray bricksJSON=new JSONArray();
			if(jsonRoot.get("Type").equals("NewMap"))
			{	
				number = (Integer) jsonRoot.get("number");
				bricksJSON = jsonRoot.getJSONArray("data");
				bricks = new Brick[number];
				for(int i = 0; i<number; i++)
				{
					bricks[i] = new Brick();
					temp = bricksJSON.getJSONObject(i);
					bricks[i].setX((Float) temp.get("xPosition"));
					bricks[i].setY((Float) temp.get("yPosition"));
					bricks[i].setHitTimes((Integer) temp.get("HitTimes"));
					bricks[i].setLength((Float) temp.get("Length"));
					bricks[i].setWidth((Float) temp.get("Width"));
					bricks[i].setWorldView(this.worldView);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
