package com.doubook.utiltools;

import android.content.Context;

import java.io.File;

public class FileCache {
    private static File cacheDir;

    public FileCache(Context context) {
        // 判断外存SD卡挂载状态，如果挂载正常，创建SD卡缓存文件夹
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "VRead");
            cacheDir = new File(FileCache.getCaheDir());
        } else {
            // SD卡挂载不正常，获取本地缓存文件夹（应用包所在目录）
            cacheDir = context.getCacheDir();
        }
//        if (!cacheDir.exists()) {
//            cacheDir.mkdirs();
//        }
    }

    public static String getCaheDir() {
        if (cacheDir == null) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "VRead");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        return cacheDir.toString();
    }

    public static File getCaheDirFile() {
        if (cacheDir == null) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "VRead");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        }
        return cacheDir;
    }

    public File getFile(String url) {
        String fileName = String.valueOf(url.hashCode());
        File file = new File(cacheDir, fileName);
        return file;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File f : files)
            f.delete();
    }

    public String getCacheSize() {
        long size = 0;
        if (cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            for (File f : files) {
                size += f.length();
            }
        }
        String cacheSize;
        if (size / 1024 > 1024) {
            cacheSize = String.valueOf(size / 1024 / 1024) + " M";
        }else {
            cacheSize = String.valueOf(size / 1024) + " K";
        }
        return cacheSize;
    }

}
