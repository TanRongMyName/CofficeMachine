package com.coffice.shengtao.cofficemachine.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePalApplication;

public class MyApplication extends Application {
    public static RequestQueue mQueue;//volley 使用的request请求队列
    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }
    //初始化一些使用到的框架 或者控件
    public void initConfig(){
        //初始LitePal 数据库
        LitePalApplication.initialize(this);
        //初始化Volley请求队列
        mQueue=Volley.newRequestQueue(this);
    }


}
