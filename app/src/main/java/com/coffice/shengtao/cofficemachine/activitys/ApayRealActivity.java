package com.coffice.shengtao.cofficemachine.activitys;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
//真实的调用
public class ApayRealActivity extends BaseActivity {
    boolean hasRequestPermission=false;
    private String[] cameraPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apay_real);
    }


    public void onRealPay(View view){
        //公司的APPID
        MyALipayUtils.ALiPayBuilder builder =new MyALipayUtils.ALiPayBuilder();
        MyALipayUtils myALipayUtils =builder.build();
        builder.setAppid(GlobalData.ShengTao_APPID);
        builder.setMoney("0.1");
        builder.setOrderTradeId(myALipayUtils.getOutTradeNo());
        builder.setRsa2(GlobalData.ShengTao_RSA2);
        builder.setTitle("午餐消费");
        builder.setNotifyUrl("https://auth.example.com/authRedirect");
        myALipayUtils.toALiPay(ApayRealActivity.this);

        //自己的APPID pid
//         MyALipayUtils.ALiPayBuilder builder =new MyALipayUtils.ALiPayBuilder();
//         MyALipayUtils myALipayUtils =builder.build();
//        builder.setAppid(GlobalData.APayId);
//        builder.setMoney("0.1");
//        builder.setOrderTradeId(myALipayUtils.getOutTradeNo());
//        builder.setRsa2(GlobalData.APayRSA2);
//        builder.setTitle("午餐消费");
//        builder.setNotifyUrl("https://auth.example.com/authRedirect");
//        myALipayUtils.toALiPay(ApayRealActivity.this);
    }
    //授权成功 支付出现异常 是没有签约？？？？等待疑问中
    public void onauthok(View view){
        //公司的APPID
        MyALipayUtils.ALiPayBuilder builder =new MyALipayUtils.ALiPayBuilder();
        MyALipayUtils myALipayUtils =builder.build();
        builder.setAppid(GlobalData.ShengTao_APPID);

        builder.setPid(GlobalData.ShengTao_PID);
        builder.setTarget_id("测试使用");

        builder.setMoney("0.1");
        builder.setOrderTradeId(myALipayUtils.getOutTradeNo());
        builder.setRsa2(GlobalData.ShengTao_RSA2);
        builder.setTitle("午餐消费");
        builder.setNotifyUrl("https://auth.example.com/authRedirect");
        myALipayUtils.antoker(ApayRealActivity.this);
    }

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
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }
}
