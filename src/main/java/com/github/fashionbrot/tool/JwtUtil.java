package com.github.fashionbrot.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.fashionbrot.tool.encrypt.AesUtil;
import com.github.fashionbrot.tool.encrypt.RsaUtil;
import com.github.fashionbrot.tool.enums.AlgorithmEnum;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author fashi
 */
public class JwtUtil {

    private static ReentrantLock lock = new ReentrantLock();

    public static RSAPublicKey PUBLIC_KEY = null;
    public static RSAPrivateKey PRIVATE_KEY = null;
    public static String SECRET = "";

    /**
     * 创建 token
     * @param obj
     * @param second
     * @return
     */
    public static String createToken(Map<String,Object> obj,int second){
        return createToken(obj, AlgorithmEnum.RSA256,second);
    }

    /**
     * 创建token
     * @param obj
     * @param algorithm
     * @param second
     * @return
     */
    public static String createToken(Map<String,Object> obj,AlgorithmEnum algorithm,int second) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, second);
        Date expiresDate = nowTime.getTime();

        String token = JWT.create()
                .withPayload(obj)
//                .withClaim("map",obj)
                // sign time
                .withIssuedAt(iatDate)
                // expire time
                .withExpiresAt(expiresDate)
                .sign(getAlgorithm(algorithm));

        return token;
    }

    public static Algorithm getAlgorithmHMAC(AlgorithmEnum algorithmEnum,String secretKey){
        if (algorithmEnum == AlgorithmEnum.HMAC256){
            return Algorithm.HMAC256(secretKey);
        }else if (algorithmEnum== AlgorithmEnum.HMAC512){
            return Algorithm.HMAC512(secretKey);
        }
        return null;
    }

    public static Algorithm getAlgorithmRsaStr(AlgorithmEnum algorithmEnum,String publicKey ,String privateKey){
        RSAPublicKey rsaPublicKey = RsaUtil.convertPublicKey(publicKey);
        RSAPrivateKey rsaPrivateKey = RsaUtil.convertPrivateKey(privateKey);
        return getAlgorithmRsa(algorithmEnum,rsaPublicKey,rsaPrivateKey);
    }

    public static Algorithm getAlgorithmRsa(AlgorithmEnum algorithmEnum ,RSAPublicKey publicKey,RSAPrivateKey privateKey){
        if (publicKey==null || privateKey==null){
            return null;
        }
        if (algorithmEnum== AlgorithmEnum.RSA256){
            return Algorithm.RSA256(publicKey,privateKey);
        }else if (algorithmEnum==AlgorithmEnum.RSA512){
            return Algorithm.RSA512(publicKey,privateKey);
        }
        return null;
    }

    /**
     * 验证解密token
     * @param token
     * @param algorithmEnum
     * @return
     */
    public static Map<String, Claim> verifyToken(String token,AlgorithmEnum algorithmEnum) {
        JWTVerifier verifier = JWT.require(getAlgorithm(algorithmEnum)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }


    /**
     * 验证解密token
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {
        return verifyToken(token,AlgorithmEnum.RSA256);
    }


    /**
     * 根据算法类型获取 算法
     * @param algorithmEnum
     * @return
     */
    public static Algorithm getAlgorithm(AlgorithmEnum algorithmEnum) {
        lock.lock();
        try {
            if (algorithmEnum == AlgorithmEnum.HMAC256) {
                if (ObjectUtil.isEmpty(SECRET)) {
                    SECRET = NanoIdUtil.randomNanoId(64);
                }
                return getAlgorithmHMAC(algorithmEnum,SECRET);
            } else {
                Algorithm  algorithm = null;
                if (algorithmEnum == AlgorithmEnum.RSA256) {
                    if (PUBLIC_KEY==null){
                        KeyPair keyPair = RsaUtil.genKeyPair(512);
                        if (keyPair!=null) {
                            PUBLIC_KEY = (RSAPublicKey) keyPair.getPublic();
                            PRIVATE_KEY = (RSAPrivateKey) keyPair.getPrivate();
                        }
                    }
                } else if (algorithmEnum == AlgorithmEnum.RSA512) {
                    if (PUBLIC_KEY==null) {
                        KeyPair keyPair = RsaUtil.genKeyPair(1024);
                        if (keyPair != null) {
                            PUBLIC_KEY = (RSAPublicKey) keyPair.getPublic();
                            PRIVATE_KEY = (RSAPrivateKey) keyPair.getPrivate();
                        }
                    }
                }
                return getAlgorithmRsa(algorithmEnum,PUBLIC_KEY,PRIVATE_KEY);
            }
        }finally {
            lock.unlock();
        }
    }




    public static void main(String[] args) throws InterruptedException {

        Map map=new HashMap(1);
        map.put("test","张三");
        String token1 = createToken(map,AlgorithmEnum.HMAC512,2);
        System.out.println(token1);
        String key = "q!w@e#r$t%Y^UIOP";
        String aesToken = AesUtil.encryptString(token1, key);
        System.out.println("AES token:"+aesToken);

        Map<String, Claim> stringClaimMap = verifyToken(token1,AlgorithmEnum.HMAC512);
        System.out.println(stringClaimMap);


        System.out.println(SECRET);

    }


}
