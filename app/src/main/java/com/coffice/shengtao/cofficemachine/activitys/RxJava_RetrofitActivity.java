package com.coffice.shengtao.cofficemachine.activitys;


import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.data.model.Alipay_Trade_Pay_Return;
import com.coffice.shengtao.cofficemachine.data.model.Alipay_Trade_Precreate_Return;
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
 */
public class RxJava_RetrofitActivity extends BaseActivity {

    Request_Interface requestserver  ;
    Alipay_Trade_Precreate_Return myAlipay;
    Alipay_Trade_Pay_Return myAlipay_pay;
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

    private Handler handler=new Handler();//用来延时发送线程
    private Runnable delayRun=new Runnable() {
        @Override
        public void run() {
            //执行程序-----
            String code = coderesult.getText().toString();
            if (code != null && code.length() > 15) { //16--24  18-24
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
                                LogUtils.d("myAlipay_pay===="+myAlipay_pay);
                                LogUtils.d("myAlipay_pay.toString()==="+myAlipay_pay.toString());

                                if (myAlipay_pay != null) {
                                    ToastUtils.showShort(RxJava_RetrofitActivity.this, "支付结果：" + myAlipay_pay.getAlipay_trade_pay_response().getMsg());
                                }
                            }

                        });
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




                /*coderesult.setFocusable(false);
                coderesult.setCursorVisible(false);*/
//                String code=coderesult.getText().toString();
//                LogUtils.d("code==="+code+"  code.lenght==="+code.length());//16-18位扩充到16-24位
//                ToastUtils.showShort(RxJava_RetrofitActivity.this,"获取到的二维码是："+code);
               /* Observable<Alipay_Trade_Precreate_Return> observable = requestserver.getPrecreateResult("2",code);
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
                                LogUtils.d("获取网络结果----"+myAlipay.getAlipay_trade_precreate_response().getMsg());
                                qrCodeEncoder.createQrCode2ImageView(myAlipay.getAlipay_trade_precreate_response().getQr_code(),qrcode);
                            }

                        });*/
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
                                LogUtils.d("获取网络结果----"+myAlipay.getAlipay_trade_precreate_response().getMsg());
                                qrCodeEncoder.createQrCode2ImageView(myAlipay.getAlipay_trade_precreate_response().getQr_code(),qrcode);
                            }

                        });
                break;
            case R.id.qrcode_wechat:
                //diffient url ---need ---diffent retrofit
                break;
            case R.id.scancode:
                coderesult.setEnabled(true);
                coderesult.setFocusable(true);
                coderesult.setCursorVisible(true);
                coderesult.setVisibility(View.VISIBLE);
                coderesult.setFocusableInTouchMode(true);
                coderesult.requestFocus();
                coderesult.setText(null);
                //获取到扫描吗后 ----不可 点击  -----可见 ----进行炒作
                break;
        }
    }
    //生成二维码
    // qrCodeEncoder = new QRCodeEncoder(this);
    //qrCodeEncoder.createQrCode2ImageView(textContent, ivQRCode);
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
