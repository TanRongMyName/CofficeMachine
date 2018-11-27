package com.coffice.shengtao.cofficemachine.broadcast;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.activitys.DownLoadMangerActivity;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

/**
 * DowmLoadManger  用来监听 下载时候 文件是否下载完事  --用来在 通知栏提示----
 */
public class DownLoadCompleteReceiver extends BroadcastReceiver {
    public DownLoadMangerActivity downloadactivity;
    public void setDownloadactivity(DownLoadMangerActivity downloadactivity){
        this.downloadactivity=downloadactivity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d(intent.getAction()+" intent.getString"+intent.getStringExtra("first"));
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Toast.makeText(context, "编号："+id+"的下载任务已经完成！", Toast.LENGTH_SHORT).show();
            //调用 activity 的方法 将点击事件变得可以点击
            LogUtils.d("context:----"+context);
            downloadactivity.setButtonClickedAble(true);
        }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
            Toast.makeText(context, "别瞎点！！！", Toast.LENGTH_SHORT).show();
        }else {
            LogUtils.d("自定义的过滤器:----");
        }
    }


}
