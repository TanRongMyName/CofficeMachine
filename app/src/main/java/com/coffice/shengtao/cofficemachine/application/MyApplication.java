package com.coffice.shengtao.cofficemachine.application;

import android.app.Application;

import org.litepal.LitePalApplication;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化一些控件
        LitePalApplication.initialize(this);
    }


}
