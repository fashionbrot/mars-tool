package com.github.fashionbrot.tool;

import java.util.Base64;

public class Base64Util {

    public final static byte[] byte_empty = new byte[]{};

    public static byte[] decodeBase64(byte[] str) {
        if (ObjectUtil.isNotEmpty(str)) {
            return Base64.getMimeDecoder().decode(str);
        }
        return byte_empty;
    }

    public static byte[] decodeBase64(String str) {
        if (ObjectUtil.isNotEmpty(str)) {
            return Base64.getMimeDecoder().decode(str);
        }
        return byte_empty;
    }


    public static byte[] encodeBase64(final byte[] binaryData) {
        if (ObjectUtil.isNotEmpty(binaryData)) {
            return Base64.getMimeEncoder().encode(binaryData);
        }
        return byte_empty;
    }



    public static String encodeBase64String(final byte[] binaryData) {
        return ObjectUtil.newStringUsAscii(encodeBase64(binaryData));
    }



}
