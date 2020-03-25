package com.demo.double_screen_different_show;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MainActivity extends Activity {
   
	VideoView mVideoView;
	
	View v;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);
        
        Intent it = getIntent();
        if(it!=null){
	        try {
	        	SharedPreferences spf = this.getSharedPreferences("rk",
	    				Context.MODE_PRIVATE);
	    		Editor editor = spf.edit();
	    		editor.putString("rk_video_path", it.getData().getPath());
	    		editor.commit();
	        	
				System.out.println("===>"+it.getData().getPath());
				
				 if(isServiceRunning()){
					 
					 Intent broadCastIntent = new Intent();
						broadCastIntent.setAction("android.set");
						broadCastIntent.putExtra("paramInt", 1);
						sendBroadcast(broadCastIntent);
						
					 finish();
					 
			        }else{
			        	System.out.println("new service ....");
			        	Intent newIntent = new Intent("com.demo.double_screen_different_show.XgguoService");	
			            MainActivity.this.startService(newIntent);
			        }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        
        
        if(isServiceRunning()){
        	System.out.println("this service is runing ....");
        }else{
        	System.out.println("new service ....");
        	Intent newIntent = new Intent("com.demo.double_screen_different_show.XgguoService");	
            MainActivity.this.startService(newIntent);
        }
    	
        v = findViewById(R.id.li);
        mFlag = true;
        if(rk!=null){
			rk.interrupt();
			rk = null;
		}
		rk = new ReadKeyThread();
		rk.start();
        
        findViews();
		init();
		playvideo();
    	}
    private boolean isServiceRunning(){
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (XgguoService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
    
    private void findViews() {
		this.mVideoView = (VideoView) findViewById(R.id.bbvideoview);
		
	}

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	// TODO Auto-generated method stub
    	state = 6;
    	
    	return super.dispatchTouchEvent(ev);
    }

    
	private void init() {
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mediaplayer) {
				mediaplayer.start();
			}
		});

		mVideoView.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer mediaplayer) {
				mVideoView.pause();
				playvideo();
			}
		});

		mVideoView.setOnErrorListener(new OnErrorListener() {
			public boolean onError(MediaPlayer mediaplayer, int i, int j) {
				mVideoView.pause();
				return true;
			}
		});
		MediaController mc = new MediaController(this);
		mc.setVisibility(View.INVISIBLE);
		mVideoView.setMediaController(mc);
	}

	private void playvideo() {
		mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.video));
		mVideoView.start();
	}
	
	
	public void appy(View v){
		Log.d("lzl", "==========appy============");
		setstate(1,"dizhi");
	}
	
	
	public void appt(View v){
		setstate(0,"dizhi");
		Log.d("lzl", "==========appt============");
		
	}
	
	public void appq(View v){
		setstate(2,"dizhi");	
	}
	
	public void setstate(int value,String path){
		SharedPreferences spf = this.getSharedPreferences("rk",
				Context.MODE_PRIVATE);
		Editor editor = spf.edit();
		editor.putInt("rk", value);
		editor.putString("rk_video_path", path);
		editor.commit();
		
		Intent broadCastIntent = new Intent();
		broadCastIntent.setAction("android.set");
		broadCastIntent.putExtra("paramInt", value);
		sendBroadcast(broadCastIntent);
	}
	
	boolean mFlag = false;
	ReadKeyThread rk;
	int state = 6;
	public class ReadKeyThread extends Thread {
    	public void run() {
    		super.run();
    		
			while (mFlag) {
				if(state == 5){
					Log.d("lzl", "==========state == 5==========");
					rkhandler.sendMessage(rkhandler.obtainMessage(1000,"0"));
				}				
				if(state == 0){
					rkhandler.sendMessage(rkhandler.obtainMessage(1000,"1"));
				}				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				state =state >=0?(state -1):-1;
			}
    	}  	
    }
	
	
	private Handler rkhandler = new Handler() {

  		public void handleMessage(Message msg) {
  			if(msg.obj.toString().equals("1")){
  				v.setVisibility(View.GONE);
  			}else{
  				v.setVisibility(View.VISIBLE);
  			}
  		}
    };
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mFlag = false;
        if(rk!=null){
			rk.interrupt();
			rk = null;
		}
	}
	
}
