package com.github.fashionbrot.tool.compressor.lz4;

import net.jpountz.lz4.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Lz4Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lz4Util.class);
    private static final int ARRAY_SIZE = 1024;

    public static byte[] compress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        LZ4Compressor compressor = LZ4Factory.fastestInstance().fastCompressor();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (LZ4BlockOutputStream lz4BlockOutputStream
                     = new LZ4BlockOutputStream(outputStream, ARRAY_SIZE, compressor)) {
            lz4BlockOutputStream.write(bytes);
        } catch (IOException e) {
            LOGGER.error("compress bytes error", e);
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(ARRAY_SIZE);

        LZ4FastDecompressor decompressor = LZ4Factory.fastestInstance().fastDecompressor();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try (LZ4BlockInputStream decompressedInputStream
                     = new LZ4BlockInputStream(inputStream, decompressor)) {
            int count;
            byte[] buffer = new byte[ARRAY_SIZE];
            while ((count = decompressedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
        } catch (IOException e) {
            LOGGER.error("decompress bytes error", e);
        }
        return outputStream.toByteArray();
    }

}
