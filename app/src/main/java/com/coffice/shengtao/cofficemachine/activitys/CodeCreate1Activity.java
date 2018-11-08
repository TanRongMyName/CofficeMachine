package com.coffice.shengtao.cofficemachine.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.coffice.shengtao.cofficemachine.R;
import com.qingmei2.library.encode.QRCodeEncoder;

public class CodeCreate1Activity extends BaseActivity implements View.OnClickListener{
    ImageView ivQRCode;
    private QRCodeEncoder qrCodeEncoder;

    private String textContent = "https://github.com/qingmei2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_create);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_create_icon).setOnClickListener(this);
        qrCodeEncoder = new QRCodeEncoder(this);
        ivQRCode =  findViewById(R.id.iv_qrcode_bg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                qrCodeEncoder.createQrCode2ImageView(textContent, ivQRCode);
                break;
            case R.id.btn_create_icon:
                qrCodeEncoder.createQrCode2ImageView(textContent, ivQRCode, R.mipmap.coffee);
                break;
        }
    }
}
