package com.unimelb.bouncyball;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;



public class downLoadLevel extends Activity {

	PopupWindow popUp;
    LayoutParams params;
    LinearLayout mainLayout;
    LinearLayout layout;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
         
        onShowDialog(); 
    }  
   
    public void onShowDialog(){  
   
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  
        builder.setTitle("Do you want to download new level?");  
        builder.setPositiveButton("yes", new yesListener());  
        builder.setNegativeButton("no", new noListener());  
        AlertDialog ad = builder.create();  
        ad.show();  
   
    }  

    private class yesListener implements DialogInterface.OnClickListener{  
   
        @Override  
        public void onClick(DialogInterface dialog, int which) {  
   
        }  
   
    }  
    
    private class noListener implements DialogInterface.OnClickListener{  
    	   
        @Override  
        public void onClick(DialogInterface dialog, int which) {  
   
        }  
   
    }  
}
