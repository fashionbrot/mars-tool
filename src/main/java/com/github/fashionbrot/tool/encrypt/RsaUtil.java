package com.github.fashionbrot.tool.encrypt;

import com.github.fashionbrot.tool.Base64Util;
import com.github.fashionbrot.tool.ObjectUtil;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaUtil {


    /**
     * 随机生成密钥对
     */
    public static Map<String,String> genKeyPair(Integer keySize)  {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器
        keyPairGen.initialize(keySize, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        String publicKeyStr = Base64Util.encodeBase64String(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyStr =Base64Util.encodeBase64String(privateKey.getEncoded());

        Map<String,String> keyMap=new HashMap<>(2);
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put("public", publicKeyStr);
        //1表示私钥
        keyMap.put("private", privateKeyStr);

        return keyMap;
    }



    /**
     * RSA公钥加密
     *
     * @param str       需要加密的字符串  UTF-8
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        if (ObjectUtil.isEmpty(str)){
            return "";
        }
        str = new String(str.getBytes(StandardCharsets.UTF_8),"UTF-8");

        //base64编码的公钥
        byte[] decoded = Base64Util.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64Util.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
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
        if (ObjectUtil.isEmpty(str)){
            return "";
        }
        //64位解码加密后的字符串
        byte[] inputByte = Base64Util.decodeBase64(str.getBytes());
        //base64编码的私钥
        byte[] decoded = Base64Util.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }


    public static void main(String[] args) throws Exception {
        Map<String, String> stringStringMap = genKeyPair(512);

        System.out.println(stringStringMap);


        String encrypt = encrypt(new String("{\"name\":\"张三111111111111111111111111111111\"}".getBytes(StandardCharsets.UTF_8),"UTF-8"), stringStringMap.get("public"));
        System.out.println("encrypt:"+encrypt);
        String decrypt = decrypt(encrypt, stringStringMap.get("private"));
        System.out.println("decrypt:"+decrypt);

    }

}
