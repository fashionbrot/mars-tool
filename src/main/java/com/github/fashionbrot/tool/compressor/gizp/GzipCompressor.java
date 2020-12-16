package com.github.fashionbrot.tool.compressor.gizp;

import com.github.fashionbrot.tool.compressor.Compressor;
import com.github.fashionbrot.tool.compressor.CompressorType;

public class GzipCompressor implements Compressor {

    @Override
    public byte[] compress(byte[] bytes) {
        return GzipUtil.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return GzipUtil.decompress(bytes);
    }

    @Override
    public CompressorType type() {
        return CompressorType.GZIP;
    }

}