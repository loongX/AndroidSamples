package com.ppdai.lwcamerademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by liwen on 2017/6/8.
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mIVData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);


        mIVData = (ImageView) findViewById(R.id.iv_data);

        startActivityForResult(new Intent(this, PhotoActivity.class), 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mIVData.setImageBitmap(Config.Image);

    }
}
