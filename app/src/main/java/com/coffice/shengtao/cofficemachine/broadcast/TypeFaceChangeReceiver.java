package com.coffice.shengtao.cofficemachine.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.data.GlobalData;

public class TypeFaceChangeReceiver extends BroadcastReceiver {
    private boolean isregister=false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(GlobalData.TYPEFACECHANGEACTION.equals(intent.getAction())){
            String typeface = intent.getStringExtra("typeface");
            //改变未销毁尚存在的Activity的字体
//            if(context instanceof BaseActivity) {
//                ((BaseActivity)context).onTypefaceChange(typeface);
//            }
        }

    }

    /**
     * 注册广播
     */
    public void registerReceiver(Context context){
        if(!isregister){
            isregister=true;
             IntentFilter typefaceFilter=new IntentFilter();
            typefaceFilter.addAction(GlobalData.TYPEFACECHANGEACTION);
            context.registerReceiver(this,typefaceFilter);
        }
    }

    /**
     * 解除广播
     */
    public void unRegisterReceiver(Context context){
        if(isregister) {
            context.unregisterReceiver(this);
            isregister = false;
        }
    }
}
