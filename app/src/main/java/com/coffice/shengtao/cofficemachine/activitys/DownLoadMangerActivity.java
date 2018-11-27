package com.coffice.shengtao.cofficemachine.activitys;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.broadcast.DownLoadCompleteReceiver;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//使用说明：很好   https://blog.csdn.net/csdn_aiyang/article/details/64126379
public class DownLoadMangerActivity extends BaseActivity {
    public String downloadFileUrl="http://192.168.1.161:8080/HelloWorld2/test.apk";
    @BindView(R.id.btnDown)
    Button btnDown;
    @BindView(R.id.btnPause)
    Button btnPause;
    @BindView(R.id.btnRemove)
    Button btnRemove;
    @BindView(R.id.btndisplay)
    Button btndisplay;
    @BindView(R.id.downProgress)
    ProgressBar downProgress;
    @BindView(R.id.mylist)
    ListView mylist;

    //重复执行程序的定时器 --- 同样可以更新UI
    public ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
    //下载handle 来更新UI
    public ScheduledFuture<?> future;

    public Thread uploadView;
    private SimpleAdapter adapter;
    private List<Map<String, String>> data ;
    DownloadManager downManager ;
    private DownLoadCompleteReceiver receiver;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    downProgress.setProgress(100);
                    LogUtils.d("下载成功");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    //更新UI
                    downProgress.setProgress((Integer) msg.obj);
                    LogUtils.d("下载中...."+(Integer) msg.obj);
                    break;

                case DownloadManager.STATUS_FAILED:
                    // 提示下载失败
                    ToastUtils.showLong(DownLoadMangerActivity.this,"下载失败");
                    break;

