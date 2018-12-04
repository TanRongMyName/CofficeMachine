package com.coffice.shengtao.cofficemachine.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ForegroundService extends Service {

    private static final int Service_id=1;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ForegroundService","开启ForegroundService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ForegroundService","关闭ForegroundService");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //2.修改                   //通知
        if(Build.VERSION.SDK_INT<18){ //Android 4.3 以下
            //将service 设置为前台服务，可以取消通知栏 消息
            startForeground(Service_id,new Notification());//通知栏 不显示 用户无感知
        }else if(Build.VERSION.SDK_INT<26){//Android 4.3---Android 7.0
            //将service 设置为前台服务，可以取消通知栏 消息
            startForeground(Service_id,new Notification());
            //可以取消掉通知栏消息
            startService(new Intent(this,InnerService.class));
        }else{// android 8.0 以上
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(manager!=null){
                NotificationChannel channel=new NotificationChannel("channel","name",NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"channel");
                //Android 8.0 后 app 启动时不会弹出通知栏消息，当退出到后台时候，会弹出通知栏消息
                //Android 9.0  app 启动时会立即弹出通知栏消息，当退出到后台时候，会弹出通知栏消息 并且 需要权限
                startForeground(Service_id,builder.build());
            }

        }
        return START_STICKY;//1. 在service 挂掉后 利用系统 机制拉活    很多的情况下 步骤效果


    }
    //可以取消掉 通知栏
    public static class InnerService extends Service{

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(Service_id,new Notification());//通知栏 不显示 用户无感知
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
