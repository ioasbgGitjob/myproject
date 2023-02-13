package com.example.bcprov;

import com.example.bcprov.sm2.SM2Util;
import com.example.bcprov.sm3.SM3Util;
import com.example.bcprov.sm4.SM4Util;

import java.io.File;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-13 16:20:39
 * @description
 */

public class SMUtils {

    private static final String DEFAULT_PUBLIC_KEY = "DEFAULT_PUBLIC_KEY";
    private static final String DEFAULT_PRIVATE_KEY = "DEFAULT_PRIVATE_KEY";
    private static final String DEFAULT_SECRET_KEY = "DEFAULT_SECRET_KEY";


    // SM2 加密
    public static String encryptSM2(String str) throws Exception {
        return encryptSM2(DEFAULT_PUBLIC_KEY, str);
    }

    public static String encryptSM2(String publicKey, String str) throws Exception {
        return SM2Util.encrypt(publicKey, str);
    }

    public static byte[] encryptSM2(byte[] bytes) throws Exception {
        return encryptSM2(DEFAULT_PUBLIC_KEY, bytes);
    }

    public static byte[] encryptSM2(String publicKey, byte[] bytes) throws Exception {
        return SM2Util.encrypt(publicKey, bytes);
    }


    // SM2解密
    public static String decryptSM2(String str) throws Exception {
        return encryptSM2(DEFAULT_PRIVATE_KEY, str);
    }

    public static String decryptSM2(String publicKey, String str) throws Exception {
        return SM2Util.encrypt(publicKey, str);
    }

    public static byte[] decryptSM2(byte[] bytes) throws Exception {
        return decryptSM2(DEFAULT_PRIVATE_KEY, bytes);
    }

    public static byte[] decryptSM2(String publicKey, byte[] bytes) throws Exception {
        return SM2Util.encrypt(publicKey, bytes);
    }

    // SM2 加密文件
    public static byte[] encryptFileSM2(File file) throws Exception {
        return encryptSM2(DEFAULT_PUBLIC_KEY, ByteConvertUtil.file2buf(file));
    }

    public static byte[] decryptFileSM2(File file) throws Exception {
        return decryptSM2(DEFAULT_PUBLIC_KEY, ByteConvertUtil.file2buf(file));
    }


    // SM3 加密
    public static String encryptSM3(String str) throws Exception {
        return SM3Util.encrypt(str);
    }

    public static String encryptSM3(byte[] bytes) throws Exception {
        return SM3Util.encryptByte(bytes);
    }


    // SM4 加解密
    public static String encryptSM4(String str) throws Exception {
        return encryptSM4(DEFAULT_SECRET_KEY, str);
    }

    public static String encryptSM4(String secretKey, String str) throws Exception {
        return new SM4Util(secretKey, null, false).encryptData_ECB(str);
    }

    /**
     *
     * @param str
     * @param hexString 16进制字符串 default false
     * @return
     * @throws Exception
     */
    public static String encryptSM4(String str, boolean hexString) throws Exception {
        return encryptSM4(DEFAULT_SECRET_KEY, str, hexString);
    }

    /**
     *
     * @param secretKey
     * @param str
     * @param hexString  16进制字符串 default false
     * @return
     * @throws Exception
     */
    public static String encryptSM4(String secretKey, String str, boolean hexString) throws Exception {
        return new SM4Util(secretKey, null, hexString).encryptData_ECB(str);
    }


    /**
     * @param str
     * @param vi  初始向量
     * @return
     * @throws Exception
     */
    public static String encryptSM4CBC(String vi, String str) throws Exception {
        return new SM4Util(null, vi, false).encryptData_CBC(str);
    }

    /**
     * @param str
     * @param vi  初始向量
     * @return
     * @throws Exception
     */
    public static String decryptSM4CBC(String vi, String str) throws Exception {
        return new SM4Util(null, vi, false).encryptData_CBC(str);
    }

    /**
     * @param str
     * @param vi        初始向量
     * @param hexString 16进制字符串 default false
     * @return
     * @throws Exception
     */
    public static String encryptSM4CBC(String vi, String str, boolean hexString) throws Exception {
        return new SM4Util(null, vi, hexString).encryptData_CBC(str);
    }

    /**
     * @param str
     * @param vi        初始向量
     * @param hexString 16进制字符串 default false
     * @return
     * @throws Exception
     */
    public static String decryptSM4CBC(String vi, String str, boolean hexString) throws Exception {
        return new SM4Util(null, vi, hexString).encryptData_CBC(str);
    }


}
