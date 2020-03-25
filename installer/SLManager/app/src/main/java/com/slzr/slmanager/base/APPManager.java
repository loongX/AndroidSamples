package com.slzr.slmanager.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

/**
 * Created by pxl on 2020/3/24 0024 下午 4:02.
 * Describe:
 */
public class APPManager {
    static String TAG = APPManager.class.getSimpleName();

    public static String esfPackageName = "com.slzr.esf3000n";
    public static boolean startESF = true;
    public static void openApp(Context packageContext,PackageManager packageManager, String packageName) {
        //这里是判断APP中是否有相应APP的方法
        PackageInfo packageInfo = null;
        packageName = esfPackageName;
        Log.i(TAG, "openApp 1");
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "openApp 2");
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        Log.i(TAG, "openApp 3");
        ResolveInfo resolveInfo = apps.iterator().next();
        if (resolveInfo != null) {
            packageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            packageContext.startActivity(intent);
        }
        Log.i(TAG, "openApp 4");
    }
}
