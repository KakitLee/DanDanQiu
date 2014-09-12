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
import android.util.Log;
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

	private final String TAG = "Main";
	private WorldView worldView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Force device to stay in portrait orientation
		requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove banner from the top of the activity
		setContentView(R.layout.activity_main); //Set the layout to activity_main
		worldView = (WorldView) findViewById(R.id.worldView);
	}
	
	
}
	/*
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            String name = device.getName();
	            Log.d("Bluetooth Device:", name);
	        }
	    }
	};
	
    private class ServerThread extends Thread {
        private final BluetoothServerSocket myServSocket;

        public ServerThread() {
            BluetoothServerSocket tmp = null;

            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("myServer", MY_UUID);
            } catch (IOException e) {
                Log.e("Bluetooth", "Server establishing failed");
            }

            myServSocket = tmp;
        }

        public void run() {
            Log.e("Bluetooth", "Begin waiting for connection");
            BluetoothSocket connectSocket = null;
            InputStream inStream = null;
            OutputStream outStream = null;
            String line = "";

            while (true) {
                try {
                    connectSocket = myServSocket.accept();
                    mBluetoothAdapter.cancelDiscovery();
                } catch (IOException e) {
                    Log.e("Bluetooth", "Connection failed");
                    break;
                }

                try {
                    inStream = connectSocket.getInputStream();
                    outStream = connectSocket.getOutputStream();
                    worldView.outputStream = outStream;
                    worldView.connected = true;
                    BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                    
                    while ((line = br.readLine()) != null) {
                        try {
                            if(line.startsWith("ShowOnScreen")) {
                            	worldView.onScreen = true;
                            	List<String> coords = Arrays.asList(line.split(","));
                            	
                            	float screenWidth = Float.parseFloat(coords.get(1));
                            	float screenHeight = Float.parseFloat(coords.get(2));
                            	float x = Float.parseFloat(coords.get(3));
                            	float y = Float.parseFloat(coords.get(4));
                            	float xSpeed = Float.parseFloat(coords.get(5));
                            	float ySpeed = Float.parseFloat(coords.get(6));
                            	
                            	worldView.ball.resetCoords(screenWidth, screenHeight, x, y, xSpeed, ySpeed);
                            }
                        	
                            Log.e("Bluetooth", "Received: " + line);
                        } catch (Exception e3) {
                            Log.e("Bluetooth", "Disconnected");
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

            }
        }
    }
    
    private class ConnectThread extends Thread {
        private final BluetoothSocket mySocket;

        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;

            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e("Bluetooth", "Could not connect");
            }
            mySocket = tmp;
        }

        public void run() {
            InputStream inStream = null;
            OutputStream outStream = null;
            mBluetoothAdapter.cancelDiscovery();

            try {
                mySocket.connect();
            } catch (IOException e) {
                Log.e("Bluetooth", this.getName() + ": Could not establish connection with device");
                try {
                    mySocket.close();
                } catch (IOException e1) {
                    Log.e("Bluetooth", this.getName() + ": could not close socket", e1);
                    this.destroy();
                }
            }

            String line = "";
            try {
                inStream = mySocket.getInputStream();
                outStream = mySocket.getOutputStream();
                
                BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                worldView.outputStream = outStream;
                
                while ((line = br.readLine()) != null) {
                    try {
                        Log.e("Bluetooth", "Received: " + line);
                        
                        if(line.startsWith("ShowOnScreen")) {
                        	worldView.onScreen = true;
                        	List<String> coords = Arrays.asList(line.split(","));
                        	
                        	float screenWidth = Float.parseFloat(coords.get(1));
                        	float screenHeight = Float.parseFloat(coords.get(2));
                        	float x = Float.parseFloat(coords.get(3));
                        	float y = Float.parseFloat(coords.get(4));
                        	float xSpeed = Float.parseFloat(coords.get(5));
                        	float ySpeed = Float.parseFloat(coords.get(6));
                        	
                        	worldView.ball.resetCoords(screenWidth, screenHeight, x, y, xSpeed, ySpeed);
                        }
                    } catch (Exception e3) {
                        Log.e("Bluetooth", "Disconnected");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                mySocket.close();
            } catch (IOException e) {
                Log.e("Bluetooth", this.getName() + ": Could not close socket");
            }
        }
    }
}*/
