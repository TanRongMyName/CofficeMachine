package com.coffice.shengtao.cofficemachine.activitys;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.qrphoto.qr.QrActivity;
//启动activity 后申请权限
//也可将要使用权限的View添加接口 让其自己去实现权限的申请
public class ScanCodeActivity extends BaseActivity {
//    集成
//    https://github.com/maogedadada/qrphoto
//    allprojects {
//        repositories {
//				...
//            maven { url 'https://jitpack.io' }
//        }
//    }
//    dependencies {
//        compile 'com.github.maogedadada:qrphoto:0.0.6'
//    }

    //支付宝扫码有点慢  微信挺快的 可能原因是  支付宝中间有了自己的Loge 成了异形码

    //添加权限
    private String[] cameraPermissions = {
            Manifest.permission.CAMERA
    };
    TextView tv;
    private boolean hasRequestPermission=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        tv=findViewById(R.id.codeshow);
    }

    public void onScanCode(View view){
        Intent intent = new Intent(ScanCodeActivity.this, QrActivity.class);
        intent.putExtra("title","二维码扫描");
        intent.putExtra("text","扫描中。。。");
        startActivityForResult(intent,1000);
    }
    public void onJumpActivity(View view){
        Intent intent=new Intent(ScanCodeActivity.this,ScanCode1Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000&&data!=null){
            String result = data.getStringExtra("result");
            if (result!=null) {
                LogUtils.d(result);
                tv.setText(result);
            }
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        if (checkDangerousPermissions(this, cameraPermissions)){

        }else {
            if (!hasRequestPermission){
                showScanCodeTip();
            }
        }
    }

    private void showScanCodeTip() {
        ScanCodeTipDialog dialog = new ScanCodeTipDialog();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hasRequestPermission = true;
                requestDangerousPermissions(cameraPermissions, REQUEST_CODE_CAMERA);
            }
        });
        dialog.show(getSupportFragmentManager(), ScanCodeActivity.class.getSimpleName());
    }

    @Override
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        if (requestCode == REQUEST_CODE_CAMERA){
            if (!granted){
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }
}
