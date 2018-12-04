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
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int kind = msg.what;
            if (kind == 1) {
                String urlcode = (String) msg.obj;
                qrCodeEncoder.createQrCode2ImageView(urlcode, imageView);
            } else if (kind == 2) {

            }
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {//每次都要开一个线程去查询订单情况，直到有用户支付成功的结果。
                @Override
                public void run() {
                    checkOrder();
                }
            }).start();
            handler2.postDelayed(runnable2, 5000);
        }
    };
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
//        handler2.post(runnable2);//在main中调用
    }

    @OnClick(R.id.wxgetcode)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("WXActivity", "开始unifiedOrder 下预订单");
                String urlcode = unifiedOrder();
                Message message = handler2.obtainMessage();
                message.obj = urlcode;
                message.what = 1;
                handler2.sendMessage(message);
                handler2.post(runnable2);//轮训的去查询 付款的结果----
            }
        }).start();

    }

    public void onSubmit(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlcode = unifiedOrder2(editText.getText().toString());
                handler2.post(runnable2);//轮训的去查询 付款的结果----
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }


    //1 统一下单   -----去预支付 然后生成二维码 URL 返回
    public String unifiedOrder() {
        LogUtils.d("WXActivity", "开始unifiedOrder    1");
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("appid", Constent.VALUE_APPID);//公众号ID
        parameterMap.put("mch_id", Constent.VALUE_MCH_ID);//商户号
        //parameterMap.put("device_info", "654321");//设备号
        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));//随机字符串
        parameterMap.put("body", "腾讯充值中心-QQ会员充值");//商品描述
        parameterMap.put("sign_type", "MD5");//签名类型
        //parameterMap.put("detail", "");//商品详情
        parameterMap.put("attach", "支付测试");//附加数据
        time = System.currentTimeMillis() + "";
        parameterMap.put("out_trade_no", time);//商户订单号
        parameterMap.put("fee_type", "CNY");//标价币种
        parameterMap.put("total_fee", "1");//标价金额
        parameterMap.put("spbill_create_ip", "14.23.150.211");//终端IP
        parameterMap.put("time_start", System.currentTimeMillis() + "");//交易起始时间
        //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的，不能携带参数。
        parameterMap.put("notify_url", "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");//通知地址(支付结果通知)//----------
        parameterMap.put("trade_type", "NATIVE");//交易类型
        parameterMap.put("product_id", "123456");//商品ID(和设备ID一起需知，扫码支付时必须传）
        parameterMap.put("limit_pay", "no_credit");//指定支付方式
        LogUtils.d("WXActivity", "开始unifiedOrder    2");
        parameterMap.put("sign", PayCommonUtil.createSign("utf-8", parameterMap));//签名
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);//将请求组装成xml形式
        String result = PayCommonUtil.httpsRequest(
                Constent.URL_unifiedorder, Constent.POST,
                requestXML);//调用统一支付接口返回String类型字符串
        LogUtils.d("WXActivity", "开始unifiedOrder    3");
        Map<String, String> map = null;
        try {
            map = PayCommonUtil.xmlToMap(result);//将返回的结果转为map形式
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d("WXActivity", "开始unifiedOrder    4    " + map.get("code_url"));

        return map.get("code_url");//得到code_url用来生成二维码
    }

    //1 统一下单   获取用户的二维码code 提交---直接支付完事
    public String unifiedOrder2(String auth_code) {

        //没有 parameterMap.put("trade_type", "NATIVE");//交易类型
        //没有 parameterMap.put("product_id", "123456");//商品ID(和设备ID一起需知，扫码支付时必须传）
        //parameterMap.put("notify_url", "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");//通知地址(支付结果通知)//----------
        //多了一个 --授权码
        // 同时返回的参数结果不一样----

        LogUtils.d("WXActivity", "开始unifiedOrder    1");
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("appid", Constent.VALUE_APPID);//公众号ID
        parameterMap.put("mch_id", Constent.VALUE_MCH_ID);//商户号
        //parameterMap.put("device_info", "654321");//设备号
        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));//随机字符串
        parameterMap.put("body", "腾讯充值中心-QQ会员充值");//商品描述
        parameterMap.put("sign_type", "MD5");//签名类型
        //parameterMap.put("detail", "");//商品详情
        parameterMap.put("attach", "支付测试");//附加数据
        time = System.currentTimeMillis() + "";
        parameterMap.put("out_trade_no", time);//商户订单号
        parameterMap.put("fee_type", "CNY");//标价币种
        parameterMap.put("total_fee", "1");//标价金额
        parameterMap.put("spbill_create_ip", "14.23.150.211");//终端IP
        parameterMap.put("time_start", System.currentTimeMillis() + "");//交易起始时间
        //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的，不能携带参数。
        //parameterMap.put("notify_url", "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");//通知地址(支付结果通知)//----------
        //没有 parameterMap.put("trade_type", "NATIVE");//交易类型
        //没有 parameterMap.put("product_id", "123456");//商品ID(和设备ID一起需知，扫码支付时必须传）
        parameterMap.put("limit_pay", "no_credit");//指定支付方式
        parameterMap.put("auth_code", auth_code);
        LogUtils.d("WXActivity", "开始unifiedOrder    2");
        parameterMap.put("sign", PayCommonUtil.createSign("utf-8", parameterMap));//签名
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);//将请求组装成xml形式
        String result = PayCommonUtil.httpsRequest(
                Constent.URL_micropay, Constent.POST,
                requestXML);//调用统一支付接口返回String类型字符串
        LogUtils.d("WXActivity", "开始unifiedOrder    3   " + result);
        Map<String, String> map = null;
        try {
            map = PayCommonUtil.xmlToMap(result);//将返回的结果转为map形式
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d("WXActivity", "开始unifiedOrder    4    ");
        if(!map.get("return_msg").equals("OK")){
            LogUtils.d("WXActivity", "交易异常 需要 取消 订单");
        }else{
            LogUtils.d("WXActivity", "订单成功等待 付款");//查询订单---
        }

        return map.get("code_url");//得到code_url用来生成二维码
    }






