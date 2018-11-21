package com.coffice.shengtao.cofficemachine.activitys;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.CountDownTimerHelper;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.MobSDKHelper;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 使用 mob 实现短信认证  同时 封装了下 使用类
 */
public class SMS_AuthenticationActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edPhone)
    EditText edPhone;
    @BindView(R.id.edcode)
    EditText edcode;
    @BindView(R.id.btCode)
    Button btCode;
    @BindView(R.id.line_code)
    LinearLayout lineCode;
    @BindView(R.id.btJudy)
    Button btJudy;


    //倒计时空间
    private CountDownTimerHelper countDownTimer;

    private MobSDKHelper.SendListener sendListener;
    private MobSDKHelper.SubmitListener submitListener;
    //电话号码  验证码
    private String phoneNumber,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__authentication);
        binder = ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        countDownTimer=new CountDownTimerHelper(this,btCode,1000,60000);
        sendListener=new MobSDKHelper.SendListener() {
            @Override
            public void success(String phone) {
                 ToastUtils.showShort(SMS_AuthenticationActivity.this,"获取验证码成功");
            }

            @Override
            public void failed() {
                ToastUtils.showShort(SMS_AuthenticationActivity.this,"获取验证码失败");
            }
        };
        submitListener=new MobSDKHelper.SubmitListener() {
                    @Override
                    public void success(String phone) {
                        countDownTimer.cancleTimer();
                        ToastUtils.showShort(SMS_AuthenticationActivity.this,"验证码验证成功");

                    }

                    @Override
                    public void failed() {
                        countDownTimer.cancleTimer();
                        ToastUtils.showShort(SMS_AuthenticationActivity.this,"验证码验证失败");
                    }
                };

    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.btCode, R.id.btJudy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btCode:
                //判断电话号码 是否异常
                if(JustPhoneAndCode(false)) {
                    //每点一次重新 创建？
                    countDownTimer=new CountDownTimerHelper(this,btCode,60000,1000);
                    countDownTimer.start();
                    btCode.setBackgroundColor(R.color.MSMChange);
                    btCode.setClickable(false);
                    MobSDKHelper.getInstance().sendCode("86", phoneNumber, sendListener);
                }
                break;
            case R.id.btJudy:
                //判断电话号码是否异常  收到的验证码是否为空
                if(JustPhoneAndCode(true)) {
                    MobSDKHelper.getInstance().submitCode("86", phoneNumber, code, submitListener);
                }
                break;
        }
    }

    /**
     * 判断电话号码是否正确
     * 判断验证码是否正确
     * @param istrue  需要 验证输入码
     * @return
     */
    private boolean JustPhoneAndCode(boolean  istrue){
        phoneNumber=edPhone.getText().toString();
        code=edcode.getText().toString();
        if(TextUtils.isEmpty(phoneNumber)){
            ToastUtils.showShort(SMS_AuthenticationActivity.this,"请输入手机号码");
            return false;
        }else {
            if(isPhone(phoneNumber)){
                if(istrue){
                    if(TextUtils.isEmpty(code)){
                        ToastUtils.showShort(SMS_AuthenticationActivity.this,"验证码不能为空，请输入验证码");
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }else{
                ToastUtils.showShort(SMS_AuthenticationActivity.this,"手机号码有误，请重新输入手机号码");
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if(countDownTimer!=null){
            countDownTimer.cancleTimer();
            countDownTimer=null;
        }
        binder.unbind();
        MobSDKHelper.getInstance().unRegistSMSS();
        super.onDestroy();
    }
    /**
     * 最全的 电话号码 验证
     * @param phone
     * @return
     */
    public  boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            ToastUtils.showShort(SMS_AuthenticationActivity.this,"手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            LogUtils.e(isMatch+"");
            if (!isMatch) {
                ToastUtils.showShort(SMS_AuthenticationActivity.this,"请填入正确的手机号");
            }
            return isMatch;
        }
    }



}
