package com.coffice.shengtao.cofficemachine.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.Manager.KeepLiveManger;
import com.coffice.shengtao.cofficemachine.service.ForegroundService;
import com.coffice.shengtao.cofficemachine.service.JobHandlerService;
import com.coffice.shengtao.cofficemachine.service.LocalService;
import com.coffice.shengtao.cofficemachine.service.RemoteService;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoubleProgressActivity extends BaseActivity {

    @BindView(R.id.doubleone)
    Button doubleone;
    @BindView(R.id.doubletwo)
    Button doubletwo;
    @BindView(R.id.doublethree)
    Button doublethree;
    @BindView(R.id.doublefour)
    Button doublefour;


    //定义一个标记用来ondescroy
    int DesType=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_progress);
        binder = ButterKnife.bind(this);
    }

    @OnClick({R.id.doubleone, R.id.doubletwo, R.id.doublethree, R.id.doublefour})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.doubleone:
                DesType=1;
                KeepLiveManger.getInstance().RegisterKeepLiverReceiver(this);
                break;
            case R.id.doubletwo:
                DesType=2;
                startService(new Intent(this,ForegroundService.class));
                break;
            case R.id.doublethree:
                DesType=3;
                startService(new Intent(this,LocalService.class));
                break;
            case R.id.doublefour:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    LogUtils.d("开启任务调令");
                    openJobService();
                } else {
                    LogUtils.d("开启双服务");
                    openTwoService();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        switch (DesType){
            case 1:
                KeepLiveManger.getInstance().UnRegisterKeepLiverReceiver(this);
                break;
            case 2:
                //关闭服务
                stopService(new Intent(this,ForegroundService.class));
                break;
            case 3:
                //关闭服务
                //stopService(new Intent(this,LocalService.class));

                break;
            case 4:

                break;
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


//启动双进程  不做修改 4.0 以下可以通过  5.0 以上失败  修改----
//        startService(new Intent(this, LocalService.class));
//        startService(new Intent(this, RemoteService.class));
}
