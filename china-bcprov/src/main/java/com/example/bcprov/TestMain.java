package com.example.bcprov;

import org.bouncycastle.util.encoders.Hex;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-13 16:08:18
 * @description
 */

public class TestMain {

    private static final String src = "哇咔咔111AAAaaa.!@#$%^&*()_+";
    private static final String pk = "04ABC2230A05A72CEB667B20019C4F2A580E4D0A3BE9D20BF914565AB3B82631E1C0E15803FA3ADE3E6D9EEF293CBD8BAECC51D82B61404A39584198B6985686FB";
    private static final String prik = "969FC0F73FA117A040B37D5B5018382A74D40590EAA02809B87FA09196F8276D";

    public static void main(String[] args) throws Exception {
        String ss = SMUtils.encryptSM2(pk, src);
        System.out.println("SM2密文:" + ss);
        System.out.println("SM2解密:" + SMUtils.decryptSM2(prik, ss));

        System.out.println("--------------");
        String ssByte = Hex.toHexString(SMUtils.encryptSM2(pk, src.getBytes()));
        System.out.println("SM2密文_byte:" + ssByte);
        System.out.println("SM2解密_byte:" + SMUtils.decryptSM2(prik, ssByte));

        System.out.println("--------------");
        System.out.println("SM3密文:" + SMUtils.encryptSM3(src));
        System.out.println("--------------");

//        String key = UUID.randomUUID().toString().replace("-", "");
        String key = "00000000000000008888888888888888";
        String sm4 = SMUtils.encryptSM4(src, true);
        System.out.println("sm4密文ECB:" + sm4);
        System.out.println("sm4解密ECB:" + SMUtils.decryptSM4(sm4, true));
        String key2 = "31313131313131313131313131313131";
        sm4 = SMUtils.encryptSM4CBC(key, key2, src, true);
        System.out.println("sm4密文_CBC:" + sm4);
        System.out.println("sm4解密_CBC:" + SMUtils.decryptSM4CBC(key2, sm4, true));
        System.out.println("--------------");
    }

}
