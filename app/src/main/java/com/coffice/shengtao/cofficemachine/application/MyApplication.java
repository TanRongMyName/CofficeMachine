package com.coffice.shengtao.cofficemachine.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoMaster;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoSession;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderConfig;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderManager;
import com.coffice.shengtao.cofficemachine.pictureframe.base.LoaderEnum;
import com.coffice.shengtao.cofficemachine.pictureframe.fresco.FrescoImageLoader;
import com.coffice.shengtao.cofficemachine.pictureframe.glide.GlideImageLocader;

import org.greenrobot.greendao.database.Database;
import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private static MyApplication context;
    public static MyApplication getInstent(){
        return context;
    }

    public static RequestQueue mQueue;//volley 使用的request请求队列

    public static DaoSession daosession;//greendao 使用的Session

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

        //初始化Greendao 数据   dao泛型   --操作数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "coffic_vending_machine_db1.db", null);
        //获取可写数据库
        //SQLiteDatabase db = helper.getWritableDatabase();
        //数据库加密
        Database db = helper.getEncryptedWritableDb("123");
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        daosession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daosession;
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
                .Builder(LoaderEnum.FRESCO,new FrescoImageLoader())   //这样切换
                //.addImageLodaer(LoaderEnum.GLIDE,new GlideImageLocader())
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


}
