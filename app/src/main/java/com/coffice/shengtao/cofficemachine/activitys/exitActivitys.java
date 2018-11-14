package com.coffice.shengtao.cofficemachine.activitys;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 一键退出 app
 */
public class exitActivitys extends BaseActivity {


    @BindView(R.id.exitapp)
    Button exitapp;
    @BindView(R.id.restartapp)
    Button restartapp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_activitys);
        binder = ButterKnife.bind(this);
        exit0();
    }

    //需要5.0以上的系统  一键退出
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void exit0() {
        // 1\. 通过Context获取ActivityManager
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        // 2\. 通过ActivityManager获取任务栈
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();

        // 3\. 逐个关闭Activity
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
        // 4\. 结束进程
        System.exit(0);
    }

    public void restatApp() {
        //android 重启app
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.exitapp, R.id.restartapp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.exitapp:
                exit0();
                break;
            case R.id.restartapp:
                restatApp();
                break;
        }
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
//    public void restartApp() {
//        //启动页--- 切换到 闪屏页
//        Intent intent = new Intent(BaseApplication.instance(), SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        BaseApplication.instance().startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }
//
//    /**
//     * 重新启动App -> 不杀进程,缓存的东西不清除,启动快
//     * //启动页--- 切换到 闪屏页
//     */
//    public void restartApp2() {
//        final Intent intent = BaseApplication.instance().getPackageManager()
//                .getLaunchIntentForPackage(BaseApplication.instance().getPackageName());
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        BaseApplication.instance().startActivity(intent);
//    }


}
