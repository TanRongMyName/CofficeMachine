package com.coffice.shengtao.cofficemachine.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;
import com.coffice.shengtao.cofficemachine.utils.aplayutils.OrderInfoUtil2_0;
import com.coffice.shengtao.cofficemachine.utils.aplayutils.PayResult;

import org.litepal.util.LogUtil;

import java.util.Map;

public class ApayStandboxActivity extends BaseActivity {
    private String[] cameraPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private boolean hasRequestPermission=false;
    private static final int SDK_PAY_FLAG = 1001;
    //Globaldate 中
    private String RSA2_PRIVATE = GlobalData.Sand_RSA2;
    public  String APPID = GlobalData.Sand_APayId;

    //用来接收回调信息
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //同步获取结果
                    String resultInfo = payResult.getResult();
                    LogUtil.d("Pay", "Pay:" + resultInfo);
                    LogUtils.d("Pay:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                       ToastUtils.showShort(ApayStandboxActivity.this, "支付成功");
                    } else {
                        ToastUtils.showShort(ApayStandboxActivity.this, "支付失败");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_apay);
    }


    public void onStandPay(View view){
        //秘钥验证的类型 true:RSA2 false:RSA
        boolean rsa = true;
        //构造支付订单参数列表
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa);
        //构造支付订单参数信息
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //对支付参数信息进行签名
        String sign = OrderInfoUtil2_0.getSign(params, RSA2_PRIVATE, rsa);
        //订单信息
        final String orderInfo = orderParam + "&" + sign;
        //异步处理
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(ApayStandboxActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtils.d("result==="+result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
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
        dialog.show(getSupportFragmentManager(), ApayStandboxActivity.class.getSimpleName());
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
