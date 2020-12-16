package com.github.fashionbrot.tool.compressor.lz4;

import com.github.fashionbrot.tool.compressor.Compressor;
import com.github.fashionbrot.tool.compressor.CompressorType;

public class Lz4Compressor implements Compressor {
    @Override
    public byte[] compress(byte[] bytes) {
        return Lz4Util.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return Lz4Util.decompress(bytes);
    }

    @Override
    public CompressorType type() {
        return CompressorType.LZ4;
    }
}