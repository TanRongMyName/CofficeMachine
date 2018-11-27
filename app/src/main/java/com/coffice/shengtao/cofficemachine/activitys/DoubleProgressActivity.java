package com.coffice.shengtao.cofficemachine.activitys;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.service.JobHandlerService;
import com.coffice.shengtao.cofficemachine.service.LocalService;
import com.coffice.shengtao.cofficemachine.service.RemoteService;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

public class DoubleProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_progress);
        //启动双进程  不做修改 4.0 以下可以通过  5.0 以上失败  修改----
//        startService(new Intent(this, LocalService.class));
//        startService(new Intent(this, RemoteService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtils.d("开启任务调令");
            openJobService();
        } else {
            LogUtils.d("开启双服务");
            openTwoService();
        }
    }
    private void openTwoService() {
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));
    }
    private void openJobService() {
        Intent intent = new Intent();
        intent.setClass(DoubleProgressActivity.this, JobHandlerService.class);
        startService(intent);
    }

}
