package com.coffice.shengtao.cofficemachine.activitys;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.httprequest.IRequestCallback;
import com.coffice.shengtao.cofficemachine.httprequest.IRequestManager;
import com.coffice.shengtao.cofficemachine.httprequest.RequestFactory;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;
import com.mob.tools.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/*
 *每个Android版本的发布，对于安全性问题的要求越来越高，
 * 也为Android程序员增加了额外的工作量。Android6.0引入动态权限控制(Runtime Permissions)，
 * Android7.0引入私有目录被限制访问和StrictMode API 。私有目录被限制访问是指在Android7.0
 * 中为了提高应用的安全性，在7.0上应用私有目录将被限制访问，这与iOS的沙盒机制类似。
 * StrictMode API是指禁止向你的应用外公开 file:// URI。 如果一项包含文件 file:// URI类型
 * 的 Intent 离开你的应用，则会报出异常。
 * */
public class AutoInstall_Start7Activity extends BaseActivity {
    public String TestAppUrl="http://192.168.1.161:8080/HelloWorld2/test.apk";
    //下载器
    private DownloadManager downloadManager;
    //下载的ID
    private long downloadId;
    @BindView(R.id.downloadbefore)
    Button downloadbefore;
    @BindView(R.id.downloadafter)
    Button downloadafter;
    //网络下载 ---两种方式----DownLoadManger---监控 download manger 下载的状况
    //Okhttp 对返回值得一种外调  --- 深挖 RxJava

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_install__start7);
        binder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        binder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.downloadbefore, R.id.downloadafter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.downloadbefore:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DownLoadAPP();
                    }
                }).start();
                break;
            case R.id.downloadafter:
               // DownOkHttpAPP();
                downloadFile3();
                break;
        }
    }

    /**
     * 使用DownLoadManger 下载APP 位置都是一样的
     * https://www.jianshu.com/p/6816977bfdeb
     */
    public void DownLoadAPP(){
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(TestAppUrl));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        request.setVisibleInDownloadsUi(true);
        //设置下载的路径   request.setDestinationInExternalFilesDir Environment.DIRECTORY_DOWNLOADS
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath() , "test.apk");
        //获取DownloadManager
        downloadManager = (DownloadManager) AutoInstall_Start7Activity.this.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);
        //对ID柱塞监控
        DownloadManager.Query query=new DownloadManager.Query();
        //根据任务编号id查询下载任务信息
        query.setFilterById(downloadId);
        try {
            boolean isGoging=true;//
            //柱塞下载
            String address=null;
            while (isGoging) {
                Cursor cursor = downloadManager.query(query);

                if (cursor != null && cursor.moveToFirst()) {
                    //获得下载状态
                    int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    switch (state) {
                        case DownloadManager.STATUS_SUCCESSFUL://下载成功
                            isGoging=false;
                            RunOnUi(1,address);
                            break;
                        case DownloadManager.STATUS_FAILED://下载失败
                            RunOnUi(0,address);
                            isGoging=false;
                            break;
                        case DownloadManager.STATUS_RUNNING://下载中
                            LogUtils.d("下载中.....address=="+address);
                            break;
                        case DownloadManager.STATUS_PAUSED://下载停止
                            break;
                        case DownloadManager.STATUS_PENDING://准备下载
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

    /**
     * 使用Okhttp 网络框架 下载文件---
     */
    public void DownOkHttpAPP(){
        //下载路径，如果路径无效了，可换成你的下载路径
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD","startTime="+startTime);
        IRequestManager requestManager = RequestFactory.getRequestManager(RequestFactory.NetRequestType_OKHTTP);
        requestManager.get2(TestAppUrl, new IRequestCallback() {
            @Override
            public void onSuccess(String response) {
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.i("DOWNLOAD","download failed");
            }
            @Override
            public void onSuccess(Response response) {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    String mSDCardPath= Environment.getExternalStorageDirectory().getAbsolutePath();
                    File dest = new File(mSDCardPath,   TestAppUrl.substring(TestAppUrl.lastIndexOf("/") + 1));
                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    Log.i("DOWNLOAD","download success   dest.getAbsolutepath==" +dest.getAbsolutePath());
                    Log.i("DOWNLOAD","totalTime="+ (System.currentTimeMillis() - startTime));
                    installAPP(dest);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("DOWNLOAD","download failed----发生异常");
                } finally {
                    if(bufferedSink != null){
                        try {
                            bufferedSink.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    private void downloadFile3(){
        //下载路径，如果路径无效了，可换成你的下载路径
        final String url = TestAppUrl;
        final long startTime = System.currentTimeMillis();
        Log.i("DOWNLOAD","startTime="+startTime);

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                e.printStackTrace();
                Log.i("DOWNLOAD","download failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("DOWNLOAD","response" +response.toString());
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    String mSDCardPath= Environment.getExternalStorageDirectory().getAbsolutePath();
                    File dest = new File(mSDCardPath,   url.substring(url.lastIndexOf("/") + 1));
                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    Log.i("DOWNLOAD","response.body().source()" +response.body().source());
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    Log.i("DOWNLOAD","download success");
                    Log.i("DOWNLOAD","totalTime="+ (System.currentTimeMillis() - startTime));
                    installAPP(dest);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("DOWNLOAD","download failed");
                } finally {
                    if(bufferedSink != null){
                        bufferedSink.close();
                    }

                }
            }
        });
    }


    public void RunOnUi(final int type, final String filePath){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(type==1){
                    ToastUtils.showShort(AutoInstall_Start7Activity.this,"下载成功");
                    //开始安装
                    File file= FileUtils.getFileByPath(filePath.substring(7,filePath.length()));
                    LogUtils.d("file.exists: "+file.exists());
                    LogUtils.d("file.exists: "+file.getAbsolutePath());
                    installAPP(file);
                }else{
                    ToastUtils.showShort(AutoInstall_Start7Activity.this,"下载成功");
                }
            }
        });
    }







    //----------------------权限申请-----------------------
    boolean hasRequestPermission=false;
    private String[] cameraPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onResume() {
        super.onResume();
        if (checkDangerousPermissions(this, cameraPermissions)){
        }else {
            if (!hasRequestPermission){
                showScanCodeTip(cameraPermissions);
                hasRequestPermission=true;
            }
        }
    }
    @Override
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        if (requestCode == REQUEST_CODE_CAMERA){
            if (!granted){
                ToastUtils.showShort(AutoInstall_Start7Activity.this,"没有获取到权限 闪退");
                LogUtils.d("没有获取到权限 闪退");
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }



}
