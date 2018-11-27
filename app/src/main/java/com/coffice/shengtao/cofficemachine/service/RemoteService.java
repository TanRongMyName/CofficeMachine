package com.coffice.shengtao.cofficemachine.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.coffice.shengtao.cofficemachine.AWakeAidlInterface;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

public class RemoteService extends Service {
    public RemoteService() {
    }
    private MyBinder binder;
    private ServiceConnection conn;

    @Override
    public void onCreate() {
        super.onCreate();
        // System.out.println("远程进程开启");
        init();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("远程进程启动");
        Intent intents = new Intent();
        intents.setClass(this, LocalService.class);
        bindService(intents, conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private void init() {
        if (conn == null) {
            conn = new MyConnection();
        }
        binder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    static class MyBinder extends AWakeAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public String getName() throws RemoteException {
            return "远程连接";
        }
    }

    class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("获取远程连接");
        }
        @Override
        public void onServiceDisconnected(ComponentName nme) {
            LogUtils.d("本地连接被干掉了");
            RemoteService.this.startService(new Intent(RemoteService.this,
                    LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this,
                    LocalService.class), conn, Context.BIND_IMPORTANT);
        }
    }
}
