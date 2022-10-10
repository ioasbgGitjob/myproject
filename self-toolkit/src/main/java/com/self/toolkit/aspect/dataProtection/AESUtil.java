package com.self.toolkit.aspect.dataProtection;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    public static String aesEncrypt(String password, String aesKey) throws Exception {
        SecretKey key = generateMySQLAESKey(aesKey,"ASCII");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cleartext = password.getBytes("UTF-8");
        byte[] ciphertextBytes = cipher.doFinal(cleartext);
        return Base64.getEncoder().encodeToString(ciphertextBytes);
    }

    public static String aesDecrypt(String content, String aesKey) throws Exception {
        SecretKey key = generateMySQLAESKey(aesKey,"ASCII");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cleartext = Base64.getDecoder().decode(content.getBytes());
        byte[] ciphertextBytes = cipher.doFinal(cleartext);
        return new String(ciphertextBytes, "UTF-8");
    }

    public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) throws Exception {
        final byte[] finalKey = new byte[16];
        int i = 0;
        for (byte b : key.getBytes(encoding)) {
            finalKey[i++ % 16] ^= b;
        }
        return new SecretKeySpec(finalKey, "AES");
    }
}
