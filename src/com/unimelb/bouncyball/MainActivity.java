package com.unimelb.bouncyball;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends Activity {

	private WorldView worldView;
	private static final String TAG="Main";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Force device to stay in portrait orientation
		requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove banner from the top of the activity
		setContentView(R.layout.activity_main); //Set the layout to activity_main
		
		ImageButton pauseButton =(ImageButton) findViewById(R.id.pauseButton);
		pauseButton.setImageResource(R.drawable.pause);
		
		worldView = (WorldView) findViewById(R.id.worldView);
	    worldView.setLevel(1);
		worldView.saveActivity(this);
		//Resources res = getResources();
		//Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.main_game_wall_paper);
		//worldView.setWallPaper(bitmap);
		
		
		
		Intent intent = new Intent(this,BGM.class);  
		startService(intent);  
		Log.i(TAG,"this is onCreate");
	}
	
	protected void onStart()
	{
		super.onStart();
		Log.i(TAG,"this is onStart");
	}
	
	protected void onResume(){
		//Intent intent = new Intent(this,BGM.class);
		//startService(intent);
		super.onResume();
		Log.i(TAG,"this is onResume");
	}
	
	protected void onPause()
	{
		super.onPause();
		Log.i(TAG,"this is onPause");
	}
	
	
	protected void onStop() {  
	    // TODO Auto-generated method stub  
	    Intent intent = new Intent(this,BGM.class);  
	    stopService(intent);  
	    super.onStop();  
	    Log.i(TAG,"this is onStop");
	}  
	
	protected void onDestory(){
		super.onDestroy();
	}
	
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		  Log.i(TAG,"this is onSaveInstanceState");
		  super.onSaveInstanceState(savedInstanceState);
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG,"this is onRestoreInstanceState");
	}
	
    public void clickPauseOrResume(View view) 
    {
    	worldView.pause=!worldView.pause;
    	
    	ImageButton  pauseButton =(ImageButton) findViewById(R.id.pauseButton);
    	if(worldView.pause==false)
    	{
    		pauseButton.setImageResource(R.drawable.pause);
    	    Intent intent = new Intent(this,BGM.class);  
    	    startService(intent);  
    	}
    	else
    	{
    		pauseButton.setImageResource(R.drawable.resume);//the button become resume
    	    Intent intent = new Intent(this,BGM.class);  
    	    stopService(intent); 
    	}
    }
    
    
	/*
	@Override
	public void onConfigurationChanged(Configuration newConfig) {   
	    super.onConfigurationChanged(newConfig);
	    if (this.getResources().getConfiguration().orientation
	            == Configuration.ORIENTATION_LANDSCAPE){
	    }
	    else if(this.getResources().getConfiguration().orientation
	            ==Configuration.ORIENTATION_PORTRAIT) {
	    }
    } 
    */
	

	
}

