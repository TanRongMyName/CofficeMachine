package com.coffice.shengtao.cofficemachine.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

public class SDCardUtils {
    private SDCardUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable()
    {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath()
    {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize()
    {
        if (isSDCardEnable())
        {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath)
    {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath()))
        {
            filePath = getSDCardPath();
        } else
        {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath()
    {
        return Environment.getRootDirectory().getAbsolutePath();
    }
    /**
     * 在sd卡下面创建一个目录
     */
    public static String createFolder(Context context, String folderName){
        File file = null;
        String sdRootPath = getSDCardPath();
        if(TextUtils.isEmpty(sdRootPath)){//表示sd卡不存在
            if(context!=null){
                // /data/data/com.sqlite/files/zhgougui
                file = new File(context.getFilesDir().getAbsolutePath()+File.separator+folderName);
            }
        }else{
            file = new File(sdRootPath+File.separator+folderName);
        }
        if(file!=null&&!file.exists()){
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    /**
     *
     * @param ctx 上下文
     * @param folderName 文件夹名
     * @param dataBaseName 数据库名
     * @return
     */
    public static String getDataBasePath(Context ctx, String folderName, String dataBaseName){
        String dbPath = "";
        String dirPath = createFolder(ctx,folderName);
        File file = new File(dirPath+File.separator+dataBaseName);
        if(file!=null&&!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dbPath = file.getAbsolutePath();
        return dbPath;
    }
}
