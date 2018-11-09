package com.coffice.shengtao.cofficemachine.activitys;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.NetWorkUtil;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

/**
 * 切换网络来请求 数据
 */
public class NetChangeActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_net_switch,btn_change;
    boolean hasRequestPermission=false;
    private WebView webview;
    private String[] cameraPermissions = {
            Manifest.permission.CHANGE_NETWORK_STATE
//            Manifest.permission.WRITE_SETTINGS
    };
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
              // String str=NetWorkUtil.get("https://www.baidu.com");
               String str=NetWorkUtil.get("https://blog.csdn.net/maqianli23/article/details/53556467");
               //https://blog.csdn.net/maqianli23/article/details/53556467
               webview.loadData(str,"text/html; charset=UTF-8", null);
         }
    }

    @Override
    public void initView() {
        super.initView();
        btn_net_switch=findViewById(R.id.btn_net_swhitch);
        btn_change=findViewById(R.id.btn_change);
        webview=findViewById(R.id.webview);
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
        webview.clearCache(true);
        webview.clearFormData();
    }


    @Override
    public void initEvent() {
        super.initEvent();
        btn_net_switch.setOnClickListener(this);
        btn_change.setOnClickListener(this);
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
                ToastUtils.showShort(NetChangeActivity.this,"没有获取到权限 闪退");
                LogUtils.d("没有获取到权限 闪退");
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }
}
