package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;


import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.data.model.BarTab;
import com.coffice.shengtao.cofficemachine.data.model.BaseBottomBar;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment4;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTabHostActivity extends BaseActivity {

    @BindView(R.id.fl_test)
    FrameLayout flTest;
    @BindView(R.id.fth_test)
    FragmentTabHost fthTest;

    public List<BarTab> list;

    BaseBottomBar baseBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab_host);
        ButterKnife.bind(this);
        initData();


    }

    @Override
    public void initData() {
        super.initData();
        list=new ArrayList<>();
        list.add(new BarTab("爱好",R.drawable.like_fill,R.drawable.like,MenuFragment1.class));
        list.add(new BarTab("首页",R.drawable.home_fill,R.drawable.home,MenuFragment2.class));
        list.add(new BarTab("联系人",R.drawable.person_fill,R.drawable.person,MenuFragment3.class));
        list.add(new BarTab("位置",R.drawable.location_fill,R.drawable.location,MenuFragment4.class));

        baseBottomBar= new BaseBottomBar<BarTab>(this,fthTest,R.id.fl_test,R.layout.item_bar,list) {
            @Override
            protected TextView getBarText(View view) {
                LogUtils.d("view.findViewById(R.id.iv_bar)"+view.findViewById(R.id.tv_bar).getClass().toString());
                return view.findViewById(R.id.tv_bar);
            }

            @Override
            protected ImageView getBarImage(View view) {
                LogUtils.d("view.findViewById(R.id.tv_bar)"+view.findViewById(R.id.iv_bar).getClass().toString());
                return  view.findViewById(R.id.iv_bar);
            }
        };
        baseBottomBar.create();
        //baseBottomBar.setBottomBarListener(new)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
