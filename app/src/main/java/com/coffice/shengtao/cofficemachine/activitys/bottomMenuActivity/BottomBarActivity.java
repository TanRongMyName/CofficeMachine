package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.FrameLayout;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.selfview.BottomBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomBarActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        binder = ButterKnife.bind(this);

        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(MenuFragment1.class,
                        "首页",
                        R.drawable.home_fill,
                        R.drawable.home)
                .addItem(MenuFragment2.class,
                        "订单",
                        R.drawable.like_fill,
                        R.drawable.like)
                .addItem(MenuFragment3.class,
                        "我的",
                        R.drawable.location_fill,
                        R.drawable.location)
                .build();
    }


}
