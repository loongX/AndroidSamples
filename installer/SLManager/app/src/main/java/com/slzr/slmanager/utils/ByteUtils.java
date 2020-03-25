package com.slzr.slmanager.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Locale;

public class ByteUtils {

    /**
     * 相当于bcd转码
     *
     * @param b
     * @param size
     * @return
     */
    public static final String byte2hexStr(byte b[], int size) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < size; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    private final static String mHexStr = "0123456789ABCDEF";

    /**
     * BCD转码
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexStr2ByteArray(String hexStr) {
        hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int iTmp = 0x00;
        for (int i = 0; i < bytes.length; i++) {
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (iTmp & 0xFF);
        }
        return bytes;
    }

    /**
     * hex字符串和数组一对一转换，一个数字对应一个byte 比如“0”就对应0x00。
     *
     * @param hexStr
     * @return
     */
    public static byte[] sightHexStr2ByteArray(String hexStr) {
//        hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
        byte[] bytes = new byte[hexStr.length()];

        for (int i = 0; i < bytes.length; i++) {
            String str = hexStr.substring(i, i + 1);
            bytes[i] = (byte) Integer.parseInt(str);
        }
        return bytes;
    }

    public static String sightByteArray2String(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            stringBuilder.append(bytes[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * char 转 byte[] 数组
     *
     * @param c
     * @return
     */
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    public static int bytes2Int(byte[] bytes) {
        int num = 0;
        if (bytes.length == 1) {
            num = bytes[0] & 0xFF;

        } else if (bytes.length == 2) {
            num = bytes[1] & 0xFF;
            num |= ((bytes[0] << 8) & 0xFF00);

        } else if (bytes.length == 3) {
            num = bytes[2] & 0xFF;
            num |= ((bytes[1] << 8) & 0xFF00);
            num |= ((bytes[0] << 16) & 0xFF0000);

        } else {
            num = bytes[3] & 0xFF;
            num |= ((bytes[2] << 8) & 0xFF00);
            num |= ((bytes[1] << 16) & 0xFF0000);
            num |= ((bytes[0] << 24) & 0xFF000000);
        }
        return num;
    }


    /**
     * byte[] 数组转 char
     *
     * @param b
     * @return
     */
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

    /**
     * char[] 数组转为byte[] 数组
     *
     * @param chars
     * @return
     */
    public static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    /**
     * byte[] 数组转为数组 char[]
     *
     * @param bytes
     * @return
     */
    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    /**
     * 截取一部分来倒序
     *
     * @param originArray
     * @param begin
     * @param length      从1开始
     * @return
     */
    public static byte[] arrayReverse(byte[] originArray, int begin, int length) {
        byte[] newArray = new byte[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = originArray[begin + length - i - 1];
        }
        return newArray;
    }

    /**
     * 截取一部分
     *
     * @param originArray
     * @param begin       从0开始数起
     * @param length      从1开始
     * @return
     */
    public static byte[] cutArray(byte[] originArray, int begin, int length) {
        byte[] newArray = new byte[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = originArray[begin + i];
        }
        return newArray;
    }

    /**
     * 累计和校验，每个byte代表一个数字，然后相加
     *
     * @param bytes
     * @return
     */
    public static int checkSum(byte[] bytes) {
        if (bytes == null) return -1;
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value += (bytes[i] & 0xFF);
        }
        return value;
    }


    public static byte[] ASCII_To_BCD(byte[] ascii) {
        int asc_len = ascii.length;
        byte[] bcd = new byte[asc_len / 2 + 1];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = ASCII_To_BCD(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : ASCII_To_BCD(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte ASCII_To_BCD(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    /**
     * 合并byte[]数组 （不改变原数组）
     *
     * @param byte_1
     * @param byte_2
     * @return 合并后的数组
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    /**
     * 截取byte数组   不改变原数组
     * @param b 原数组
     * @param off 偏差值（索引）
     * @param length 长度
     * @return 截取后的数组
     */
    public static byte[] subByte(byte[] b, int off, int length){
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;
    }


    public static void main(String[] args)  {
        testBytes();
        testGetBytes();
    }

    /**
     * 将所有的字节数组全部写入内存中，之后将其转化为字节数组
     */
    private static void testBytes() {
        String str1 = "132";
        String str2 = "asd";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            os.write(str1.getBytes());
            os.write(str2.getBytes());
            byte[] byteArray = os.toByteArray();
            System.out.println(new String(byteArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *   从内存中读取字节数组
     */
    public static void testGetBytes() {
        String str1 = "132asd";
        byte[] b = new byte[3];
        ByteArrayInputStream in = new ByteArrayInputStream(str1.getBytes());
        try {
            in.read(b);
            System.out.println(new String(b));
            in.read(b);
            System.out.println(new String(b));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
