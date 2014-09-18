package com.unimelb.bouncyball;

import android.os.Bundle;
import android.app.Activity;
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
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Window;

import java.io.BufferedReader;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Force device to stay in portrait orientation
		requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove banner from the top of the activity
		setContentView(R.layout.activity_main); //Set the layout to activity_main
		worldView = (WorldView) findViewById(R.id.worldView);
		Intent intent = new Intent(this,BGM.class);  
		startService(intent);  
	}
	
	protected void onStop() {  
	    // TODO Auto-generated method stub  
	    Intent intent = new Intent(this,BGM.class);  
	    stopService(intent);  
	    super.onStop();  
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

