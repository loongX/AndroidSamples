package com.example.algorithm_samples;

import java.util.zip.CRC32;

/**
 * CRC校验
 */
public class CRCUtils {

    /**
     * CRC32 计算
     * @param str
     * @return
     */
    public static String calcCRC32(String str) {
        CRC32 c32 = new CRC32();
        c32.update(str.getBytes());
        String hex = Long.toHexString(c32.getValue());
        return hex;
    }

    /**
     * 测试用例
     * @param args
     */
    public static void main(String[] args) {
        String str = "hello";
        String hex = calcCRC32(str);
        System.out.println("原文：" + str);
        System.out.println("CRC-32处理后：" + hex);
    }
}
