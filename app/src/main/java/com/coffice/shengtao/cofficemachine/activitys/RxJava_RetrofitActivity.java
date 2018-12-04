package com.coffice.shengtao.cofficemachine.activitys;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.data.Constent;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.data.model.Alipay_Trade_Pay_Return;
import com.coffice.shengtao.cofficemachine.data.model.Alipay_Trade_Precreate_Return;
import com.coffice.shengtao.cofficemachine.data.model.WX_OrderResult;
import com.coffice.shengtao.cofficemachine.data.model.WX_PerOrderResult;
import com.coffice.shengtao.cofficemachine.data.model.WX_ScanOrderResult;
import com.coffice.shengtao.cofficemachine.httprequest.RetrofitServiceManger;
import com.coffice.shengtao.cofficemachine.interfacep.Request_Interface;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;
import com.qingmei2.library.encode.QRCodeEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Retrofit+Rxjava实现网络请求
 * 获取生成的二维码 ----
 *
 * 微信付款代码都是在 Android 端实现-----
 * 切换将微信的 付款码 放在 服务器端-----明天实现
 */
public class RxJava_RetrofitActivity extends BaseActivity {

    Request_Interface requestserver  ;
    Alipay_Trade_Precreate_Return myAlipay;
    Alipay_Trade_Pay_Return myAlipay_pay;

