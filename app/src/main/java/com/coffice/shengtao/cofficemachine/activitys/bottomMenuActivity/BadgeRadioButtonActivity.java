package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment4;
import com.coffice.shengtao.cofficemachine.selfview.BadgeRadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BadgeRadioButtonActivity extends AppCompatActivity {
    @BindView(R.id.main_vp)
    ViewPager mainVp;
    @BindView(R.id.main_chunvzuo)
    BadgeRadioButton mainChunvzuo;
    @BindView(R.id.main_mojiezuo)
    BadgeRadioButton mainMojiezuo;
    @BindView(R.id.main_shuangzizuo)
    BadgeRadioButton mainShuangzizuo;
    @BindView(R.id.main_tianpingzuo)
    BadgeRadioButton mainTianpingzuo;
    @BindView(R.id.main_rg)
    RadioGroup mainRg;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_radio_button);
        bind = ButterKnife.bind(this);
        changeImageSize();
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_chunvzuo:
                        //点击第一个radiobutton,显示viewpager的第一页
                        mainVp.setCurrentItem(0, false);
                        break;
                    case R.id.main_mojiezuo:
                        //点击第二个radiobutton,显示viewpager的第二页
                        mainVp.setCurrentItem(1, false);
                        break;
                    case R.id.main_shuangzizuo:
                        mainVp.setCurrentItem(2, false);
                        break;
                    case R.id.main_tianpingzuo:
                        mainVp.setCurrentItem(3, false);
                        break;
                }
            }
        });

        mainVp.setAdapter(new FragmentPagerAdapter(this.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        //当滑动到第一页时候,展示这个fragment
                        fragment = MenuFragment1.newInstance("处女座");
                        break;
                    case 1:
                        //当滑动到第二页时候,展示这个fragment
                        fragment = MenuFragment2.newInstance("摩羯座");
                        break;
                    case 2:
                        //当滑动到第二页时候,展示这个fragment
                        fragment = MenuFragment3.newInstance("双子座");
                        break;
                    case 3:
                        //当滑动到第二页时候,展示这个fragment
                        fragment = MenuFragment4.newInstance("天秤座");
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                //返回viewpager的数量
                return 4;
            }
        });
        //viewpager滑动监听
        mainVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //radiogroup选中对应的radiobutton
                mainRg.check(mainRg.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    private void changeImageSize() {

//        mBrbMain0.setBadgeNumber(1);
//        mBrbMain1.setBadgeNumber(10).setBadgeColorBackground(Color.GREEN);
//        mBrbMain2.setBadgeNumber(100).setBadgeOffX(10).setBadgeColorBadgeText(Color.BLUE);
//        mBrbMain3.setBadgeNumber(0).setBadgeOffX(-10).setBadgeOffY(10);
//        mBrbMain4.setBadgeText("new");
//        mBrbMain5.setBadgeText("免费");
//        mBrbMain6.setBadgeText("free").setBadgeGravity(Gravity.LEFT);
//        mBrbMain7.setBadgeText("free").setBadgeGravity(Gravity.RIGHT);

        //定义底部标签图片大小
        Drawable drawableFirst = getResources().getDrawable(R.drawable.bg_drawer_navigation_four_clicked);
        drawableFirst.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainChunvzuo.setCompoundDrawables(null, drawableFirst, null, null);//只放上面
        mainChunvzuo.setBadgeNumber(10).setBadgeColorBackground(Color.GREEN);

        Drawable drawableSearch = getResources().getDrawable(R.drawable.bg_drawer_navigation_one_clicked);
        drawableSearch.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainMojiezuo.setCompoundDrawables(null, drawableSearch, null, null);//只放上面
        mainMojiezuo.setBadgeNumber(9).setBadgeColorBackground(Color.GREEN);

        Drawable drawableMe = getResources().getDrawable(R.drawable.bg_drawer_navigation_three_clicked);
        drawableMe.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainShuangzizuo.setCompoundDrawables(null, drawableMe, null, null);//只放上面
        mainShuangzizuo.setBadgeNumber(8).setBadgeColorBackground(Color.GREEN);

        Drawable drawable = getResources().getDrawable(R.drawable.bg_drawer_navigation_two_clicked);
        drawable.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainTianpingzuo.setCompoundDrawables(null, drawable, null, null);//只放上面
        mainTianpingzuo.setBadgeNumber(7).setBadgeColorBackground(Color.GREEN);

    }

    @OnClick({R.id.main_chunvzuo, R.id.main_mojiezuo, R.id.main_shuangzizuo, R.id.main_tianpingzuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_chunvzuo:
                mainChunvzuo.setChecked(true);
                mainChunvzuo.setBadgeNumber(-1);
                break;
            case R.id.main_mojiezuo:
                mainMojiezuo.setChecked(true);
                mainMojiezuo.setBadgeNumber(-1);
                break;
            case R.id.main_shuangzizuo:
                mainShuangzizuo.setChecked(true);
                mainShuangzizuo.setBadgeNumber(-1);
                break;
            case R.id.main_tianpingzuo:
                mainTianpingzuo.setChecked(true);
                mainTianpingzuo.setBadgeNumber(-1);
                break;
        }
    }
}
