package com.coffice.shengtao.cofficemachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.activitys.AlipayIntegrationActivity;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.activitys.BottomMenuListActivity;
import com.coffice.shengtao.cofficemachine.activitys.GPSAddressActivity;
import com.coffice.shengtao.cofficemachine.activitys.GreenDaoActivity;
import com.coffice.shengtao.cofficemachine.activitys.LitePalTestActivity;
import com.coffice.shengtao.cofficemachine.activitys.MoreActivity;
import com.coffice.shengtao.cofficemachine.activitys.NetChangeActivity;
import com.coffice.shengtao.cofficemachine.activitys.ScanCodeActivity;
import com.coffice.shengtao.cofficemachine.activitys.ApayStandboxActivity;
import com.coffice.shengtao.cofficemachine.activitys.SlinMenuActivity;
import com.coffice.shengtao.cofficemachine.activitys.exitActivitys;
import com.coffice.shengtao.cofficemachine.utils.ExampleUtil;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private Button button1, button2, button3, button4,gpsaddress,netChangeRequest,
            bottommenu,slidingMenu,onPowerStart,activityexit,jpush,moreActivity;
    public static boolean isForeground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        //注册广播
        registerMessageReceiver();  // used for receive msg
    }

    @Override
    public void initView() {
        super.initView();
        button1=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button5);
        gpsaddress=findViewById(R.id.readaddress);
        netChangeRequest=findViewById(R.id.changeNetRequest);
        bottommenu=findViewById(R.id.bottommenu);
        slidingMenu=findViewById(R.id.slidingMenu);
        onPowerStart=findViewById(R.id.onPowerStart);//开机自启动 app
        activityexit=findViewById(R.id.activityexit);
        jpush=findViewById(R.id.jpush);
        moreActivity=findViewById(R.id.moreActivity);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        gpsaddress.setOnClickListener(this);
        netChangeRequest.setOnClickListener(this);
        bottommenu.setOnClickListener(this);
        slidingMenu.setOnClickListener(this);
        onPowerStart.setOnClickListener(this);
        activityexit.setOnClickListener(this);
        jpush.setOnClickListener(this);
        moreActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch(v.getId()){
            case R.id.button:
                intent=new Intent(this,LitePalTestActivity.class);
                break;
            case R.id.button2:
                intent=new Intent(this,GreenDaoActivity.class);
                break;
            case R.id.button3:
                intent=new Intent(this,ScanCodeActivity.class);
                break;
            case R.id.button5:
                intent=new Intent(this,AlipayIntegrationActivity.class);
                break;
            case R.id.readaddress:
                intent=new Intent(this,GPSAddressActivity.class);
                break;
            case R.id.changeNetRequest:
                intent=new Intent(this,NetChangeActivity.class);
                break;
            case R.id.bottommenu:
                intent=new Intent(this,BottomMenuListActivity.class);
                break;
            case R.id.slidingMenu:
                intent=new Intent(this,SlinMenuActivity.class);
                break;
            case R.id.onPowerStart:
                //成功 关键是 注册静态广播 来监听 开机事件  获取到开机事件  跳转到 启动页面
                //5.0开机么页面
                intent=new Intent(this,SlinMenuActivity.class);
                break;
            case R.id.activityexit:
                //成功 关键是 注册静态广播 来监听 开机事件  获取到开机事件  跳转到 启动页面
                //5.0开机么页面
                intent=new Intent(this,exitActivitys.class);
                break;
            case R.id.jpush:
                ToastUtils.showShort(this,"点击收到的极光推送信息后会自动跳转");
                //不需要添加intent=new Intent(this,exitActivitys.class);
            case R.id.moreActivity:
                intent=new Intent(this,MoreActivity.class);
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

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground=false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //解除本地注册的广播 ----
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
        if (null != msg) {
            ToastUtils.showShort(this,"接受到的推送为："+msg);
        }
    }


}
