package com.slzr.timetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent = new Intent();
    Intent intentActivity = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_startAnotherApp).setOnClickListener(this);
        findViewById(R.id.btn_stopAnotherApp).setOnClickListener(this);
        intent.setComponent(new ComponentName("com.slzr.slmanager", "com.slzr.slmanager.TimeService"));//设置一个组件名称  同组件名来启动所需要启动Service


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_startAnotherApp:
                //设置要传送的数据
//                android 通过包名启动另外一个app:


//                intent.putExtra("data", "Hello AppService,I am App2Activity");
//                startService(intent);
                testService();
//                Intent intent = new Intent();
//                intent.setPackage("com.slzr.slmanager");
//                intent.setAction("android.intent.action.START_B_SERVICE");
//                startService(intent);
                break;
            case R.id.btn_stopAnotherApp:
//                stopService(intent);
                break;
        }
    }


    public void luandOtherApp(String packname) {
        PackageManager packageManager = getPackageManager();
        if (checkPackInfo(packname)) {
            Intent intent = packageManager.getLaunchIntentForPackage(packname);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "没有安装" + packname, 1).show();
        }

    }

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testService() {
        // 5.0以后的隐式调用方式
        Intent mIntent = new Intent();
        mIntent.setAction("android.intent.action.START_B_SERVICE");
        mIntent.setPackage("com.slzr.slmanager");
        Intent eintent = new Intent(getExplicitIntent(this, mIntent));
        eintent.putExtra("data", "set d d fd d d fd");
        eintent.putExtra("time", 1514766400000L);
//        startService(eintent);
        startForegroundService(eintent);
    }


    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }


}
