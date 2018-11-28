package com.coffice.shengtao.cofficemachine.activitys;


import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Retrofit+Rxjava实现网络请求
 * 获取生成的二维码 ----
 */
public class RxJava_RetrofitActivity extends BaseActivity {

    @BindView(R.id.qrcode_ali)
    Button button10;
    @BindView(R.id.qrcode)
    ImageView qrcode;
    @BindView(R.id.qrcode_wechat)
    Button qrcodeWechat;
    @BindView(R.id.coderesult)
    EditText coderesult;
    @BindView(R.id.scancode)
    Button scancode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java__retrofit);
        binder = ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        //将输入框 不可编辑 同时取消 onFocues
        coderesult.setEnabled(false);
        coderesult.setFocusable(false);
        coderesult.setCursorVisible(false);
        coderesult.setVisibility(View.INVISIBLE);
        //而且不能显示  只有点击了显示 同时 onFocees
        //监听事件
        coderesult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                coderesult.setText(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                coderesult.setFocusable(false);
                coderesult.setCursorVisible(false);
                String code=coderesult.getText().toString();
                ToastUtils.showShort(RxJava_RetrofitActivity.this,"获取到的二维码是："+code);
            }
        });

    }

    @Override
    protected void onDestroy() {
        binder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.qrcode_ali, R.id.qrcode_wechat, R.id.scancode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qrcode_ali:
                break;
            case R.id.qrcode_wechat:
                break;
            case R.id.scancode:
                coderesult.setFocusable(true);
                coderesult.setCursorVisible(true);
                coderesult.setVisibility(View.VISIBLE);
                //获取到扫描吗后 ----不可 点击  -----可见 ----进行炒作
                break;
        }
    }
    //生成二维码
    // qrCodeEncoder = new QRCodeEncoder(this);
    //qrCodeEncoder.createQrCode2ImageView(textContent, ivQRCode);
    //////////////////////-----------权限控制需要这四个就可以了----------------/////////////////////////////
    boolean hasRequestPermission=false;
    private String[] cameraPermissions = {
            Manifest.permission.CAMERA
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
                ToastUtils.showShort(RxJava_RetrofitActivity.this,"没有获取到权限 闪退");
                LogUtils.d("没有获取到权限 闪退");
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }
}
