package com.github.fashionbrot.tool;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author fashi
 */
public class Base64Util {

    public final static byte[] byte_empty = new byte[]{};

    /**
     * 加密
     * @param  binaryData
     * @return
     */
    public static byte[] encode(final byte[] binaryData) {
        if (ObjectUtil.isNotEmpty(binaryData)) {
            return Base64.getEncoder().encode(binaryData);
        }
        return byte_empty;
    }

    /**
     * 加密
     * @param binaryData
     * @return
     */
    public static byte[] mimeEncode(final byte[] binaryData) {
        if (ObjectUtil.isNotEmpty(binaryData)) {
            return Base64.getMimeEncoder().encode(binaryData);
        }
        return byte_empty;
    }

    /**
     * 加密
     * @param binaryData
     * @return
     */
    public static byte[] encode(final String binaryData) {
        if (ObjectUtil.isNotEmpty(binaryData)) {
            byte[] bytes = binaryData.getBytes(StandardCharsets.UTF_8);
            return encode(bytes);
        }
        return byte_empty;
    }


    /**
     * 解密
     * @param str
     * @return
     */
    public static byte[] decode(byte[] str) {
        if (ObjectUtil.isNotEmpty(str)) {
            return Base64.getDecoder().decode(str);
        }
        return byte_empty;
    }

    /**
     * 解密
     * @param str
     * @return
     */
    public static byte[] mimeDecode(byte[] str) {
        if (ObjectUtil.isNotEmpty(str)) {
            return Base64.getMimeDecoder().decode(str);
        }
        return byte_empty;
    }


    /**
     * 解密
     * @param str
     * @return
     */
    public static byte[] decode(String str) {
        if (ObjectUtil.isNotEmpty(str)) {
            return Base64.getDecoder().decode(str);
        }
        return byte_empty;
    }




    public static String encodeBase64String(final byte[] binaryData) {
        return ObjectUtil.newStringUsAscii(encode(binaryData));
    }



}