    WX_PerOrderResult wx_perOrderResult;//预付订单 返回结果
    WX_OrderResult wxOrderResult;//订单查询结果
    WX_ScanOrderResult wx_scanOrderResult;//扫码订单的结果
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
    /**
     * 用来判断扫码提交是哪个接口
     */
    int current_buttonClicked=0;
    private Handler handler=new Handler();//用来延时发送线程
    private Runnable delayRun=new Runnable() {
        @Override
        public void run() {
            final String code = coderesult.getText().toString();
            //先将查询关闭------
            handler2.removeCallbacks(runnable2);
            LogUtils.d("current_buttonClicked===="+current_buttonClicked);//16-18位扩充到16-24位
            if (code != null && code.length() > 15) { //16--24  18-24
                LogUtils.d("code===" + code + "  code.lenght===" + code.length());//16-18位扩充到16-24位
                if(current_buttonClicked==1) {
                    LogUtils.d("code===" + code + "  code.lenght===" + code.length());//16-18位扩充到16-24位
                    Observable<Alipay_Trade_Pay_Return> observable = requestserver.getPrecreateResult("1", code);
                    observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                            .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                            .subscribe(new Observer<Alipay_Trade_Pay_Return>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Alipay_Trade_Pay_Return alipay_trade_precreate_return) { //这里的book就是我们请求接口返回的实体类
                                    myAlipay_pay = alipay_trade_precreate_return;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace(); //请求过程中发生错误
                                }

                                @Override
                                public void onComplete() {//所有事件都完成，可以做些操作。。。
                                    LogUtils.d("myAlipay_pay====" + myAlipay_pay);
                                    LogUtils.d("myAlipay_pay.toString()===" + myAlipay_pay.toString());

                                    if (myAlipay_pay != null) {
                                        ToastUtils.showShort(RxJava_RetrofitActivity.this, "支付结果：" + myAlipay_pay.getAlipay_trade_pay_response().getMsg());
                                    }
                                }

                            });
                }else if(current_buttonClicked==2){//微信扫码支付
                    time = System.currentTimeMillis() + "";
                    Observable<WX_ScanOrderResult> observable = requestserver.getWXOrderPay(GlobalData.TYPE_PAY, code,time);
                    observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                            .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                            .subscribe(new Observer<WX_ScanOrderResult>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(WX_ScanOrderResult wx_scanOrderResult1) { //这里的book就是我们请求接口返回的实体类
                                    wx_scanOrderResult = wx_scanOrderResult1;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace(); //请求过程中发生错误
                                }

                                @Override
                                public void onComplete() {//所有事件都完成，可以做些操作。。。
                                    LogUtils.d("wxqrcode====="+wx_scanOrderResult.toString());//结果  然后 ---
                                    //查询结果----
                                    //还是需要去查询
                                    if(wx_scanOrderResult.getResult_code().equals(Constent.SUCCESS)){
                                        handler2.post(runnable2);//轮训的去查询 付款的结果----
                                    }else{
                                        ToastUtils.showShort(RxJava_RetrofitActivity.this,wx_scanOrderResult.getReturn_msg());
                                    }

                                }

                            });

                }
            }
        }
    };

    private QRCodeEncoder qrCodeEncoder;
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
         qrCodeEncoder = new QRCodeEncoder(this);
         requestserver= (Request_Interface) RetrofitServiceManger.getInstance(GlobalData.LocalHost).create(Request_Interface.class);
         coderesult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //coderesult.setText(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(delayRun!=null){
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                String code=s.toString();
                LogUtils.d("code==="+code+"  code.lenght==="+code.length());//16-18位扩充到16-24位
                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 800);
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
                current_buttonClicked=1;
                //reflite  create ---and intent load---- result-detail
                Observable<Alipay_Trade_Precreate_Return> observable = requestserver.getPrecreateResult();
                LogUtils.d("点击了 按钮---");
                observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                        .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                        .subscribe(new Observer<Alipay_Trade_Precreate_Return>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Alipay_Trade_Precreate_Return alipay_trade_precreate_return) { //这里的book就是我们请求接口返回的实体类
                                myAlipay=alipay_trade_precreate_return;
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace(); //请求过程中发生错误
                            }

                            @Override
                            public void onComplete() {//所有事件都完成，可以做些操作。。。
                                qrCodeEncoder.createQrCode2ImageView(myAlipay.getAlipay_trade_precreate_response().getQr_code(),qrcode);
                            }

                        });
                break;
            case R.id.qrcode_wechat:
                time = System.currentTimeMillis() + "";
                current_buttonClicked=2;
                Observable<WX_PerOrderResult> order_pay= requestserver.getWXOrderQRCODE(GlobalData.TYPE_PAY_QRCODE,time);
                order_pay.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                        .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                        .subscribe(new Observer<WX_PerOrderResult>() {
                                       @Override
                                       public void onSubscribe(Disposable d) {

                                       }

                                       @Override
                                       public void onNext(WX_PerOrderResult s) {
                                           wx_perOrderResult=s;
                                       }

                                       @Override
                                       public void onError(Throwable e) {

                                       }

                                       @Override
                                       public void onComplete() {
                                           LogUtils.d("获取网络结果 微信二维码支付----"+wx_perOrderResult.toString());
                                           if(wx_perOrderResult.getReturn_code().equals(Constent.SUCCESS)&&wx_perOrderResult.getResult_code().equals(Constent.SUCCESS)) {
                                             //返回码 为success 才可以获取到 URLcode
                                               qrCodeEncoder.createQrCode2ImageView(wx_perOrderResult.getCode_url(),qrcode);
                                               //去执行查询结果
                                               handler2.post(runnable2);//轮训的去查询 付款的结果----
                                           }else{
                                              ToastUtils.showShort(RxJava_RetrofitActivity.this,wx_perOrderResult.getReturn_msg());
                                           }

                                       }
                                   });
                break;
            case R.id.scancode:
                coderesult.setEnabled(true);
                coderesult.setFocusable(true);
                coderesult.setCursorVisible(true);
                coderesult.setVisibility(View.VISIBLE);
                coderesult.setFocusableInTouchMode(true);
                coderesult.requestFocus();
                coderesult.setText(null);
                break;
        }
    }

    ///////////////////////-----------微信支付-----------------------------------------------------------
    // 2 查询订单
    public String time;//用来标志  唯一订单号
    Handler handler2 = new Handler();
    //用来轮训去查询支付结果
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {//每次都要开一个线程去查询订单情况，直到有用户支付成功的结果。
                @Override
                public void run() {
                    Observable<WX_OrderResult> order_pay= requestserver.getWXOrderResult(GlobalData.TYPE_CHECK_ORDER,null,time);
                    order_pay.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                            .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                            .subscribe(new Observer<WX_OrderResult>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(WX_OrderResult wx_orderResult) {
                                    wxOrderResult=wx_orderResult;
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    LogUtils.d("获取网络结果 查询----"+wxOrderResult.toString());
                                    //更新UI
                                    if(wxOrderResult.getResult_code().equals(Constent.SUCCESS)&&wxOrderResult.getReturn_code().equals(Constent.SUCCESS)&&wxOrderResult.getTrade_state().equals(Constent.SUCCESS)){
                                        //提示订单提交成功
                                        ToastUtils.showShort(RxJava_RetrofitActivity.this,"支付成功");
                                        handler2.removeCallbacks(runnable2);
                                    }else if(!wxOrderResult.getResult_code().equals(Constent.SUCCESS)&&wxOrderResult.getReturn_code().equals(Constent.SUCCESS)){
                                        ToastUtils.showShort(RxJava_RetrofitActivity.this,wxOrderResult.getReturn_msg());
                                        handler2.removeCallbacks(runnable2);
                                    }
                                }
                            });
                }
            }).start();
            handler2.postDelayed(runnable2, 5000);
        }
    };
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
