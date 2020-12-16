package com.github.fashionbrot.tool.compressor.bzip2;

import com.github.fashionbrot.tool.compressor.Compressor;
import com.github.fashionbrot.tool.compressor.CompressorType;

public class BZip2Compressor implements Compressor {

    @Override
    public byte[] compress(byte[] bytes) {
        return BZip2Util.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return BZip2Util.decompress(bytes);
    }

    @Override
    public CompressorType type() {
        return CompressorType.BZIP2;
    }

}