/*  终端使用code_url生成二维码
    private void createQRCode() {
        //匿名内部类导致Activity内存泄漏的问题待解决
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                System.out.println("ds>>>  生成二维码成功");
                return QRCodeEncoder.syncEncodeQRCode(unifiedOrder(), BGAQRCodeUtil.dp2px(WechatPaymentActivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != ) {
                    iv.setImageBitmap(bitmap);
                } else {
                    System.out.println("ds>>>  生成二维码失败");
                }
            }
        }.execute();
    }*/


    // 2 查询订单
    public void checkOrder() {
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("appid", Constent.VALUE_APPID);//公众号ID
        parameterMap.put("mch_id", Constent.VALUE_MCH_ID);//商户号
        parameterMap.put("out_trade_no", time);//商户订单号
        parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));//随机字符串
        parameterMap.put("sign", PayCommonUtil.createSign("utf-8", parameterMap));//签名
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);//将请求组装成xml形式
        String result = PayCommonUtil.httpsRequest(
                Constent.URL_CHAXUN_DINGDAN, Constent.POST,
                requestXML);//调用查询订单接口返回String类型字符串
        Map<String, String> map = null;
        try {
            if (result != null)
                map = PayCommonUtil.xmlToMap(result);//将返回的结果转为map形式
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map != null)
            packageParams = PayCommonUtil.getSortedMap(map);//将map中空的过滤掉
        if (packageParams != null && PayCommonUtil.isTenpaySign("utf-8", packageParams, Constent.VALUE_API_KEY)) {
            handler.post(runnable);//在新的线程去匹配返回的信息，做相应的UI变化
        } else {
            System.out.println("通知签名验证失败");
        }
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                updateUI(packageParams);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private void updateUI(SortedMap<Object, Object> packageParams) throws InterruptedException {
        String result_code = (String) packageParams.get(Constent.RESULT_CODE);
        String return_code = (String) packageParams.get(Constent.RETURN_CODE);
        String trade_state = (String) packageParams.get(Constent.TRADE_STATE);
        String trade_state_desc = (String) packageParams.get(Constent.TRADE_STATE_DESC);
        String error_code = (String) packageParams.get(Constent.ERROR_CODE);
        System.out.println("ds>>>  result_code=" + result_code + ", return_code=" + return_code + ", trade_state=" + trade_state);
        System.out.println("ds>>>  trade_state_desc=" + trade_state_desc + ", error_code=" + error_code);
        if (result_code.equals(Constent.SUCCESS) && return_code.equals(Constent.SUCCESS) && trade_state.equals(Constent.SUCCESS)) {
            handler2.removeCallbacks(runnable2);
            //支付成功，做相关逻辑。
            ToastUtils.showShort(WXActivity.this, "付款成功");
        } else if (Constent.PAYERROR.equals(packageParams.get(Constent.TRADE_STATE))) {
            handler2.removeCallbacks(runnable2);
            ToastUtils.showShort(WXActivity.this, "付款失败");
            //支付失败，做相关逻辑。
        }
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
