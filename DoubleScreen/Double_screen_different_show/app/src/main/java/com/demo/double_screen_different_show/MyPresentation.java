package com.demo.double_screen_different_show;

import java.io.File;
import java.net.URI;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Presentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
@SuppressLint("NewApi") @SuppressWarnings("unused")
public class MyPresentation extends Presentation  implements OnCompletionListener,
OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback
{
//	GLSurfaceView mPreview;
	
	Context mcontext;
	
	 private MediaPlayer mp = new MediaPlayer();
	 
	 public static MediaPlayer mMediaPlayer;
	 public static SurfaceView mPreview;
	private SurfaceHolder holder;
	String video_name=""; 	
	/**
	 * public Presentation (Context outerContext, Display display)
	 * �����ʼ�����������outerContext����Ҫactivity��һ��activity��context��
	 * ��Ȼpresentation�ᴴ���Լ���context�������������������context֮�ϵģ�
	 * �������activity��ת��presentation����ʧ�ˣ����˵��getApplicationContext()��ֱ�ӱ��?Ҳ���С� 
	 */   	
    public MyPresentation(Context outerContext, Display display,String path) {
       super(outerContext, display);  
       this.mcontext=outerContext;
       this.video_name = path;
       releaseMediaPlayer();
   }

    
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);         
       setContentView(R.layout.show);      
       
       mPreview = (SurfaceView) findViewById(R.id.show_view);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		playVideo(mcontext);
   }
   
   public void playVideo(Context mcontext) {
		try {
			SharedPreferences sp = mcontext.getSharedPreferences("rk",
					Context.MODE_PRIVATE);
			int  key= sp.getInt("rk", 0);
			
		    String path= sp.getString("rk_video_path", "dizhi");
			System.out.println("path ===>"+path);
			if (path.equals("dizhi"))
			{
				Log.d("lzl", "=========e.printStackTrace6666====333=====" + path);
				video_name = "/mnt/external_sd/test.mp4";
			}
			
			Log.d("lzl", "=========e.printStackTrace6666=========" + path);
			// ����һ��MediaPlayer����
			if(czVideo(video_name)){
			// ���ò��ŵ���Ƶ���Դ
			mMediaPlayer = new MediaPlayer();
			// ���ò��ŵ���Ƶ���Դ
			mMediaPlayer.setDataSource(video_name);
			mMediaPlayer.prepareAsync();
			Log.d("lzl", "=========e.printStackTrace6666====111=====");
			}else{
				Log.d("lzl", "=========e.printStackTrace6666=======222==");
			mMediaPlayer = MediaPlayer.create(mcontext.getApplicationContext(), R.raw.abcd);
			}
			// ����Ƶ�����SurfaceView
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setLooping(true);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} catch (Exception e) {
			Log.d("lzl", "=========e.printStackTrace=========" + e.toString());
			e.printStackTrace();
		}
	}

  	public boolean czVideo(String path){
  		return new File(path).exists();
  	}
  	
  	
	private void FirstrestartPlayVideo() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		releaseMediaPlayer();
//		LedTime = 500;
		
		
	}

	private void restartPlayVideo() {
		releaseMediaPlayer();

		mPreview = (SurfaceView) findViewById(R.id.show_view);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		playVideo(mcontext);
	}

	
	private void startsmdtPlayVideo() {
		releaseMediaPlayer();
		mPreview = (SurfaceView) findViewById(R.id.show_view);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		playVideo(mcontext);
	}

	public void onCompletion(MediaPlayer arg0) {
		try {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			restartPlayVideo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����Ƶ�Ŀ�͸�
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

	}

	// ʵ��OnPreparedListener�еķ���������Ƶ׼����ϻ��������ص���������ʼ����
	public void onPrepared(MediaPlayer mediaplayer) {
//		Log.d(TAG, "onPrepared");
//
//
//		mIsVideoReadyToBePlayed = true;
//		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
//		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		playVideo(mcontext);
	}


	// �ͷ�MediaPlayer
	public void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
			
		}
		if(holder!=null){
			holder =null;
		}
	}

    
	// ������Ƶ�ķ���
	private void startVideoPlayback() {
//		Log.v(TAG, "startVideoPlayback");
//		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}
}