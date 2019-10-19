package com.example.selfturnon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.selfturnon.MainActivity;


/**
 * Created by pxl on 2019/10/14 0014 上午 9:42.
 * Describe: 广播接收开机启动
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String TAG = "BootBroadcastReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent mBootIntent = new Intent(context, MainActivity.class);
            mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mBootIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
