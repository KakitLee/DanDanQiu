package com.unimelb.bouncyball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;



public class DownLoadLevel  implements DialogInterface.OnClickListener{  
   
	private boolean comfirm;
	private int level; 
	private boolean pressed = false;
	private Context context;
	private static final String TAG="Main";
	
	public DownLoadLevel(boolean answer, int level)
	{
		comfirm = answer;
		this.level = level;
	}
	
        @Override  
        public void onClick(DialogInterface dialog, int which) {  
        	if(comfirm)//press yes
        	{	
        	  	String levelURL = "http://128.199.134.230/level.php?levelID=";
        	  	String FILENAME = "Level-" + level + ".map";
        		File root = android.os.Environment.getExternalStorageDirectory(); 
        		File file = new File (root.getAbsolutePath() + "/Breakout/Maps",FILENAME);
        		
        		UpdateLevelActivity UA = new UpdateLevelActivity(null, file);
        		UA.setLevel(level);
        		UA.setContext(context);
        		UA.execute(levelURL);        		
        		pressed = true;     		
        	 }
        	else // press no
        	{
        		pressed = true;
        	}
        }  
        
        public boolean isPressed()
        {
        	return pressed;
        }

		public void setContext(Context context) {
			// TODO Auto-generated method stub
			this.context = context;
		}
		
		public Context getContext()
		{
			return context;
		}
        
}
