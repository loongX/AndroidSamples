package com.slzr.screen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;

public class MainActivity extends AppCompatActivity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 11;
    private DifferentDislay mPresentation;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (!Settings.canDrawOverlays(this)){
        //没有悬浮窗权限m,去开启悬浮窗权限
//        try {
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        } else {
//            Log.i("main","2222");
//            DisplayManager manager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
//            Display[] displays = manager.getDisplays();
//            // displays[0] 主屏
//            // displays[1] 副屏
//            DifferentDislay differentDislay = new DifferentDislay(this,displays[1]);
//            differentDislay.getWindow().setType(
//                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            differentDislay.show();
//        }

        showOtherScreen();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showOtherScreen() {
        DisplayManager manager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = manager.getDisplays();
        // displays[0] 主屏
        // displays[1] 副屏
        DifferentDislay differentDislay = new DifferentDislay(this, displays[1]);

        //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            differentDislay.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else {
            differentDislay.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        differentDislay.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                    //有悬浮窗权限开启服务绑定 绑定权限
//                    Intent intent = new Intent(MainActivity.this, FBService.class);
//                    startService(intent);
                    showOtherScreen();
                }
            }
        }
    }
}
