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
	public void onDraw(Canvas canvas) throws Exception {
		// TODO Auto-generated method stub
		int count = 0;
		for(int i = 0; i<bricks.length; i++)
		{
			count = count + bricks[i].onDraw(canvas);
		}
		if(count == 0)
		{
			throw new Exception("load a new map");
		}
	}

	


	/**After designing a new array of bricks(new maps) then call toJSON,
	*  we turn it to string(A) and save at the server, then when the user ask 
	*  for downloading a new maps, server send the String A to the client, 
	*  then client just call the FromJSON then the string turn to an array of 
	*  bricks again. then we can onDraw the bricks.  
	**/
	
	public String ToJSON()
	{ 
		String MessageType="NewMap";
		JSONObject obj=new JSONObject();
		JSONArray bricksJSON=new JSONArray();
		try {
			for(int i = 0; i<bricks.length;i++)
			{
				JSONObject ABrick=new JSONObject();
				ABrick.put("xPosition", Float.toString(bricks[i].getX()/worldView.getWidth()));
				ABrick.put("yPosition", Float.toString(bricks[i].getY()/worldView.getHeight()));
				ABrick.put("HitTimes", bricks[i].getHitTimes());
				bricksJSON.put(ABrick);
			}
			obj.put("Type",MessageType);
			obj.put("Number", bricks.length);	
			obj.put("Length",Float.toString(bricks[0].getLength()));//assume all bricks have same length
			obj.put("Width",Float.toString(bricks[0].getWidth()));//assume all bricks have same width
			obj.put("Data", bricksJSON);
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
				number = (Integer) jsonRoot.get("Number");
				bricksJSON = jsonRoot.getJSONArray("Data");
				bricks = new Brick[number];
				float length = Float.valueOf((String) jsonRoot.get("Length"));
				float width = Float.valueOf((String) jsonRoot.get("Width"));
				for(int i = 0; i<number; i++)
				{
					bricks[i] = new Brick(this.worldView);
					temp = bricksJSON.getJSONObject(i);
					bricks[i].setX(Float.valueOf(temp.getString("xPosition"))*worldView.getWidth());
					bricks[i].setY(Float.valueOf(temp.getString("yPosition"))*worldView.getHeight());
					bricks[i].setHitTimes(temp.getInt("HitTimes"));
					bricks[i].setLength(length);
					bricks[i].setWidth(width);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
