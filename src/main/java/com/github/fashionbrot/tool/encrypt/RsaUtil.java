package com.github.fashionbrot.tool.encrypt;

import com.github.fashionbrot.tool.Base64Util;
import com.github.fashionbrot.tool.ObjectUtil;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fashi
 */
public class RsaUtil {


    /**
     * 随机生成密钥对
     */
    public static KeyPair genKeyPair(Integer keySize) {
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

        return keyPair;
    }


    /**
     * 随机生成密钥对
     */
    public static Map<String, String> genKeyPairMap(Integer keySize) {
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

        String publicKeyStr = publicKeyToString(publicKey);
        // 得到私钥字符串
        String privateKeyStr = privateKeyToString(privateKey);

        Map<String, String> keyMap = new HashMap<>(2);
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put("public", publicKeyStr);
        //1表示私钥
        keyMap.put("private", privateKeyStr);

        return keyMap;
    }


    public static String publicKeyToString(final RSAPublicKey publicKey) {
        if (publicKey == null) {
            return "";
        }
        return Base64Util.encodeBase64String(publicKey.getEncoded());
    }

    public static String privateKeyToString(final RSAPrivateKey privateKey) {
        if (privateKey == null) {
            return "";
        }
        return Base64Util.encodeBase64String(privateKey.getEncoded());
    }

    /**
     * RSA公钥加密
     *
     * @param keyPair
     * @param str
     * @return
     * @throws Exception
     */
    public static String encrypt(KeyPair keyPair, String str) {
        if (ObjectUtil.isEmpty(str)) {
            return "";
        }
        return encrypt(str, (RSAPublicKey) keyPair.getPublic());
    }

    /**
     * 秘钥转换
     * @param publicKey
     * @return
     */
    public static RSAPublicKey convertPublicKey(String publicKey){
        RSAPublicKey pubKey = null;
        if (ObjectUtil.isNotEmpty(publicKey)){
            byte[] decoded = Base64Util.decode(publicKey);
            try {
                pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return pubKey;
    }

    /**
     * 秘钥转换
     * @param privateKey
     * @return
     */
    public static RSAPrivateKey convertPrivateKey(String privateKey){
        RSAPrivateKey priKey = null;
        if (ObjectUtil.isNotEmpty(privateKey)){
            //base64编码的私钥
            byte[] decoded = Base64Util.decode(privateKey);
            try {
                priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return priKey;
    }

    /**
     * RSA公钥加密
     *
     * @param str       需要加密的字符串  UTF-8
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey){
        if (ObjectUtil.isEmpty(str)) {
            return "";
        }
        //base64编码的公钥
        RSAPublicKey rsaPublicKey = convertPublicKey(publicKey);
        return encrypt(str,rsaPublicKey);
    }


    public static String encrypt(String str,RSAPublicKey pubKey){
        //RSA加密
        if (ObjectUtil.isNotEmpty(str)){
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, pubKey);
                return Base64Util.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * RSA私钥解密
     *
     * @param str        需要解密的字符串
     * @param privateKey 私钥
     * @return 名文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey)  {
        if (ObjectUtil.isEmpty(str)) {
            return "";
        }
        RSAPrivateKey rsaPrivateKey = convertPrivateKey(privateKey);
        return decrypt(str,rsaPrivateKey);
    }


    public static String decrypt(String str, RSAPrivateKey priKey) {
        if (ObjectUtil.isNotEmpty(str)){
            byte[] inputByte = Base64Util.decode(str.getBytes());
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, priKey);
                return new String(cipher.doFinal(inputByte));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }


    public static String decrypt(KeyPair keyPair, String str)  {
        if (ObjectUtil.isEmpty(str)) {
            return "";
        }
        return decrypt(str, (RSAPrivateKey) keyPair.getPrivate());
    }


    public static void main(String[] args) throws Exception {
        Map<String, String> stringStringMap = genKeyPairMap(512);

        System.out.println(stringStringMap);


        String encrypt = encrypt(new String("{张三哦}".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), stringStringMap.get("public"));
        System.out.println("encrypt:" + encrypt);
        String decrypt = decrypt(encrypt, stringStringMap.get("private"));
        System.out.println("decrypt:" + decrypt);


        KeyPair keyPair = genKeyPair(512);
        String encrypt1 = encrypt(keyPair, "{张三哦}");
        System.out.println("encrypt1:"+encrypt1);
        String decrypt1 = decrypt(keyPair, encrypt1);
        System.out.println("decrypt1:"+decrypt1);

    }

}
