package com.example.bcprov.sm4;

import com.example.bcprov.ByteConvertUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author szy
 * @version 1.0
 * @date 2023-02-13 16:40:42
 * @description SM4属于对称加密算法，可用于替代DES/AES等国际算法， SM4算法与AES算法具有相同的密钥长度和分组长度，都是128位。<p/>
 * <p>
 * 1.电码本模式（Electronic Codebook Book (ECB)）；
 * 2.密码分组链接模式（Cipher Block Chaining (CBC)）；
 * 3.计算器模式（Counter (CTR)）；
 * 4.密码反馈模式（Cipher FeedBack (CFB)）；
 * 5.输出反馈模式（Output FeedBack (OFB)）。
 * https://www.cnblogs.com/starwolf/p/3365834.html
 */

public class SM4Util {
    // ECB 密钥(default)
    public String secretKey = "";
    // CBC 密钥
    public String iv = "";

    public boolean hexString = true;

    public SM4Util() {
    }

    public SM4Util(String secretKey, String iv, boolean hexString) {
        this.secretKey = secretKey;
        this.iv = iv;
        this.hexString = hexString;
    }

    public String encryptData_ECB(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = ByteConvertUtil.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));
            return ByteConvertUtil.byteToHex(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            byte[] encrypted = ByteConvertUtil.hexToByte(cipherText);
            cipherText = Base64.encodeBase64String(encrypted);
            ;
            //cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }

            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            if (hexString) {
                keyBytes = ByteConvertUtil.hexStringToBytes(secretKey);
            } else {
                keyBytes = secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64.decodeBase64(cipherText));
            //byte[] decrypted = sm4.sm4_crypt_ecb(ctx, new BASE64Decoder().decodeBuffer(cipherText));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encryptData_CBC(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_ENCRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = ByteConvertUtil.hexStringToBytes(secretKey);
                ivBytes = ByteConvertUtil.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("UTF-8"));
            return ByteConvertUtil.byteToHex(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText) {
        try {
            byte[] encrypted = ByteConvertUtil.hexToByte(cipherText);
            cipherText = Base64.encodeBase64String(encrypted);
            ;
            //cipherText = new BASE64Encoder().encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = SM4.SM4_DECRYPT;

            byte[] keyBytes;
            byte[] ivBytes;
            if (hexString) {
                keyBytes = ByteConvertUtil.hexStringToBytes(secretKey);
                ivBytes = ByteConvertUtil.hexStringToBytes(iv);
            } else {
                keyBytes = secretKey.getBytes();
                ivBytes = iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            //byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, new BASE64Decoder().decodeBuffer(cipherText));
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, Base64.decodeBase64(cipherText));
            /*String text = new String(decrypted, "UTF-8");
            return text.substring(0,text.length()-1);*/
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // ---------------------------


    //产生对称秘钥
    public static String generateSM4Key() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    //对称秘钥加密(CBC)
    public static String SM4EncForCBC(String key, String text) {
        SM4Util sm4 = new SM4Util();
        sm4.secretKey = key;
        sm4.hexString = true;
        sm4.iv = "31313131313131313131313131313131";
        String cipherText = sm4.encryptData_CBC(text);
        return cipherText;
    }

    //对称秘钥解密(CBC)
    public static String SM4DecForCBC(String key, String text) {
        SM4Util sm4 = new SM4Util();
        sm4.secretKey = key;
        sm4.hexString = true;
        sm4.iv = "31313131313131313131313131313131";
        String plainText = sm4.decryptData_CBC(text);
        return plainText;
    }

    //对称秘钥加密(ECB)
    public static String SM4EncForECB(String key, String text) {
        SM4Util sm4 = new SM4Util();
        sm4.secretKey = key;
        sm4.hexString = true;
        String cipherText = sm4.encryptData_ECB(text);
        return cipherText;
    }

    //对称秘钥解密(ECB)
    public static String SM4DecForECB(String key, String text) {
        SM4Util sm4 = new SM4Util();
        sm4.secretKey = key;
        sm4.hexString = true;
        String plainText = sm4.decryptData_ECB(text);
        return plainText;
    }


    public static void main(String[] args) throws IOException {
        String src = "哇咔咔1123AaaaccCCC.!@#$%^&*()_+";
        System.out.println("--生成SM4秘钥--");
        String sm4Key = generateSM4Key();
        System.out.println("sm4Key:" + sm4Key);
        System.out.println("--生成SM4结束--");
        System.out.println("--SM4的CBC加密--");
        String s1 = SM4EncForCBC(sm4Key, src);
        System.out.println("密文:" + s1);
        System.out.println("CBC解密");
        String s2 = SM4DecForCBC(sm4Key, s1);
        System.out.println("解密结果:" + s2);
        System.out.println("--ECB加密--");
        String s3 = SM4EncForECB(sm4Key, src);
        System.out.println("ECB密文:" + s3);
        System.out.println("ECB解密");
        String s4 = SM4DecForECB(sm4Key, s3);
        System.out.println("ECB解密结果:" + s4);
    }

    public enum SM4Type {
        ECB, CBC,
    }
}
