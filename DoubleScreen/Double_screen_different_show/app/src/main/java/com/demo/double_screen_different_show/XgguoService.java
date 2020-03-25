package com.demo.double_screen_different_show;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;

public class XgguoService extends Service{

	private DisplayManager mDisplayManager;
	private static MyPresentation myPresentation;
	  public IBinder onBind(Intent paramIntent){
	    return null;
	  }

	  @SuppressLint("NewApi") 
	  public void updateContents(int state,String path) {
	        mDisplayManager = (DisplayManager) getSystemService(
	                Context.DISPLAY_SERVICE);
	        Display[] displays = mDisplayManager.getDisplays();
	        System.out.println("hdmi path ==>"+displays.length);
	        showPresentation(displays[state],path);
	    }
	  
	  public void onCreate(){
	    super.onCreate();
	    SharedPreferences sp = XgguoService.this.getSharedPreferences("rk",
				Context.MODE_PRIVATE);
		int  key= sp.getInt("rk", 0);
		
	    String path= sp.getString("rk_video_path", "dizhi");
		if(key == 2){
			
		}else{
			updateContents(key,path);
		}
	    registerMountEvent();	
	  }
	  
	  private void registerMountEvent() {
			IntentFilter localIntentFilter = new IntentFilter();
		    localIntentFilter.addAction("android.set");
		
		    registerReceiver(this.XgguoReceiver, localIntentFilter);
		}
		
		private void nnRegisterMountEvent()
		{
		    unregisterReceiver(this.XgguoReceiver);
		}

	  private BroadcastReceiver XgguoReceiver = new BroadcastReceiver(){
		    @SuppressLint("NewApi") 
		    public void onReceive(Context context, Intent intent){
		    	System.out.println("xxx");
		    	if(intent.getAction().equals("android.set")){
		    		if(intent.getIntExtra("paramInt", 0)== 2){
		    			 if(myPresentation!=null){
		    				  myPresentation.releaseMediaPlayer();
		    				  myPresentation.dismiss();
		    				  myPresentation.cancel();
		    				  myPresentation =null;
		    			  }	
		    		}else{
		    			SharedPreferences sp = XgguoService.this.getSharedPreferences("rk",
		    					Context.MODE_PRIVATE);
		    		    String path= sp.getString("rk_video_path", "dizhi");
		    		    Log.d("lzl", "=======XgguoReceiver====path===================" + path);
		    		updateContents(intent.getIntExtra("paramInt", 0),path);
		    		}
		    	}
		    	registerMountEvent();
		    }
		};
	  
	  
	  @SuppressLint("NewApi") 
	  private void showPresentation(Display display,String path) {
		  
		  if(myPresentation!=null){
			  myPresentation.releaseMediaPlayer();
			  myPresentation.cancel();
			  myPresentation =null;
		  }
	        myPresentation = new MyPresentation(this, display,path);
	        myPresentation.setOnDismissListener(new DialogInterface.OnDismissListener() {
	            @Override
	            public void onDismiss(DialogInterface dialog) {
	                // ������ʧ�����浱ǰ����λ�á�
	            	Log.d("lzl", "=======XgguoReceiver====onDismiss===================");
	            }
	        });
	        myPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
	        myPresentation.show();
//	        presentSurface = myPresentation.getSurface();
//	        presentSurface.getHolder().addCallback(new MySurfaceCallback());
	    }
	  
	  public void onDestroy(){
		  Log.d("lzl", "=======XgguoReceiver====onDestroy===================");
	    super.onDestroy();
	    nnRegisterMountEvent();
	  }
}
