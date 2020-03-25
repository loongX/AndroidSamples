package com.slzr.slmanager;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TimeService extends IntentService {

    private static final long EARLIEST_SUPPORTED_TIME = 1514764800000L;//20180101 00:00:00

    public TimeService() {
        super("SetTimeIntentService");//IntentService
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (null != intent) {
            long time = intent.getLongExtra("time", 0L);
            Log.i("TimeService", "onHandleIntent接收到的数据是:" +time);
            if (time > 0) {
                SystemClock.setCurrentTimeMillis(time);
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_app";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "MyApp", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();
            startForeground(1, notification);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (null != intent) {
//            Log.i("AppService", "onStartCommand接收到的数据是:" + intent.getStringExtra("data"));
//        }
        return super.onStartCommand(intent, flags, startId);
    }


}
