package com.example.mediaplayer_samples;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button openAssetMusic;
    private MediaPlayer mediaPlayer;
    private Button pause;
    private Button openRawMusic;
    private Button pauseRawMusic;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        openAssetMusic = findViewById(R.id.openAssetMusic);

        openAssetMusic.setOnClickListener(this);
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(this);
        openRawMusic = findViewById(R.id.openRawMusic);
        openRawMusic.setOnClickListener(this);
        pauseRawMusic = findViewById(R.id.pauseRawMusic);
        pauseRawMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openAssetMusic:
                //开始播放
                openAssetMusics();
                break;
            case R.id.pause:
                //暂停播放
                mediaPlayer.pause();
                break;
            case R.id.openRawMusic:
                //开始播放
                openRawMusicS();
                break;
            case R.id.pauseRawMusic:
                //暂停播放
                mediaPlayer.pause();
                break;
        }
    }

    /**
     * 打开raw目录下的音乐mp3文件
     */
    private void openRawMusicS() {
//        mediaPlayer = MediaPlayer.create(this, R.raw.please_get_close);
        //用prepare方法，会报错误java.lang.IllegalStateExceptio
        //mediaPlayer.prepare();
//        mediaPlayer.start();
    }

    /**
     * 打开assets下的音乐mp3文件
     */
    private void openAssetMusics() {

        try {
            //播放 assets/a2.mp3 音乐文件
            AssetFileDescriptor fd = getAssets().openFd("dong.wav");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
