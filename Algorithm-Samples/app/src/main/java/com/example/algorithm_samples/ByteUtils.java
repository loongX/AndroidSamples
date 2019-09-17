package com.example.algorithm_samples;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Created by pxl on 2019/9/17 0017 下午 4:52.
 * Describe:
 */
public class ByteUtils {

    /**
     * 累计和校验，每个byte代表一个数字，然后相加
     *
     * @param bytes
     * @return
     */
    public static int checkSum(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += (bytes[i] & 0xFF);
        }
        return value;
    }

    /**
     * 16进制String进去
     *
     * @param hexdata
     * @return
     */
    public static String makeChecksum(String hexdata) {
        if (hexdata == null || hexdata.equals("")) {
            return "00";
        }
        hexdata = hexdata.replaceAll(" ", "");
        int total = 0;
        int len = hexdata.length();
        if (len % 2 != 0) {
            return "00";
        }
        int num = 0;
        while (num < len) {
            String s = hexdata.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        return hexInt(total);
    }


    private static String hexInt(int total) {
        int a = total / 256;
        int b = total % 256;
        if (a > 255) {
            return hexInt(a) + format(b);
        }
        return format(a) + format(b);
    }


    private static String format(int hex) {
        String hexa = Integer.toHexString(hex);
        int len = hexa.length();
        if (len < 2) {
            hexa = "0" + hexa;
        }
        return hexa;
    }

    public static void main(String[] args) {

        int intCheck = checkSum("文件版本1".getBytes());
        System.out.println("check: " + intCheck);
    }

}
