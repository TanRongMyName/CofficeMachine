package com.coffice.shengtao.cofficemachine.activitys;

import android.os.Bundle;
import android.view.View;
import com.alipay.sdk.app.EnvUtils;
import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.data.GlobalData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TastPayTwoActivity extends BaseActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test_apay);
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }

        public void onpay(View view) {
            MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
            builder.setRsa2(GlobalData.Sand_RSA2);
            builder.setAppid(GlobalData.Sand_APayId);
            builder.setMoney("100.00");
            builder.setTitle("烤鸭");
            builder.setOrderTradeId(getOutTradeNo());//订单唯一号码
            builder.build().toALiPay(TastPayTwoActivity.this);
        }
    /**
     * 要求外部订单号必须唯一。
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
