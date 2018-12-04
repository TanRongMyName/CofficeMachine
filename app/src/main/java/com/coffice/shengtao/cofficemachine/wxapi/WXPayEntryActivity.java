package com.coffice.shengtao.cofficemachine.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;
/*微信付款 回调*/
/*
WX_Pay pay = new WX_Pay(getContext());
pay.pay(str1,str2,str3);
*/
//public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
//    private IWXAPI api;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        /*setContentView(R.layout.activity_wxpay_entry);*///1.第一个修改的地方 我删掉了,它自带的 布局, 当然如果你想保留,完全OK.因为布局太难看所以我干掉他了
//        api = WXAPIFactory.createWXAPI(this, "wxAPPID");//这里填入自己的微信APPID
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//        Log.d("coyc", "onPayFinish, errCode = " + baseResp.errCode);
//
//        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            int errCord = baseResp.errCode;
//            if (errCord == 0) {
//                ToastUtils.showShort(WXPayEntryActivity.this,"支付成功");
//            } else if(errCord==-1){
//                ToastUtils.showShort(WXPayEntryActivity.this,"支付失败");
//            }else if(errCord==-2){
//                ToastUtils.showShort(WXPayEntryActivity.this,"取消支付");
//            }
//            //这里接收到了返回的状态码可以进行相应的操作，如果不想在这个页面操作可以把状态码存在本地然后finish掉这个页面，这样就回到了你调起支付的那个页面
//            //获取到你刚刚存到本地的状态码进行相应的操作就可以了
//            finish();
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }

//}
