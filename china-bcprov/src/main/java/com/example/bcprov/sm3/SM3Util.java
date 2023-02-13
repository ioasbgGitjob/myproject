package com.example.bcprov.sm3;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-13 16:20:08
 * @description SM3属于不可逆加密算法，类似于md5，常用于签名。
 */

public class SM3Util {

    public static String encrypt(String src) throws Exception {
        return encryptByte(src.getBytes());
    }

    public static String encryptByte(byte[] bytes) throws Exception {
        return new String(Hex.encode(encrypt(bytes)));
    }

    public static byte[] encrypt(byte[] bytes) throws Exception {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(bytes, 0, bytes.length);
        byte[] md = new byte[sm3.getDigestSize()];
        sm3.doFinal(md, 0);
        return Hex.encode(md);
    }
}
