package com.coffice.shengtao.cofficemachine.activitys;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.NetWorkUtil;

/**
 * 切换网络来请求 数据
 */
public class NetChangeActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_net_switch,btn_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_change);
        initView();
        initEvent();
        initData();
    }

    @Override
    public void onClick(View v) {
         if(v.getId()==R.id.btn_net_swhitch){
             if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_ETHNET) {
                 NetWorkUtil.getNetState(NetChangeActivity.this, NetWorkUtil.NET_CELLULAR);
             } else if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_CELLULAR) {
                 NetWorkUtil.getNetState(NetChangeActivity.this, NetWorkUtil.NET_ETHNET);
             }
             updateView();
         }else if(v.getId()==R.id.btn_change){
               NetWorkUtil.get("https://www.baidu.com");
         }
    }

    @Override
    public void initView() {
        super.initView();
        btn_net_switch=findViewById(R.id.btn_net_swhitch);
        btn_change=findViewById(R.id.btn_change);
    }

    @Override
    public void initData() {
        super.initData();
        NetWorkUtil.getNetState(NetChangeActivity.this, NetWorkUtil.NET_ETHNET);
        updateView();
    }
    //修改UI
    private void updateView() {
        if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_ETHNET) {
            btn_net_switch.setText("以太网");
        } else if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_CELLULAR) {
            btn_net_switch.setText("蜂窝网");
        }
    }


    @Override
    public void initEvent() {
        super.initEvent();
        btn_net_switch.setOnClickListener(this);
        btn_change.setOnClickListener(this);
    }
}
