package com.github.fashionbrot.tool.compressor;

import com.github.fashionbrot.tool.StringUtil;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class CompressorFactory {

    /**
     * The constant COMPRESSOR_MAP.
     */
    private static Map<CompressorType, Compressor> COMPRESSOR_MAP = new ConcurrentHashMap<>();

    static {
        COMPRESSOR_MAP.put(CompressorType.NONE, new NoneCompressor());
        ServiceLoader<Compressor> classLoader = ServiceLoader.load(Compressor.class);
        for(Compressor service : classLoader) {
            COMPRESSOR_MAP.put(service.type(),service);
        }
    }

    /**
     * Get compressor by compressorName.
     *
     * @return the compressor
     */
    public static Compressor getCompressor(String compressorName) {
        CompressorType type = CompressorType.NONE;
        if (StringUtil.isEmpty(compressorName)){
            type = CompressorType.getByName(compressorName);
        }
        if (COMPRESSOR_MAP.containsKey(type)) {
            return COMPRESSOR_MAP.get(type);
        } else {
            return COMPRESSOR_MAP.get(CompressorType.NONE);
        }
    }


    /**
     * Get compressor by code.
     *
     * @return the compressor
     */
    public static Compressor getCompressor(byte code) {
        CompressorType type = CompressorType.getByCode(code);

        if (COMPRESSOR_MAP.containsKey(type)) {
            return COMPRESSOR_MAP.get(type);
        } else {
            ServiceLoader<Compressor> classLoader = ServiceLoader.load(Compressor.class);
            for(Compressor service : classLoader) {
                COMPRESSOR_MAP.put(service.type(),service);
            }
            return COMPRESSOR_MAP.get(type);
        }
    }

    /**
     * None compressor
     */
    public static class NoneCompressor implements Compressor {
        @Override
        public byte[] compress(byte[] bytes) {
            return bytes;
        }

        @Override
        public byte[] decompress(byte[] bytes) {
            return bytes;
        }

        @Override
        public CompressorType type() {
            return CompressorType.NONE;
        }
    }

}
