package com.github.fashionbrot.tool.compressor.sevenz;

import com.github.fashionbrot.tool.compressor.Compressor;
import com.github.fashionbrot.tool.compressor.CompressorType;

public class SevenZCompressor implements Compressor {

    @Override
    public byte[] compress(byte[] bytes) {
        return SevenZUtil.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return SevenZUtil.decompress(bytes);
    }

    @Override
    public CompressorType type() {
        return CompressorType.SEVENZ;
    }

}
