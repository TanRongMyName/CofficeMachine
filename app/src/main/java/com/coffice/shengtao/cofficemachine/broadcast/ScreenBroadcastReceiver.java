package com.coffice.shengtao.cofficemachine.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.coffice.shengtao.cofficemachine.utils.LogUtils;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = ScreenBroadcastReceiver.class.getSimpleName();
    private boolean isRegisterReceiver = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏
            LogUtils.d(TAG, "onReceive: 1");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            // 锁屏
            LogUtils.d(TAG, "onReceive: 2");
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            // 解锁
            LogUtils.d(TAG, "onReceive: 3");
        }
    }
    //注册广播 监听事件
    public void registerScreenBroadcastReceiver(Context mContext) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(ScreenBroadcastReceiver.this, filter);
        }
    }
    //解除广播 ---
    public void unRegisterScreenBroadcastReceiver(Context mContext) {
        if (isRegisterReceiver) {
            isRegisterReceiver = false;
            mContext.unregisterReceiver(ScreenBroadcastReceiver.this);
        }
    }
}
