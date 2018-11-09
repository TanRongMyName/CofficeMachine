package com.coffice.shengtao.cofficemachine.activitys;



import android.os.Bundle;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

public class BottomMenuShowActivity extends BaseActivity {
    public int MenuWay=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenuWay= (int) getIntent().getExtras().get("MenuWay");
        LogUtils.d("MenuWay==="+MenuWay);
        setContentView(R.layout.activity_bottom_menu_show);
        LogUtils.d("MenuWay==="+MenuWay);
    }
}
