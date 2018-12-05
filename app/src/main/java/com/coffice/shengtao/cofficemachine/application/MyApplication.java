package com.coffice.shengtao.cofficemachine.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.text.TextUtils;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.dao.GreenDaoManager;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoMaster;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoSession;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderConfig;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderManager;
import com.coffice.shengtao.cofficemachine.pictureframe.base.LoaderEnum;
import com.coffice.shengtao.cofficemachine.pictureframe.glide.GlideImageLocader;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.SPUtils;
import com.coffice.shengtao.cofficemachine.utils.TypefaceUtils;

import org.greenrobot.greendao.database.Database;
import org.litepal.LitePalApplication;
import org.litepal.util.SharedUtil;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private static MyApplication context;
    public static MyApplication getInstent(){
        return context;
    }

    public static RequestQueue mQueue;//volley 使用的request请求队列

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initConfig();
    }
    /**
     *
     *     初始化一些使用到的框架 或者控件
     */
    public void initConfig(){
        //初始LitePal 数据库    实体泛型  --操作数据库
        setupDatabase();
        initPictureFrame();
        initHttp();
        initJPush();
        initTypeFace();

    }

    private void initTypeFace() {
        //初始化APP 整体的字体样式

        String fontpath= (String) SPUtils.get(this,GlobalData.FONTTYPE_PATH_KEY,"");
        LogUtils.d("初始化字体库的-------文件位置==="+fontpath);
        if(fontpath!=null&&!TextUtils.isEmpty(fontpath)) {
            setAPPTypeFont(fontpath);
        }
    }

    /**
     * 初始化网络通信 的 volley 请求队列
     * 网络框架   okhttp3
     */
    public void initHttp(){
        //初始化Volley请求队列
        mQueue=Volley.newRequestQueue(this);
    }
    /**
     * Litepal 数据库  最后  将两个数据库转换为 一个 然后提供接口 返回出来  一次只 使用一个
     * 配置greendao数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db
        LitePalApplication.initialize(this);
        GreenDaoManager.getInstance();
    }


    /**
     * 初始化 图片框架
     */
    public void initPictureFrame(){

        //默认使用Fresco  addImangeLoader 就切换了Glide 框架
//        ImageLoaderConfig config = new ImageLoaderConfig
//                .Builder(LoaderEnum.FRESCO,new FrescoImageLoader())
////                        .addImageLodaer(LoaderEnum.GLIDE,new GlideImageLocader())
//                .maxMemory(40*1024*1024L)  // 单位为Byte
//                .build();
//        ImageLoaderManager.getInstance().init(this,config);

        ImageLoaderConfig config = new ImageLoaderConfig
                .Builder(LoaderEnum.GLIDE,new GlideImageLocader())   //这样切换
                //.addImageLodaer(LoaderEnum.GLIDE,new GlideImageLocader())   LoaderEnum.FRESCO,new FrescoImageLoader()
                .maxMemory(40*1024*1024L)  // 单位为Byte
                .build();
        ImageLoaderManager.getInstance().init(this,config);

    }

    /**
     * 极光推送  的初始化
     */
    public void initJPush(){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public void setAPPTypeFont(String fontpath){
            LogUtils.d("传递的字体库fontpath==="+fontpath);
        if(Typeface.createFromAsset(getAssets(),fontpath)==null){
            LogUtils.d("传递的字体库文件位置不存在");
        }else {
            SPUtils.put(this,GlobalData.FONTTYPE_PATH_KEY,fontpath);
            TypefaceUtils.replaceSystemDefaultFont(this, fontpath);
            String fontpath2= (String) SPUtils.get(this,GlobalData.FONTTYPE_PATH_KEY,"");
            LogUtils.d("fontpath2===="+fontpath2);
        }
    }


}
