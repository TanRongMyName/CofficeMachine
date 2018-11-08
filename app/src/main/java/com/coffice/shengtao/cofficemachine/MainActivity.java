package com.coffice.shengtao.cofficemachine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.activitys.GPSAddressActivity;
import com.coffice.shengtao.cofficemachine.activitys.LitePalTestActivity;
import com.coffice.shengtao.cofficemachine.activitys.ScanCodeActivity;
import com.coffice.shengtao.cofficemachine.activitys.TastPayTwoActivity;
import com.coffice.shengtao.cofficemachine.activitys.TestAPay;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private Button button1, button2, button3, button4,gpsaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    @Override
    public void initView() {
        super.initView();
        button1=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button5);
        gpsaddress=findViewById(R.id.readaddress);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        gpsaddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId()){
            case R.id.button:
                intent=new Intent(this,LitePalTestActivity.class);
                break;
            case R.id.button2:
                break;
            case R.id.button3:

                intent=new Intent(this,ScanCodeActivity.class);
                break;
            case R.id.button5:
                intent=new Intent(this,TestAPay.class);
                break;
            case R.id.readaddress:
                intent=new Intent(this,GPSAddressActivity.class);
                break;
            default:
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }else{
            ToastUtils.showShort(this,"intent 为空");
            LogUtils.d("intent 为空");
        }
    }
}
