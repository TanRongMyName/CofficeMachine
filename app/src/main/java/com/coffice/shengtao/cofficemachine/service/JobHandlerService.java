package com.coffice.shengtao.cofficemachine.service;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import java.util.List;

public class JobHandlerService extends JobService {
    public JobHandlerService() {
    }
    private JobScheduler mJobScheduler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("服务被创建");
//        startService(new Intent(this, LocalService.class));
//        startService(new Intent(this, RemoteService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(startId++,
                    new ComponentName(getPackageName(), JobHandlerService.class.getName()));
            builder.setPeriodic(5000); //每隔5秒运行一次
            builder.setRequiresCharging(true);
            builder.setPersisted(true);  //设置设备重启后，是否重新执行任务
            builder.setRequiresDeviceIdle(true);

            if (mJobScheduler.schedule(builder.build()) <= 0) {
                LogUtils.d("工作失败");
            } else {
                LogUtils.d("工作成功");
            }
        }
        return START_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        LogUtils.d("服务启动");
//        || isServiceRunning(this, "com.ph.myservice.RemoteService") == false
        LogUtils.d("开始工作");
        if (!isServiceRunning(getApplicationContext(), "com.coffice.shengtao.cofficemachine.service.LocalServer") && !isServiceRunning(getApplicationContext(), "com.coffice.shengtao.cofficemachine.service.RemoteServer")) {
            startService(new Intent(this, LocalService.class));
//            startService(new Intent(this, RemoteService.class));
        }

       /* boolean serviceRunning = isServiceRunning(getApplicationContext(), "com.ph.myservice");
        System.out.println("进程一" + serviceRunning);

        boolean serviceRunning2 = isServiceRunning(getApplicationContext(), "com.ph.myservice:remote");
        System.out.println("进程二" + serviceRunning2);*/
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogUtils.d("结束工作");
        if (!isServiceRunning(this, "com.coffice.shengtao.cofficemachine.service.LocalServer") && !isServiceRunning(this, "com.coffice.shengtao.cofficemachine.service.RemoteServer")) {
            startService(new Intent(this, LocalService.class));
           // startService(new Intent(this, RemoteService.class));
        }
        return false;
    }

    // 服务是否运行
    public boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) this
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {// 获取运行服务再启动
            System.out.println(info.processName);
            if (info.processName.equals(serviceName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }
}
