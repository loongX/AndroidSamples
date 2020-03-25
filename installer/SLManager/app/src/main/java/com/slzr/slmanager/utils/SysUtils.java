package com.slzr.slmanager.utils;

import android.util.Log;


import java.io.IOException;

/**
 * Created by pxl on 2019/11/20 0020 下午 2:32.
 * Describe: 这个主要是系统io口控制集合
 */
public class SysUtils {
    static final String TAG = SysUtils.class.getName();

    /**
     * 控制蜂鸣器的
     */
    public static void openBeed() {

        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("gpio-test 98 1");
                    Thread.currentThread().sleep(150);
                    Runtime.getRuntime().exec("gpio-test 98 0");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        });

    }

    /**
     * 背光灯
     * /sys/class/leds/aw9106_led/brightness 可以控制
     * cat 255 > brightness 亮度最大
     * cat 0 > brightness  关闭
     * ledtest  4  arg1  arg2
     * //arg1: 代表哪个灯亮，值的范围是0x20~0x25
     * //arg2: 代表亮度值， 值的范围是0x00~0xff
     * eg.
     * ledtest 4  23 55
     * ledtest 4  20 255
     * ledtest 4  21 ff
     * ledtest 4  21 0xff
     * ledtest 4  0x22 0x00
     * 一个char， 十进制，十六进制都可以，0~255
     * <p>
     * ledtest  2  arg1    //调节背光
     * //arg1: 代表背光，值的返回是0~255
     */
    public static void setLED() {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("ledtest 4 0x20 0xff");
                    Runtime.getRuntime().exec("ledtest 4 0x21 0xff");
                    Runtime.getRuntime().exec("ledtest 4 0x22 0xff");
                    Runtime.getRuntime().exec("ledtest 4 0x23 0xff");
                    Thread.currentThread().sleep(150);
                    Runtime.getRuntime().exec("ledtest 4 0x20 0x00");
                    Runtime.getRuntime().exec("ledtest 4 0x21 0x00");
                    Runtime.getRuntime().exec("ledtest 4 0x22 0x00");
                    Runtime.getRuntime().exec("ledtest 4 0x23 0x00");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    public static final String LED1 = "0x20";
    public static final String LED2 = "0x21";
    public static final String LED3 = "0x22";
    public static final String LED4 = "0x23";
    public static final String LED5 = "0x24";
    public static final String LED6 = "0x25";

    /**
     * 设置led亮度
     *
     * @param led
     * @param light
     */
    public static void setLED(String led, String light) {
        if (led == null || light == null) return;

        try {
            Runtime.getRuntime().exec("ledtest 4 " + led + " " + light);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

    }

    /**
     * 屏幕背光程度控制
     * <p>
     * ledtest  2  arg1    //调节背光
     * //arg1: 代表背光，值的返回是0~255
     */
    public static void setBackLight(String light) {
        if (light == null) return;
        try {
            Runtime.getRuntime().exec("ledtest 2 " + light);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }


    /**
     * 关闭摄像头端口
     */
    public static void closeCamera() {
        Log.i(TAG, "closeCamera");
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("gpio-test 66 0 \n");
                    Thread.currentThread().sleep(150);
//                    Runtime.getRuntime().exec("gpio-test 1 0 \n ");
                    Runtime.getRuntime().exec("gpio-test 137 0 \n ");
                    Thread.currentThread().sleep(150);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    /**
     * 打开摄像头端口
     */
    static public void openCamera() {
        Log.i(TAG, "openCamera");
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("gpio-test 66 1 \n ");
                    Thread.currentThread().sleep(150);
//                    Runtime.getRuntime().exec("gpio-test 1 1 \n");
                    Runtime.getRuntime().exec("gpio-test 137 1 \n");
                    Thread.currentThread().sleep(150);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    /**
     * 打开前置摄像头的灯
     */
    public static void openFrontCameraLight() {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("gpio-test 44 1");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    /**
     * 关闭前置摄像头的灯
     */
    public static void closeFrontCameraLight() {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("gpio-test 44 0");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    /**
     * 获取Android发布的版本
     */
    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 获取SDK的API Level
     * @return
     */
    public static int getSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

}
