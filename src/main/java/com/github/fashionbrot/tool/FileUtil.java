package com.github.fashionbrot.tool;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class FileUtil {

    private FileUtil(){

    }

    public static final String PROPERTIES_SUFFIX = ".properties";


    /**
     * 判断指定的文件是否存在。
     *
     * @param fileName 要判断的文件的文件名
     * @return 存在时返回true，否则返回false。
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 创建指定的目录。
     * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
     * <b>注意：可能会在返回false的时候创建部分父目录。</b>
     *
     * @param file 要创建的目录
     * @return 完全创建成功时返回true，否则返回false。
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }

    /**
     * 创建指定的目录。
     * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
     * <b>注意：可能会在返回false的时候创建部分父目录。</b>
     *
     * @param fileName 要创建的目录的目录名
     * @return 完全创建成功时返回true，否则返回false。
     */
    public static boolean makeDirectory(String fileName) {
        File file = new File(fileName);
        return makeDirectory(file);
    }

    /**
     * 清空指定目录中的文件。
     * 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
     * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
     *
     * @param directory 要清空的目录
     * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
     */
    public static boolean emptyDirectory(File directory) {
        boolean result = true;
        File[] entries = directory.listFiles();
        for (int i = 0; i < entries.length; i++) {
            if (!entries[i].delete()) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 清空指定目录中的文件。
     * 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
     * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
     *
     * @param directoryName 要清空的目录的目录名
     * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
     */
    public static boolean emptyDirectory(String directoryName) {
        File dir = new File(directoryName);
        return emptyDirectory(dir);
    }


    /**
     * 删除指定目录及其中的所有内容。
     *
     * @param dirName 要删除的目录的目录名
     * @return 删除成功时返回true，否则返回false。
     */
    public static boolean deleteDirectory(String dirName) {
        return deleteDirectory(new File(dirName));
    }

    /**
     * 删除指定目录及其中的所有内容。
     *
     * @param dir 要删除的目录
     * @return 删除成功时返回true，否则返回false。
     */
    public static boolean deleteDirectory(File dir) {
        if ((dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Argument " + dir +
                    " is not a directory. ");
        }

        File[] entries = dir.listFiles();
        int sz = entries.length;

        for (int i = 0; i < sz; i++) {
            if (entries[i].isDirectory()) {
                if (!deleteDirectory(entries[i])) {
                    return false;
                }
            } else {
                if (!entries[i].delete()) {
                    return false;
                }
            }
        }

        if (!dir.delete()) {
            return false;
        }
        return true;
    }

    /**
     * 得到文件的类型。
     * 实际上就是得到文件名中最后一个“.”后面的部分。
     *
     * @param fileName 文件名
     * @return 文件名中的类型部分
     */
    public static String getTypePart(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 得到文件的类型。
     * 实际上就是得到文件名中最后一个“.”后面的部分。
     *
     * @param file 文件
     * @return 文件名中的类型部分
     */
    public static String getFileType(File file) {
        return getTypePart(file.getName());
    }

    /**
     * 将文件名中的类型部分去掉。
     *
     * @param filename 文件名
     * @return 去掉类型部分的结果
     */
    public static String trimType(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    public static void findPath(String path, List<File> fileList, String suffix) {
        File f = new File(path);
        if (f != null) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    findPath(file.getPath(), fileList, suffix);
                } else {
                    if (file.getName().endsWith(suffix)) {
                        fileList.add(file);
                    }
                }
            }
        }
    }

    public static boolean searchProperties(String path, String fileName) {
        File f = new File(path);
        if (f != null) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    searchProperties(file.getPath(), fileName);
                } else {
                    if (file.getName().endsWith(PROPERTIES_SUFFIX)
                            && (file.getName().equals(fileName)
                            || file.getName().equals(fileName + PROPERTIES_SUFFIX))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void putProperties(File file, Properties properties) {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            properties.load(in);
        } catch (Exception e) {
            log.error("putProperties error ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static Properties getResources(java.net.URL url, String fileName) {
        File f = new File(url.getPath());
        List<File> fileList = new ArrayList<File>();
        findPath(f.getPath(), fileList, PROPERTIES_SUFFIX);
        Properties all = new Properties();
        if (!CollectionUtil.isEmpty(fileList)) {
            for (File properties : fileList) {
                if (ObjectUtil.isEmpty(fileName)) {
                    all.putAll(toProperties(properties));
                } else {
                    String fileStr = properties.getName();
                    String fileNameTemp = fileName + PROPERTIES_SUFFIX;
                    if (fileStr.equalsIgnoreCase(fileName) || fileStr.equalsIgnoreCase(fileNameTemp)) {
                        all.putAll(toProperties(properties));
                    }
                }
            }
        }
        return all;
    }

    public static Properties toProperties(File file) {
        Properties pp = new Properties();
        putProperties(file, pp);
        return pp;
    }


    private static final String USER_HOME;

    static {
        USER_HOME = System.getProperty("user.home");
    }


    private static final int LOCK_COUNT = 10;

    public static List<File> searchFiles(File folder, final String keyword) {
        List<File> result = new ArrayList<File>();
        if (folder.isFile()) {
            result.add(folder);
        }
        File[] subFolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().contains(keyword)) {
                    return true;
                }
                return false;
            }
        });
        if (subFolders != null) {
            for (File file : subFolders) {
                if (file.isFile()) {
                    // 如果是文件则将文件添加到结果列表中
                    result.add(file);
                }
            }
        }
        return result;
    }


    /**
     * get file content
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileContent(File file) {
        RandomAccessFile randomAccessFile = null;
        FileLock fileLock = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            do {
                try {
                    fileLock = fileChannel.tryLock(0L, Long.MAX_VALUE, true);
                } catch (Exception e) {
                    log.error("getFileContent error", e);
                }
            } while (null == fileLock);


            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((randomAccessFile.read(buf)) != -1) {
                sb.append(new String(buf, "UTF-8"));
                buf = new byte[1024];
            }

            return sb.toString();
        } catch (Exception e) {
            log.error("getFileContent error", e);
        } finally {
            close(fileLock, randomAccessFile, null);
        }
        return "";
    }

    public static void deleteFile(File file){
        try {
            file.delete();
        }catch (Exception e){
            log.error("deleteFile error ",e);
        }
    }

    public static void writeFile(File file, String content) {
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            log.error("writeFile error", e);
            return;
        }


        FileChannel fileChannel = null;
        FileLock fileLock = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            fileChannel = randomAccessFile.getChannel();
            int i = 0;
            do {
                try {
                    fileLock = fileChannel.tryLock();
                } catch (Exception e) {
                    ++i;
                    if (i > LOCK_COUNT) {
                        log.error("writeFile  get lock count error filePath:{}", file.getAbsolutePath(), e);
                    }
                }
            } while (null == fileLock);

            randomAccessFile.write(content.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("writeFile error", e);
        } finally {
            close(fileLock, randomAccessFile, fileChannel);
        }
    }


    private static void close(FileLock fileLock, RandomAccessFile randomAccessFile, FileChannel fileChannel) {
        if (fileLock != null) {
            try {
                fileLock.release();
                fileLock = null;
            } catch (IOException e) {
                log.error("fileLock release error");
            }
        }
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
                randomAccessFile = null;
            } catch (IOException e) {
                log.error("randomAccessFile close error");
            }
        }
        if (fileChannel != null) {
            try {
                fileChannel.close();
                fileChannel = null;
            } catch (IOException e) {
                log.error("fileChannel close error");
            }
        }
    }




    public static void main(String[] args) {
       /* String p="E:\\dev\\ideaProject\\config-test\\spring-mvc-config\\src\\main\\resources";
        boolean b=searchFile(p,"application");
        System.out.println(b);*/

    }



}