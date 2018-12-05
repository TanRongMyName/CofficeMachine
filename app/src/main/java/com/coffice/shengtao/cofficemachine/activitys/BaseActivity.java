package com.coffice.shengtao.cofficemachine.activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.coffice.shengtao.cofficemachine.broadcast.TypeFaceChangeReceiver;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.SPUtils;
import com.coffice.shengtao.cofficemachine.utils.TypefaceUtils;

import java.io.File;
import java.util.List;

import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CAMERA = 0x123;
    public void initView(){

    }
    public void initEvent(){

    }
    public void initData(){

    }
    public Unbinder binder;
    //字体变化检测的广播  改变字体
    public TypeFaceChangeReceiver receiver;
    //对权限的申请
    /**
     * 请求权限
     */
    public void requestDangerousPermissions(String[] permissions, int requestCode) {
        if (checkDangerousPermissions(permissions)){
            handlePermissionResult(requestCode, true);
            return;
        }
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    /**
     * 检查是否已被授权危险权限
     * @param permissions
     * @return
     */
    public boolean checkDangerousPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return false;
            }
        }
        return true;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean granted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }
        boolean finish = handlePermissionResult(requestCode, granted);
        if (!finish){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 处理请求危险权限的结果
     * @return
     */
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        return false;
    }



    /**
     * 检查是否已被授权危险权限
     * @param permissions
     * @return
     */
    public boolean checkDangerousPermissions(Activity ac, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(ac, permission)) {
                return false;
            }
        }
        return true;
    }

    public  void showScanCodeTip(final String[] requestprimarys) {
        ScanCodeTipDialog dialog = new ScanCodeTipDialog();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                requestDangerousPermissions(requestprimarys, REQUEST_CODE_CAMERA);
            }
        });
        dialog.show(getSupportFragmentManager(), ApayStandboxActivity.class.getSimpleName());
    }


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


    /*对虚拟键盘的操作*/
    public final void closeIME(View v) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0); // 0 force close IME
        v.clearFocus();
    }

    public final void closeIMEWithoutFocus(View v) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0); // 0 force close IME
    }

    public void openIME(final EditText v) {
        final boolean focus = v.requestFocus();
        if (v.hasFocus()) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    boolean result = mgr.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                    LogUtils.d("openIME " + focus + " " + result);
                }
            });
        }
    }

    /**
     * 安装app 的方法 重写的原因是----7.0不能实现自动安装和启动
     */
    public void installAPP(File apkFile){
        //同时配置XML 文件
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(this, "com.coffice.shengtao.cofficemachine.fileprovider", apkFile);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加一个FLAG_GRANT_READ_URI_PERMISSION来取得目标文件使用权限。
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        } else{
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(install);
        }
    }




    /*******************权限使用的Activity 有****************************************/
//    AutoInstall_Start7Activity
//    DataBaseControl1Activity
//    RxJava_RetrofitActivity
//    ApayStandboxActivity  没有抽象出来

    //图片框架使用的位置是：GreenDaoActivity  使用的 图片加载
    //网络使用的位置是：GPSAddressActivity  --- 根据URL 访问百度地图 当前的位置   下载文件使用 okhttp 可以直接接受 response 获取数据



    //*****************************检测字体变化的广播设置**** 未使用*************************************
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        receiver=new TypeFaceChangeReceiver();
//        receiver.registerReceiver(this);
//        onTypefaceChange((String) SPUtils.get(this,GlobalData.FONTTYPE_PATH_KEY,""));
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        receiver.unRegisterReceiver(this);
//    }
//    /**
//     * 设置当前activity的layout
//     * @return 当前界面的布局id
//     */
//    protected abstract int getLayout();//将布局设置到 TypeFontUtils 上可以使用对其的View 进行遍历
//
//    /**
//     * 字体改变
//     */
//    public void onTypefaceChange(String typeface){
//        TypefaceUtils.replaceFont(this, typeface);
//    }

}
