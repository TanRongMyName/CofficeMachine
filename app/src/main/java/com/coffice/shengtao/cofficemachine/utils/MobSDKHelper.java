package com.coffice.shengtao.cofficemachine.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MobSDKHelper {
    public SendListener sendListener;
    public SubmitListener submitListener;
    public EventHandler eventHandler= new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.d("phoneNum","获取到验证码"+data.toString());
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            sendListener.success(data.toString());
                        } else {
                            Log.d("phoneNum","获取验证码失败");
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            sendListener.failed();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            Log.d("phoneNum","验证码通过"+data.toString());
                            submitListener.success(data.toString());
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            submitListener.failed();
                            Log.d("phoneNum","验证码验证失败"+data.toString());
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };
    //单例模式 枷锁
    //
    public volatile static MobSDKHelper mobSDKHelper;
    private MobSDKHelper(){
        SMSSDK.registerEventHandler(eventHandler);
    }
    public static MobSDKHelper getInstance(){
        if (mobSDKHelper==null){//第一检查    双重检查枷锁,只有在第一次实例化,才启动同步机制,提高性能
            synchronized (MobSDKHelper.class){
                if (mobSDKHelper==null){//第二次检查
                    mobSDKHelper=new MobSDKHelper();
                }
            }
        }
        return mobSDKHelper;
    }



    public void sendCode(String countryCode,String phoneNumber ,SendListener sendListener ){
        this.sendListener=sendListener;
        SMSSDK.getVerificationCode(countryCode, phoneNumber);
    }
    public void submitCode(String countryCode, String phoneNubmer,String judcode,SubmitListener submitListener ){
        this.submitListener=submitListener;
        SMSSDK.submitVerificationCode(countryCode, phoneNubmer, judcode);
    }
    public void unRegistSMSS(){
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    public abstract static class SendListener{
       public abstract void success(String phone);
       public abstract void failed();
    }
    public abstract static class SubmitListener{
        public abstract void success(String phone);
        public abstract void failed();
    }
}
