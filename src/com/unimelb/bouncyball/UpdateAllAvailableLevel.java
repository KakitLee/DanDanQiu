package com.unimelb.bouncyball;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class UpdateAllAvailableLevel extends AsyncTask<String, String, String> {
	
	
	private TextView info;



	private ProgressDialog pg;
	private  int steps = 0;
	private int level;
	private boolean finished;
	private Context context;
	private static final String TAG="Main";
	private int updateCounter = 0;
	
	public  UpdateAllAvailableLevel	(TextView info)
	{
		this.info = info;
	}
	
	@Override
    protected void onPreExecute()
    {
        pg = new ProgressDialog(context);
        pg.setCancelable(false);
        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pg.setProgress(0);
        pg.setMax(100);
        pg.setTitle("Updating Level");
        pg.setMessage("loading...");
        pg.show();
        super.onPreExecute();              
    }; 
	
	
	@Override
	protected String doInBackground(String... urls) {
		try 
		{
			File root = android.os.Environment.getExternalStorageDirectory();
			String FILENAME;
  			File file;
  			FileOutputStream fos;
  			
  			for(int i =1 ; ; i++)
  			{
  				FILENAME = "Level-" + Integer.toString(i) + ".map";
  			
  				file = new File (root.getAbsolutePath() + "/Breakout/Maps",FILENAME);
  				String url = urls[0] + Integer.toString(i);
			
  				if(!file.exists())
  				{
  					//pg.setTitle("Update Level" + Integer.toString(i));
  					pg.setProgress(0);
  					StringBuffer sb = new StringBuffer("");
			
  					HttpClient client = new DefaultHttpClient();
  					HttpGet request = new HttpGet();
  					request.setURI(new URI(url));
			
  					publishProgress("Accessing Server");
			
  					HttpResponse response = client.execute(request);
			
			
  					publishProgress("Reading Data");
			
			
  					BufferedReader in = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
			
			
  					String line = in.readLine(); 
			
  					publishProgress("Reading Finish");
		
  					in.close();
				
  					if(!line.equals("The data is failed to be retrieved !"))
  					{
  						
  						try {
  							updateCounter++;
  							
  							publishProgress("Writing Data To Local File");
							
  							fos = new FileOutputStream(file);
  							fos.write(line.getBytes());
  							fos.close();
					
  							publishProgress("Writing Finished");
  							
  							publishProgress("Level "+Integer.toString(i) +" Updata Finish");
					
  							continue;
  							
  						} catch (Exception e) {
						e.printStackTrace();
  						} finally {
						;
  						}
					}
  					else
  					{
  						publishProgress("No More New Level");
  						publishProgress("Canceling Update");
  						publishProgress("Level "+Integer.toString(i) +" Updata Canceled");
  						break;
  					}
			}
		}
  			return "complete";
		} catch (Exception e) {
			return null;
		}

	}

	@Override
    protected void onProgressUpdate(String... progress) {  
		Log.i(TAG,"8");
		super.onProgressUpdate(progress);
        if (pg != null) {
        	steps=steps+1;
            pg.setMessage(progress[0]);
            pg.setProgress(Math.round(100*((float)steps)/6));
            pg.show();
            if(steps == 6)
            {	
            	pg.dismiss();
            	steps = 0;
            }
        }
    }  
	
	@Override
	protected void onPostExecute(String result) {
		
		super.onPostExecute(result);

		
		
		if(result == null)
		{
			info.setText("Error Occured");
		}	
		else
		{
			if(updateCounter>0)
				info.setText(Integer.toString(updateCounter) + " Levels Updated");
			else
				info.setText("No New Level Available");
		}
		if(pg!=null)
			pg.dismiss();
		finished = true;
	}

	public void setLevel(int Newlevel) {
		// TODO Auto-generated method stub
		level = Newlevel;
	}

	public void setContext(Context context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
	
	public Context getContext()
	{
		return context;
	}

	public ProgressDialog getProgressDialog()
	{
		return pg;
	}
	
	public void dismissDialog()
	{
		if(pg!=null)
			pg.dismiss();
	}


	public boolean isRunning() {
		// TODO Auto-generated method stub
		if( pg!=null&&pg.isShowing())
			return true;
		else
			return false;
	}


	public boolean finished() {
		// TODO Auto-generated method stub
		return finished;
	}
	

}
