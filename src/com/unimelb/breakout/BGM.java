package com.unimelb.breakout;

import java.io.IOException;  
  
import android.app.Service;  
import android.content.Intent;  
import android.media.MediaPlayer;  
import android.os.IBinder;  
  
public class BGM extends Service {  
    private MediaPlayer mp;  
    
    private static BGM self = null;
    
    @Override  
    public void onStart(Intent intent, int startId) {  
        // TODO Auto-generated method stub  
        // ��ʼ��������  
    	super.onStart(intent, startId);  
    	self = this;
        mp.start();  
        // ���ֲ�����ϵ��¼�����  
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
  
            public void onCompletion(MediaPlayer mp) {  
                // TODO Auto-generated method stub  
                // ѭ������  
                try {  
                    mp.start();  
                } catch (IllegalStateException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
        });  
        // ��������ʱ����������¼�����  
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {  
  
            public boolean onError(MediaPlayer mp, int what, int extra) {  
                // TODO Auto-generated method stub  
                // �ͷ���Դ  
                try {  
                    mp.release();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
  
                return false;  
            }  
        });  
  
        
    }  
  
    @Override  
    public void onCreate() {  
        // TODO Auto-generated method stub  
        // ��ʼ��������Դ  
        try { 
        	self = this;
            // ����MediaPlayer����  
            mp = new MediaPlayer();  
            // �����ֱ�����res/raw/xingshu.mp3,R.java���Զ����� 
            mp = MediaPlayer.create(BGM.this, R.raw.evolution);  
            // ��MediaPlayerȡ�ò�����Դ��stop()֮��Ҫ׼��PlayBack��״̬ǰһ��Ҫʹ��MediaPlayer.prepeare()  
            mp.prepare();  
        } catch (IllegalStateException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        super.onCreate();  
    }  
  
    
    
    @Override  
    public void onDestroy() {  
        // TODO Auto-generated method stub  
        // ����ֹͣʱֹͣ�������ֲ��ͷ���Դ  
        mp.stop();  
        mp.release();  
  
        super.onDestroy();  
    }  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        // TODO Auto-generated method stub  
       
    	return null;  
    }  
  
    
    public  void pauseBGM()
    {
    	mp.pause();
    }
    
    
    public void resumeBGM()
    {
    	mp.start();
    }
    
    
    public static BGM getServiceObject(){
        return self;
    }
}  
