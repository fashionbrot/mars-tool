package com.github.fashionbrot.tool.compressor;

import com.github.fashionbrot.tool.NetUtil;

public class CompressorTest {

    public static void main(String[] args) {
        Compressor compressor = CompressorFactory.getCompressor(Byte.parseByte("3"));
        String text = "你好啊啊发 范德萨发打范德萨发打安抚第三方第三方";
        System.out.println(text.getBytes().length);
        byte[] compress = compressor.compress(text.getBytes());
        System.out.println(new String(compress));
        byte[] decompress = compressor.decompress(compress);
        System.out.println(decompress.length);
        System.out.println(new String(decompress));

        System.out.println(NetUtil.getLocalAddress());
    }

}
