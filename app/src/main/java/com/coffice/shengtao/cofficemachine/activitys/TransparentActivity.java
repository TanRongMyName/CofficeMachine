package com.coffice.shengtao.cofficemachine.activitys;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.coffice.shengtao.cofficemachine.activitys.Manager.KeepLiveManger;

/**
 * 透明的activity 使用的一相数 将activity 进程等级拉高
 */
public class TransparentActivity extends BaseActivity {
    @Override
    public void onCreate( Bundle savedInstanceState,  PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Window window=getWindow();
        window.setGravity(Gravity.START|Gravity.TOP);
        WindowManager.LayoutParams params=window.getAttributes();
        params.x=0;
        params.y=0;
        params.width=1;
        params.height=1;
        window.setAttributes(params);
        KeepLiveManger.getInstance().setKeepLiveActivity(this);
        Log.d("KeepLiveActivity","开启Activity");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("KeepLiveActivity","关闭Activity");
    }

}
