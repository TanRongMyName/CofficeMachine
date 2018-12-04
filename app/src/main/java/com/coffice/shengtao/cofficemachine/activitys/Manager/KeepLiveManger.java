package com.coffice.shengtao.cofficemachine.activitys.Manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.coffice.shengtao.cofficemachine.activitys.TransparentActivity;
import com.coffice.shengtao.cofficemachine.broadcast.ScreenBroadcastReceiver;

import java.lang.ref.WeakReference;

public class KeepLiveManger {
    private static final KeepLiveManger ourInstance = new KeepLiveManger();

    public static KeepLiveManger getInstance() {
        return ourInstance;
    }

    private KeepLiveManger() {
    }

    private WeakReference<TransparentActivity> reference;
    public void setKeepLiveActivity(TransparentActivity activity){
        reference=new WeakReference<>(activity);
    }


    /**
     * 开启Activity 方法 和关闭 activity 方法  同时注册Receiver
     */

    public void startKeepLiveActivity(Context context){
        Intent intent=new Intent(context,TransparentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 关闭 keepLiveActivity----
     */
    public void finishKeepLiveActivity(){
        if(reference!=null&&reference.get()!=null){
            reference.get().finish();
        }
    }
    private ScreenBroadcastReceiver receiver;

    /**
     * 注册广播
     * @param context
     */
    public void RegisterKeepLiverReceiver(Context context){
        Log.d("KeepLiveManger","注册广播");
        if(receiver==null) {
            receiver = new ScreenBroadcastReceiver();
        }
        receiver.registerScreenBroadcastReceiver(context);


    }

    /**
     * 反注册
     * @param context
     */
    public void UnRegisterKeepLiverReceiver(Context context){
        if(receiver!=null){
            receiver.unRegisterScreenBroadcastReceiver(context);
        }
    }

}
