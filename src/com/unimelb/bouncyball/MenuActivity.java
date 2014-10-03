package com.unimelb.bouncyball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity{

	private TextView level;
	private TextView data;
	//private ImageButton 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu);
		//level = (TextView) findViewById(R.id.textView1);
		//data = (TextView) findViewById(R.id.textView2);
		
	    File root = android.os.Environment.getExternalStorageDirectory(); 

	    File dir = new File (root.getAbsolutePath() + "/Breakout/Maps");
	    if(!dir.isDirectory())
	    {
	    	dir.mkdirs();
	    }
	    
	}
	
	public void clickNewPlayer(View view) {
        ;
    }
	
    public void clickStartGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
  	public void clickUpdate(View view) {
  		
  		/**
	  	String levelURL = "http://128.199.134.230/level.php?levelID=";
	  	String FILENAME = "Level-" + level + ".map";
		File root = android.os.Environment.getExternalStorageDirectory(); 
		File file = new File (root.getAbsolutePath() + "/Breakout/Maps",FILENAME);
		
		UpdateLevelActivity UA = new UpdateLevelActivity(null, file);
		UA.setLevel(level);
		UA.setContext(context);
		UA.execute(levelURL);  
  		**/
  		
  		String levelURL = "http://128.199.134.230/level.php?levelID=";
  		UpdateAllAvailableLevel updateAll = new UpdateAllAvailableLevel(data); 												
  	  	updateAll.setContext(this);
  	  	updateAll.execute(levelURL);
  	}
  	
  	//Read existing level data
  	public void clickRead(View view) {
  		String FILENAME = "level";
  		File file = new File(getExternalFilesDir(null), FILENAME);
  		StringBuilder text = new StringBuilder();

  		try {
  			BufferedReader br = new BufferedReader(new FileReader(file));
  			String line;

  			while ((line = br.readLine()) != null) {
  				text.append(line);
  				text.append('\n');
  			}
  		} catch (IOException e) {

  		}
  		//level.setText("Data loaded: " + text);
  		Toast.makeText(MenuActivity.this, text, Toast.LENGTH_LONG).show(); 
        
  	}
    
  	
  	public void clickHelp(View view)
  	{
  		;
  	}

  	public void clickExit(View view)
  	{
  		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
  	 
  		// set title
  		alertDialogBuilder.setTitle("Exit");
  	 
  		// set dialog message
  		alertDialogBuilder.setMessage("Are you sure?")
  						  .setCancelable(false)
  					       .setPositiveButton
  					       ("Yes",new DialogInterface.OnClickListener() 
  					       	{
  					    	   public void onClick(DialogInterface dialog,int id) 
  					    	   {
  					    		   // if this button is clicked, close
  					    		   // current activity
  					    		   MenuActivity.this.finish();
  					    	   }
  					       	}
  					       )
  					       .setNegativeButton
  					       ("No",new DialogInterface.OnClickListener() 
  					       	{
  					    	   public void onClick(DialogInterface dialog,int id) 
  					    	   {
  					    		   // if this button is clicked, just close
  					    		   // the dialog box and do nothing
  					    		   dialog.cancel();
  					    	   }
  					       	}
  					       );
  	 // create alert dialog
  	 AlertDialog alertDialog = alertDialogBuilder.create();
  	 
  	 // show it
  	 alertDialog.show();		
  	}
}
