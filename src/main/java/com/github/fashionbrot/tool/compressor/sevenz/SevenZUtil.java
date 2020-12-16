package com.github.fashionbrot.tool.compressor.sevenz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.compress.utils.SeekableInMemoryByteChannel;


public class SevenZUtil {


    private static final int BUFFER_SIZE = 8192;

    public static byte[] compress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        try (SevenZOutputFile z7z = new SevenZOutputFile(channel)) {
            SevenZArchiveEntry entry = new SevenZArchiveEntry();
            entry.setName("sevenZip");
            entry.setSize(bytes.length);
            z7z.putArchiveEntry(entry);
            z7z.write(bytes);
            z7z.closeArchiveEntry();
            z7z.finish();
            return channel.array();
        } catch (IOException e) {
            throw new RuntimeException("SevenZ compress error", e);
        }
    }

    public static byte[] decompress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(bytes);
        try (SevenZFile sevenZFile = new SevenZFile(channel)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (sevenZFile.getNextEntry() != null) {
                int n;
                while ((n = sevenZFile.read(buffer)) > -1) {
                    out.write(buffer, 0, n);
                }
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("SevenZ decompress error", e);
        }
    }

}