                case DownloadManager.STATUS_PENDING:
                    LogUtils.d("下载准备中.....");
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_manger);
        binder = ButterKnife.bind(this);
       initData();

    }

    @Override
    public void initData() {
        super.initData();
        downManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        data = new ArrayList<Map<String,String>>();
        adapter = new SimpleAdapter(this, data,
                R.layout.item_down_load_task,
                new String[]{"downid","title","address","status"},
                new int[]{R.id.downid,R.id.title,R.id.address,R.id.status});

        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Map<String, String> map = data.get(position);
                downManager.remove(Long.valueOf(map.get("downid")));
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction("com.coffice.shengtao.cofficemachine.broadcast.DownLoadCompleteReceiver");
        receiver = new DownLoadCompleteReceiver();
        receiver.setDownloadactivity(this);
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
    long id;
    long idPro;
    @OnClick({R.id.btnDown, R.id.btnPause, R.id.btnRemove, R.id.btndisplay})
    public void onViewClicked(View view) {
        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(downloadFileUrl));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("下载");
        request.setDescription("今日头条正在下载");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "mydown");
        switch (view.getId()) {
            case R.id.btnDown:
                //将下载请求添加至downManager，注意enqueue方法的编号为当前
                id= downManager.enqueue(request);
                btnDown.setClickable(false);
                downProgress.setProgress(0);
                uploadView=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        queryDownloadProgress(id,downManager);
                    }
                });
                uploadView.start();
                //将这个线程结束-----
                break;
            case R.id.btnPause:
                downManager.remove(id);
                //手动发送广播 其实不用 ----会受到广播的
                Intent intent = new Intent();
                intent.putExtra("first", "这是其他应用发送的广播！！");
                intent.setAction("com.coffice.shengtao.cofficemachine.broadcast.DownLoadCompleteReceiver");
                sendBroadcast(intent);
                if(uploadView!=null){//这样 就不会一个线程 一直被挂起了
                    uploadView.interrupt();
                    //uploadView.stop();//相当于抛出异常来停止线程 但是 程序会崩溃 除非提前catch
                    uploadView=null;
                }
                break;
            case R.id.btnRemove:
                idPro= downManager.enqueue(request);
                //一秒钟后执行
                //重复执行的目的就是更新UI 但是 ---结束后没有停止-----
                future=ses.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        queryTaskByIdandUpdateView(idPro);
                    }
                }, 0, 1, TimeUnit.SECONDS);
                break;
            case R.id.btndisplay:

                data.clear();
                //将下载中所有的 下载状态 遍历出来  显示到listview 上
                queryDownTask(downManager,DownloadManager.STATUS_FAILED);
                queryDownTask(downManager,DownloadManager.STATUS_PAUSED);
                queryDownTask(downManager,DownloadManager.STATUS_PENDING);
                queryDownTask(downManager,DownloadManager.STATUS_RUNNING);
                queryDownTask(downManager,DownloadManager.STATUS_SUCCESSFUL);
                adapter.notifyDataSetChanged();
                break;
        }
    }


    private void queryDownTask(DownloadManager downManager,int status) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(status);
        Cursor cursor= downManager.query(query);

        while(cursor.moveToNext()){
            String downId= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            //String statuss = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String size= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            Map<String, String> map = new HashMap<String, String>();
            map.put("downid", downId);
            map.put("title", title);
            map.put("address", address);
            map.put("status", sizeTotal+":"+size);
            this.data.add(map);
        }
        cursor.close();
    }

    private void queryTaskByIdandUpdateView(long id){
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor= downManager.query(query);
        String size="0";
        String sizeTotal="0";
        if(cursor.moveToNext()){
            size= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
        }
        cursor.close();
        final String finalSizeTotal = sizeTotal;
        final String finalSize = size;
                downProgress.setMax(Integer.valueOf(finalSizeTotal));
                downProgress.setProgress(Integer.valueOf(finalSize));
                LogUtils.d("maxsize:"+finalSizeTotal+"---  ----finalSize=="+finalSize);
                //使用 ---
                LogUtils.d("maxsize:"+Integer.valueOf(finalSizeTotal)+"---  ----finalSize=="+Integer.valueOf(finalSize));
                int a=Integer.valueOf(finalSizeTotal)-Integer.valueOf(finalSize);
                LogUtils.d("a:"+a+"---");
                if(a==0){
                    LogUtils.d("downLoading finish-----取消线程池 不要重复定时的发送任务了");
                    ses.shutdown();// 同样也可以
                    //future.cancel(false);---可以取消 重复执行的线程池
                }
    }
    //下载 显示进度条   重新下载在前判断是否有在下载 如果有 不加入 如果没有加入    其实 可以强制 不让重新点击-------
    private void queryDownloadProgress(long requestId, DownloadManager downloadManager) {
        DownloadManager.Query query=new DownloadManager.Query();
        //根据任务编号id查询下载任务信息
        query.setFilterById(requestId);
        try {
            boolean isGoging=true;//
            //柱塞下载
            while (isGoging) {
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {

                    //获得下载状态
                    int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (state) {
                        case DownloadManager.STATUS_SUCCESSFUL://下载成功
                            isGoging=false;
                            handler.obtainMessage(downloadManager.STATUS_SUCCESSFUL).sendToTarget();//发送到主线程，更新ui
                            break;
                        case DownloadManager.STATUS_FAILED://下载失败
                            isGoging=false;
                            handler.obtainMessage(downloadManager.STATUS_FAILED).sendToTarget();//发送到主线程，更新ui
                            break;
                        case DownloadManager.STATUS_RUNNING://下载中
                            /**
                             * 计算下载下载率；
                             */
                            int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                            handler.obtainMessage(downloadManager.STATUS_RUNNING, progress).sendToTarget();//发送到主线程，更新ui
                            break;

                        case DownloadManager.STATUS_PAUSED://下载停止
                            handler.obtainMessage(DownloadManager.STATUS_PAUSED).sendToTarget();
                            break;

                        case DownloadManager.STATUS_PENDING://准备下载
                            handler.obtainMessage(DownloadManager.STATUS_PENDING).sendToTarget();
                            break;
                    }
                }
                if(cursor!=null){
                    cursor.close();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public  void setButtonClickedAble(boolean isable){
        btnDown.setClickable(isable);
    }

}
