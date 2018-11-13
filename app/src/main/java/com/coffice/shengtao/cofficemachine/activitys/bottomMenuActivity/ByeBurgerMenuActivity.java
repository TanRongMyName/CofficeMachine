package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment4;
import com.coffice.shengtao.cofficemachine.selfview.NoScrollViewPager;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ByeBurgerMenuActivity extends BaseActivity {
    private List<Fragment> fragList ;
    @BindView(R.id.noScrollViewPager)
    NoScrollViewPager noScrollViewPager;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye_burger_menu);
        binder = ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    public void initData() {
        super.initData();
        fragList = new ArrayList<>();
        fragList.add(MenuFragment1.newInstance("处女座"));
        fragList.add(MenuFragment2.newInstance("摩羯座"));
        fragList.add(MenuFragment3.newInstance("双子座"));
        fragList.add(MenuFragment4.newInstance("天秤座"));
    }

    @Override
    public void initView() {
        super.initView();
        //标题栏
        toolbar.setTitle("Title");
        setSupportActionBar(toolbar);

        //初始化 ViewPage
        noScrollViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragList.get(position);
            }

            @Override
            public int getCount() {
                return fragList.size();
            }
        });
        //底部菜单栏
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                LogUtils.d("item.getItemId()===="+item.getItemId());
                switch (item.getItemId()) {
                    case R.id.tab1:
                        noScrollViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab2:
                        noScrollViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab3:
                        noScrollViewPager.setCurrentItem(2);
                        break;
                    case R.id.tab4:
                        noScrollViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}
