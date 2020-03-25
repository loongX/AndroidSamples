package com.slzr.slmanager.update;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pxl on 2020/3/17 0017 上午 9:51.
 * Describe: 安装apk
 */
public class SilentInstall {
    final String TAG = SilentInstall.class.getSimpleName();
    private int mSessionId = -1;
    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     * @param apkPath
     *          要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
//            Process process = Runtime.getRuntime().exec("su");
            Log.i(TAG, "静默安装，路径：" + apkPath + " pm install -r " + apkPath + "\n");
            Process process = Runtime.getRuntime().exec("pm install -r " + apkPath + "\n");
//            Process process = Runtime.getRuntime().exec("getprop ro.product.model");
//            dataOutputStream = new DataOutputStream(process.getOutputStream());
//            // 执行pm install命令
//            String command = "pm install -r " + apkPath + "\n";
//            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
//            dataOutputStream.flush();
//            dataOutputStream.writeBytes("exit\n");
//            dataOutputStream.flush();
//            process.waitFor();
//            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String msg = "";
//            String line;
//            // 读取命令的执行结果
//            while ((line = errorStream.readLine()) != null) {
//                msg += line;
//            }
//            Log.d("TAG", "install msg is " + msg);
//            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
//            if (!msg.contains("Failure")) {
//                result = true;
//            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }


    /**
     * Android 9 的静默安装方式
     * @param apkFilePath
     * @param packageContext
     * @param packageManager
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void installApp(String apkFilePath, Context packageContext, PackageManager packageManager) {
        Log.d(TAG, "installApp()------->" + apkFilePath);
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            Log.d(TAG, "文件不存在");

        }

        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo != null) {
            String packageName = packageInfo.packageName;
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.d(TAG, "packageName=" + packageName + ", versionCode=" + versionCode + ", versionName=" + versionName);

        }

        PackageInstaller packageInstaller = packageManager.getPackageInstaller();
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
            boolean copySuccess = onTransfesApkFile(apkFilePath, packageManager);
            Log.d(TAG, "copySuccess---->" + copySuccess);
            if (copySuccess) {
                execInstallAPP(packageContext, packageManager);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean onTransfesApkFile(String apkFilePath, PackageManager packageManager) {
        Log.d(TAG, "---------->onTransfesApkFile()<---------------------");
        InputStream in = null;
        OutputStream out = null;
        PackageInstaller.Session session = null;
        boolean success = false;
        try {
            File apkFile = new File(apkFilePath);
            session = packageManager.getPackageInstaller().openSession(mSessionId);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void execInstallAPP(Context packageContext, PackageManager packageManager) {
        Log.d(TAG, "--------------------->execInstallAPP()<------------------");
        PackageInstaller.Session session = null;
        try {
            session = packageManager.getPackageInstaller().openSession(mSessionId);
            Intent intent = new Intent(packageContext, InstallResultReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(packageContext,
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

}
