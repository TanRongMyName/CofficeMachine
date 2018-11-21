package com.coffice.shengtao.cofficemachine.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;

/**
 * 定时器  定时递减的执行 任务
 */
public class CountDownTimerHelper extends CountDownTimer {
    private Context context;
    private Button button;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerHelper(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public CountDownTimerHelper(Context context, Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.button=button;
        this.context=context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
         //改变按钮上的内容
        //Log.v("millisUntilFinished",millisUntilFinished+"");
        if(context!=null) {
            this.button.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }else{
            cancleTimer();
        }
    }

    @Override
    public void onFinish() {
     //结束后  button 可以点击 了
        button.setClickable(true);
        button.setText("重新获取验证码");
    }
    @SuppressLint("ResourceAsColor")
    public void cancleTimer(){
        //取消定时
        this.cancel();
        this.button.setClickable(true);
        this.button.setBackgroundColor(R.color.MSMChangeBf);
        button.setText("获取验证码");
    }
}
