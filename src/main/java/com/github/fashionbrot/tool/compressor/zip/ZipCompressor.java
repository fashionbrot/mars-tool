package com.github.fashionbrot.tool.compressor.zip;

import com.github.fashionbrot.tool.compressor.Compressor;
import com.github.fashionbrot.tool.compressor.CompressorType;

public class ZipCompressor implements Compressor {

    @Override
    public byte[] compress(byte[] bytes) {
        return ZipUtil.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return ZipUtil.decompress(bytes);
    }

    @Override
    public CompressorType type() {
        return CompressorType.ZIP;
    }

}
