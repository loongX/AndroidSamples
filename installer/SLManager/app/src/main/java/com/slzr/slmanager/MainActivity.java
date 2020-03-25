package com.slzr.slmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.slzr.slmanager.utils.SysUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btCamera;
    boolean isOpenCarmera = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCamera = findViewById(R.id.button_camera);
        btCamera.setOnClickListener(this);
        btCamera.setText("打开摄像头");
        btCamera.setBackgroundColor(Color.parseColor("#f15a22"));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_camera){
            if (isOpenCarmera){
                SysUtils.closeCamera();
                btCamera.setText("打开摄像头");
                btCamera.setBackgroundColor(Color.parseColor("#f15a22"));
                isOpenCarmera = false;
            } else {
                SysUtils.openCamera();
                btCamera.setText("关闭摄像头");
                btCamera.setBackgroundColor(Color.parseColor("#009ad6"));
                isOpenCarmera = true;
            }
        }
    }
}
