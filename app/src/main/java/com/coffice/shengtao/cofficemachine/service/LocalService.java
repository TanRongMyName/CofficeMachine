package com.coffice.shengtao.cofficemachine.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.coffice.shengtao.cofficemachine.AWakeAidlInterface;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

public class LocalService extends Service {
    private ServiceConnection conn;
    private MyService myService;
    public LocalService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return myService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        if (conn == null) {
            conn = new MyServiceConnection();
        }
        myService = new MyService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("本地进程启动");
        Intent intents = new Intent();
        intents.setClass(this, RemoteService.class);
        bindService(intents, conn, Context.BIND_AUTO_CREATE);
        return START_STICKY;
    }

    class MyService extends AWakeAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
        @Override
        public String getName() throws RemoteException {
            return null;
        }
    }
    class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("获取连接");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("远程连接被干掉了");
            LocalService.this.startService(new Intent(LocalService.this,
                    RemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this,
                    RemoteService.class), conn, Context.BIND_AUTO_CREATE);

        }

    }
}
