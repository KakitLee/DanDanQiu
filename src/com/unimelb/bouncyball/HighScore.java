package com.unimelb.bouncyball;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import android.widget.TextView;

public class HighScore extends AsyncTask<String, Void, String> {
	private TextView scoreField;

	OutputStream out = null;
	InputStream in = null;

	public HighScore(TextView scoreField) {
		this.scoreField = scoreField;
	}

	@Override
	protected String doInBackground(String... urls) {
		try {

			String url = urls[0];
			StringBuffer sb = new StringBuffer("");

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			BufferedReader in = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));

			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
				break;
			}
			in.close();

			return sb.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return "Failed!" + e;
		}

	}

	@Override
	protected void onPostExecute(String result) {
		this.scoreField.setText(result);

	}

}
