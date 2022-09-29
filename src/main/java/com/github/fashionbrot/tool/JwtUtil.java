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
                return Algorithm.HMAC256(SECRET);
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
                    algorithm =  Algorithm.RSA256(PUBLIC_KEY, PRIVATE_KEY);
                } else if (algorithmEnum == AlgorithmEnum.RSA512) {
                    if (PUBLIC_KEY==null) {
                        KeyPair keyPair = RsaUtil.genKeyPair(1024);
                        if (keyPair != null) {
                            PUBLIC_KEY = (RSAPublicKey) keyPair.getPublic();
                            PRIVATE_KEY = (RSAPrivateKey) keyPair.getPrivate();
                        }
                    }
                    algorithm =  Algorithm.RSA512(PUBLIC_KEY, PRIVATE_KEY);
                }
                return algorithm;
            }
        }finally {
            lock.unlock();
        }
    }




    public static void main(String[] args) throws InterruptedException {

        Map map=new HashMap(1);
        map.put("test","张三");
        String token1 = createToken(map,AlgorithmEnum.HMAC256,2);
        System.out.println(token1);
        String key = "q!w@e#r$t%Y^UIOP";
        String aesToken = AesUtil.encryptString(token1, key);
        System.out.println("AES token:"+aesToken);

        Map<String, Claim> stringClaimMap = verifyToken(token1,AlgorithmEnum.HMAC256);
        System.out.println(stringClaimMap);


        System.out.println(SECRET);

    }


}
