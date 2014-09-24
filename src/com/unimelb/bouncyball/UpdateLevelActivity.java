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

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class UpdateLevelActivity extends AsyncTask<String, Void, String> {
	private TextView levelField;

	OutputStream out = null;
	InputStream in = null;
	Context ctx;
	File file;
	
	public UpdateLevelActivity(TextView level,File file) {
		this.levelField = level;
		this.file = file;
	}

	@Override
	protected String doInBackground(String... urls) {
		try {

			String url = urls[0];
			StringBuffer sb = new StringBuffer("");

			for (int i = 1; i < 3; i++) {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				request.setURI(new URI(url + i));
				HttpResponse response = client.execute(request);
				BufferedReader in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));

				String line = "";
				while ((line = in.readLine()) != null) {
					sb.append(i+line);
					sb.append('\n');
					break;
				}
				in.close();
			}
			return sb.toString();
		

		} catch (Exception e) {

			e.printStackTrace();
			return "Failed!" + e;
		}

	}

	@Override
	protected void onPostExecute(String result) {
		this.levelField.setText("Data downloaded: " + result);


		FileOutputStream fos;
		if(result == null){
			result = "Data retrives failed";
		}

			try {
				fos = new FileOutputStream(file);
				fos.write(result.getBytes());
				fos.close();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {

			}
		
	}


}
