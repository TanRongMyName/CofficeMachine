package com.coffice.shengtao.cofficemachine.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

public class AlipayIntegrationActivity extends BaseActivity implements View.OnClickListener{
    private Button apaystandbox,apayreal,apaycreatcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_integration);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        super.initView();
        apaystandbox=findViewById(R.id.apaystadbox);
        apayreal=findViewById(R.id.apayreal);
        apaycreatcode=findViewById(R.id.apaycode);
    }

    @Override
    public void initEvent() {
        super.initEvent();
                apaystandbox.setOnClickListener(this);
                apayreal.setOnClickListener(this);
                apaycreatcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId()){
            case R.id.apayreal:
                intent=new Intent(AlipayIntegrationActivity.this,ApayRealActivity.class);
                break;
            case R.id.apaystadbox:
                intent=new Intent(AlipayIntegrationActivity.this,ApayStandboxActivity.class);
                break;
            case R.id.apaycreatcode:
                //intent=new Intent(AlipayIntegrationActivity.this,ApayRealActivity.class);
                break;
            default:
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }else{
            ToastUtils.showShort(AlipayIntegrationActivity.this,"功能没有开放");
        }
    }
}
