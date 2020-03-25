package com.example.myapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    Button button_ok, button_cancel;
    EditText username;
    private int mSessionId = -1;

    private PackageInstaller.SessionCallback mSessionCallback;
    private String TAG = "quiet install test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        Button enable_hub, disable_hub, fsti;
        final TextView mMsgText;
        enable_hub = findViewById(R.id.enable_hub);
        disable_hub = findViewById(R.id.disable_hub);
        fsti = findViewById(R.id.test_fstiService);

        mMsgText = (TextView) findViewById(R.id.textView4);
        enable_hub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process process = Runtime.getRuntime().exec("gpio-test 137 1");
                    Process process1 = Runtime.getRuntime().exec("gpio-test 66 1");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        disable_hub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Process process = Runtime.getRuntime().exec("gpio-test 137 0");
                    Process process1 = Runtime.getRuntime().exec("gpio-test 66 0");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        fsti.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                try {
//
//
//                    Log.i(TAG, "fsti.setOnClickListener 1");
//                    Process process1 = Runtime.getRuntime().exec("fstiService");
//                    BufferedReader mReader = new BufferedReader(new InputStreamReader(process1.getInputStream()));
//                    StringBuffer mRespBuff = new StringBuffer();
//                    char[] buff = new char[1024];
//                    int ch = 0;
//                    while ((ch = mReader.read(buff)) != -1) {
//                        mRespBuff.append(buff, 0, ch);
//                    }
//                    Log.i(TAG, "fsti.setOnClickListener 2");
//                    mReader.close();
//                    mMsgText.setText(mRespBuff.toString());
//                } catch (IOException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                }

//                installApp("/sdcard/ESF3000N/cardTest_20200115_addbutton.apk");
                installApp("/sdcard/ESF3000N/ESF3000N.apk");
//                installApp("/sdcard/ESF3000N/installTest.apk");

            }
        });
    }

    private class InstallSessionCallback extends PackageInstaller.SessionCallback {

        @Override

        public void onCreated(int sessionId) {

            // empty

            Log.d(TAG, "onCreated()" + sessionId);

        }


        @Override

        public void onBadgingChanged(int sessionId) {

            // empty

            Log.d(TAG, "onBadgingChanged()" + sessionId + "active");

        }


        @Override

        public void onActiveChanged(int sessionId, boolean active) {

            // empty

            Log.d(TAG, "onActiveChanged()" + sessionId + "active" + active);

        }


        @Override

        public void onProgressChanged(int sessionId, float progress) {

            Log.d(TAG, "onProgressChanged()" + sessionId);

            if (sessionId == mSessionId) {

                int progres = (int) (Integer.MAX_VALUE * progress);

                Log.d(TAG, "onProgressChanged" + progres);

            }

        }


        @Override

        public void onFinished(int sessionId, boolean success) {

            // empty, finish is handled by InstallResultReceiver

            Log.d(TAG, "onFinished()" + sessionId + "success" + success);

            if (mSessionId == sessionId) {

                if (success) {

                    Log.d(TAG, "onFinished() 安装成功");

                } else {

                    Log.d(TAG, "onFinished() 安装失败");

                }


            }

        }

    }

    public void init() {

        mSessionCallback = new InstallSessionCallback();

        getPackageManager().getPackageInstaller().registerSessionCallback(mSessionCallback);

    }
//////////



    public void installApp(String apkFilePath) {
        Log.d(TAG, "installApp()------->" + apkFilePath);
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            Log.d(TAG, "文件不存在");

        }

        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo != null) {
            String packageName = packageInfo.packageName;
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.d("ApkActivity", "packageName=" + packageName + ", versionCode=" + versionCode + ", versionName=" + versionName);

        }

        PackageInstaller packageInstaller = getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams
                = new PackageInstaller.SessionParams(PackageInstaller
                .SessionParams.MODE_FULL_INSTALL);

        Log.d(TAG, "apkFile length" + apkFile.length());
        sessionParams.setSize(apkFile.length());

        try {
            mSessionId = packageInstaller.createSession(sessionParams);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "sessionId---->" + mSessionId);
        if (mSessionId != -1) {
            boolean copySuccess = onTransfesApkFile(apkFilePath);
            Log.d(TAG, "copySuccess---->" + copySuccess);
            if (copySuccess) {
                execInstallAPP();
            }
        }

    }

    private boolean onTransfesApkFile(String apkFilePath) {
        Log.d(TAG, "---------->onTransfesApkFile()<---------------------");
        InputStream in = null;
        OutputStream out = null;
        PackageInstaller.Session session = null;
        boolean success = false;
        try {
            File apkFile = new File(apkFilePath);
            session = getPackageManager().getPackageInstaller().openSession(mSessionId);
            out = session.openWrite("base.apk", 0, apkFile.length());
            in = new FileInputStream(apkFile);
            int total = 0, c;
            byte[] buffer = new byte[1024 * 1024];
            while ((c = in.read(buffer)) != -1) {
                total += c;
                out.write(buffer, 0, c);
            }
            session.fsync(out);
            Log.d(TAG, "streamed " + total + " bytes");
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private void execInstallAPP() {
        Log.d(TAG, "--------------------->execInstallAPP()<------------------");
        PackageInstaller.Session session = null;
        try {
            session = getPackageManager().getPackageInstaller().openSession(mSessionId);
            Intent intent = new Intent(this, InstallResultReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    1, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            session.commit(pendingIntent.getIntentSender());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != session) {
                session.close();
            }
        }
    }

    /////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

