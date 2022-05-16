package com.github.fashionbrot.tool.encrypt;


import com.github.fashionbrot.tool.Base64Util;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Description
 * @Date 2022/4/26 9:29
 * @Version 1.0
 */
public class RSAEncryptUtil {

    private static final String RSA_PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCllghPlj6L2UxEGAgG2YXWlbPi\n" +
            "Kp0Wa5Te6L3wCOZof2Drx21pwoBSVyS4pRCJfjodiu+Cv+JXsKzkuD77U04bh/nC\n" +
            "LDN4FPqvpRzD5PBYE/2LB4fEbPjmRkRrIqElS2LBKrfAMOJZEGOlb5vFHI0AhFxZ\n" +
            "9joKtRF/MvTCtb93JwIDAQAB";

    private static final String RSA_PRIVATE_KEY =  "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKWWCE+WPovZTEQY\n" +
            "CAbZhdaVs+IqnRZrlN7ovfAI5mh/YOvHbWnCgFJXJLilEIl+Oh2K74K/4lewrOS4\n" +
            "PvtTThuH+cIsM3gU+q+lHMPk8FgT/YsHh8Rs+OZGRGsioSVLYsEqt8Aw4lkQY6Vv\n" +
            "m8UcjQCEXFn2Ogq1EX8y9MK1v3cnAgMBAAECgYBUVeTnvCshCUy5xc+YxYNQNrEG\n" +
            "uROHDsYk/j93GOFZSb8wd0M3wXOf/Hhcft28WYj58QBqBJcgeICmgVy6nsRBmYf6\n" +
            "jriYNn+J55gyFyJuRITIQJT12EwiV+6GHZHCtNScfM0oRoV1+bi/V7hioK7xysZu\n" +
            "7Xp/E1yocUJLS1m9iQJBANal7Ftl3O/kTVLYLM3YWYQn11N2TZDGhOsjGTVlWtvZ\n" +
            "ujUpfCQGpnz6yJ8HUF4I7Ab8HTDF2XrRwLpkU6ED/a0CQQDFfHPBfGrTjBuRu3Jd\n" +
            "u8IK0odPQEiE4zAYJVSv3vMOc8+Y/s6vusTENnVJ0kMFuQjD5yF1/qNCNkQa1lwv\n" +
            "ovqjAkBDtJKZQcgu4xSAzS9Uvql9xhNlgCt3PycuBCQvc4j1T/gsTKmbs31QbspZ\n" +
            "XzU6dBX/HgcXG1E+LGteX8cBQwEBAkBlNmdNHqrRSNlIUz1F9Nh6G3PllhiUoqsf\n" +
            "dOJ2UFlZatlWnCDpx1rZBOAPuYdIA5EOdgKcKqkZYh59cqdEcs7pAkB5j7YfTTG0\n" +
            "Q2j8mb4kKIYyMnn/AITSB4cESMpCiQKLU5ke4MrPnpbbwQ0qMJVfp37yTH1PRypV\n" +
            "aI3yD6ZvLY5V";


    /**
     * RSA公钥加密
     *
     * @param str       需要加密的字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64Util.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64Util.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        需要解密的字符串
     * @param privateKey 私钥
     * @return 名文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64Util.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64Util.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 固定私钥解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decrypt(String str) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64Util.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64Util.decodeBase64(RSA_PRIVATE_KEY);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) throws Exception {
        String content = "X2nj3JTQwX/zMZidcJ9g8vTh5vCm4B0Ce9Pz/22jnbwxQTBaIVBGfPlbAN8NSSX9bLEh2d5x61g6NsGY89JlUKh4piKFtEYhE/wOc/NAxjEC3y4sgduE1tBn+UcHE1uAeMIpvZXKjaThLkgMw9joAMzTVAXxMjQbh17alqYVvf0=";
        String decrypt = decrypt(content, RSA_PRIVATE_KEY);
        System.out.println(decrypt);

        System.out.println(encrypt("123456aaa",RSA_PUBLIC_KEY));
    }
}


