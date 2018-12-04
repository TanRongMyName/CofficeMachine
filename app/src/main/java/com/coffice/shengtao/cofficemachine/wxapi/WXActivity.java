package com.coffice.shengtao.cofficemachine.wxapi;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.data.Constent;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;
import com.qingmei2.library.encode.QRCodeEncoder;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 微信扫码支付
 */
public class WXActivity extends BaseActivity {

    SortedMap<Object, Object> packageParams = null;
    String time = null;
    @BindView(R.id.editText)
    EditText editText;
    private QRCodeEncoder qrCodeEncoder;
    @BindView(R.id.wxgetcode)
    Button wxgetcode;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx);
        binder = ButterKnife.bind(this);
        qrCodeEncoder = new QRCodeEncoder(this);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }







/*  6.5.退款接口
    //5 如果验证失败，需申请退款
    public void refundMoney() throws Exception {
        SortedMap<String,Object>parameterMap=new TreeMap<String,Object>();
        parameterMap.put(Constent.APPID, Constent.VALUE_APPID);//公众号ID
        parameterMap.put(Constent.MCH_ID, Constent.VALUE_MCH_ID);//商户号
        parameterMap.put(Constent.OUT_TRADE_NO, getIntent().getStringExtra(Constent.VALUE_OUT_TRADE_NO));//商户订单号
        parameterMap.put(Constent.NONCE_STR, PayCommonUtil.getRandomString(32));//随机字符串
        parameterMap.put(Constent.SIGN_TYPE, "MD5");
        parameterMap.put(Constent.OUT_REFUND_NO, System.currentTimeMillis() + "");//商户退款单号
        parameterMap.put(Constent.TOTAL_FEE, "1");//订单金额
        parameterMap.put(Constent.REFUND_FEE, "1");//退款金额
        parameterMap.put(Constent.REFUND_FEE_TYPE, "CNY");//货币种类
        parameterMap.put(Constent.REFUND_DESC, "商品验证失败");//退款原因
        parameterMap.put(Constent.REFUND_ACCOUNT, "refund reason");//退款资金来源
        parameterMap.put(Constent.SIGN, PayCommonUtil.createSign("UTF-8", parameterMap));//签名
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);
        System.out.println("ds>>> requestXML3 = " + requestXML);
        String result = PayCommonUtil.httpsRequest(ValidateIdcardActivity.this, requestXML);
        System.out.println("ds>>> result3 = " + result);
        Map<String, String> map = null;
        try {
            map = PayCommonUtil.xmlToMap(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SortedMap<Object, Object> packageParams = PayCommonUtil.getSortedMap(map);
        String key = Constent.VALUE_API_KEY;
        String result_code = (String) packageParams.get(Constent.RESULT_CODE);
        String mch_id = (String) packageParams.get(Constent.MCH_ID);//商户号
        String total_fee = (String) packageParams.get(Constent.TOTAL_FEE);//总金额
        String trade_state = (String) packageParams.get(Constent.TRADE_STATE);//交易状态
        System.out.println("ds>>>   trade_state:" + trade_state);
        System.out.println("ds>>>   mch_id:" + mch_id);
        System.out.println("ds>>>   total_fee:" + total_fee);
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
            handleResult(packageParams, result_code, mch_id, total_fee, trade_state);
        } else {
            System.out.println("通知签名验证失败");
        }
    }

    private void handleResult(final SortedMap<Object, Object> packageParams, final String result_code, final String mch_id, final String total_fee, final String trade_state) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (Constent.SUCCESS.equals(result_code)) {
                    tv_validating.setText(getString(R.string.txt_validate_timeout));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                } else {
                    System.out.println("退款失败,错误信息：" + packageParams.get("err_code"));
                }
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                refundMoney();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

        new Thread(runnable).start();*/


}
