package com.example.bcprov.sm2;

import com.example.bcprov.ByteConvertUtil;
import com.example.bcprov.sm4.SM4Util;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.UUID;

public class SM2Util {

    /**
     * SM2加密算法
     *
     * @param publicKey 公钥
     * @param data      明文数据
     * @return
     */
    public static String encrypt(String publicKey, String data) {

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造ECC算法参数，曲线方程、椭圆曲线G点、大整数N
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        //提取公钥点
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(publicKey));
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为加密模式
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));

        byte[] arrayOfBytes = null;
        try {
            byte[] in = data.getBytes();
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
        } catch (Exception e) {
            System.out.println("SM2加密时出现异常:" + e.getMessage());
        }
        return Hex.toHexString(arrayOfBytes);

    }

    public static byte[] encrypt(String publicKey, byte[] data) {

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造ECC算法参数，曲线方程、椭圆曲线G点、大整数N
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        //提取公钥点
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(publicKey));
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为加密模式
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));

        byte[] arrayOfBytes = null;
        try {
            arrayOfBytes = sm2Engine.processBlock(data, 0, data.length);
        } catch (Exception e) {
            System.out.println("SM2加密时出现异常:" + e.getMessage());
        }
        return arrayOfBytes;

    }

    /**
     * SM2解密算法
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return
     */
    public static String decrypt(String privateKey, String cipherData) {

        // 使用BC库加解密时密文以04开头，传入的密文前面没有04则补上
        if (!cipherData.startsWith("04")) {
            cipherData = "04" + cipherData;
        }
        byte[] cipherDataByte = Hex.decode(cipherData);

        //获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());

        BigInteger privateKeyD = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为解密模式
        sm2Engine.init(false, privateKeyParameters);

        String result = "";
        try {
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            return new String(arrayOfBytes);
        } catch (Exception e) {
            System.out.println("SM2解密时出现异常:" + e.getMessage());
        }
        return result;

    }

    public static byte[] decrypt(String privateKey, byte[] cipherData) {
        if (cipherData == null || cipherData.length == 0) {
            return null;
        }
        // 使用BC库加解密时密文以04开头，传入的密文前面没有04则补上
//        if (!cipherData.startsWith("04")) {
//            cipherData = "04" + cipherData;
//        }
//        byte[] cipherDataByte = Hex.decode(cipherData);
        byte[] cipherDataByte = cipherData;

        //获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());

        BigInteger privateKeyD = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为解密模式
        sm2Engine.init(false, privateKeyParameters);

        try {
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            return arrayOfBytes;
        } catch (Exception e) {
            System.out.println("SM2解密时出现异常:" + e.getMessage());
            return null;
        }

    }


    private static final String FILE_NAME = "D:" + File.separator + "tmp" + File.separator + "t1.txt";
    private static final String FILE_NAME_EN = "D:" + File.separator + "tmp" + File.separator + "t1加密.txt";
    private static final String FILE_NAME_DE = "D:" + File.separator + "tmp" + File.separator + "t1解密.txt";

    private static void testM2File() throws Exception {
        String filePath = FILE_NAME;
        File file = new File(filePath);

        // 读取文件, 转换为字节流
        byte[] datas = ByteConvertUtil.file2buf(file);

        SM2Util sm2 = new SM2Util();
        final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
        // 获取一个椭圆曲线类型的密钥对生成器
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        // 使用SM2参数初始化生成器
        kpg.initialize(sm2Spec);
        // 获取密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        BCECPublicKey p = (BCECPublicKey) publicKey;
        System.out.print("\n公钥：" + Hex.toHexString(p.getQ().getEncoded(false)));

        PrivateKey privateKey = keyPair.getPrivate();
        BCECPrivateKey s = (BCECPrivateKey) privateKey;
        System.out.print("\n私钥：" + Hex.toHexString(s.getD().toByteArray()));

        datas = sm2.encrypt(Hex.toHexString(p.getQ().getEncoded(false)), datas);
//        String data = sm2.encrypt(publicKey1, M);
        System.out.println("\n加密字符串：" + datas);
        ByteConvertUtil.BetyToFile(FILE_NAME_EN, datas);

        byte[] ddd = sm2.decrypt(Hex.toHexString(s.getD().toByteArray()), datas);
//        String text = sm2.decrypt(privateKey1, data);
        System.out.println("\n解密：" + ddd);

        System.out.println("解密: ");
        ByteConvertUtil.BetyToFile(FILE_NAME_DE, ddd);

    }

    private static void testStr() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        String publicKey1 = "04ABC2230A05A72CEB667B20019C4F2A580E4D0A3BE9D20BF914565AB3B82631E1C0E15803FA3ADE3E6D9EEF293CBD8BAECC51D82B61404A39584198B6985686FB";
        String privateKey1 = "969FC0F73FA117A040B37D5B5018382A74D40590EAA02809B87FA09196F8276D";


        String M = "哇咔咔1A.!@#$%^&*()_+";
        SM2Util sm2 = new SM2Util();
        final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
        // 获取一个椭圆曲线类型的密钥对生成器
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        // 使用SM2参数初始化生成器
        kpg.initialize(sm2Spec);
        // 获取密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        BCECPublicKey p = (BCECPublicKey) publicKey;
        System.out.print("\n公钥：" + Hex.toHexString(p.getQ().getEncoded(false)));

        PrivateKey privateKey = keyPair.getPrivate();
        BCECPrivateKey s = (BCECPrivateKey) privateKey;
        System.out.print("\n私钥：" + Hex.toHexString(s.getD().toByteArray()));

//        String data = sm2.encrypt(Hex.toHexString(p.getQ().getEncoded(false)), M);
        String data = sm2.encrypt(publicKey1, M);
        System.out.println("\n加密字符串：" + data);

//        String text = sm2.decrypt(Hex.toHexString(s.getD().toByteArray()), data);
        String text = sm2.decrypt(privateKey1, data);
        System.out.println("\n解密：" + text);
    }

    public static void main(String[] args) throws Exception {
        testStr();

    }

}

