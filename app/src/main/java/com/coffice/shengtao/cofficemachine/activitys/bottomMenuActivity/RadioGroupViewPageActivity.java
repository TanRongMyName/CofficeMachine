package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment4;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RadioGroupViewPageActivity extends BaseActivity {

    @BindView(R.id.main_vp)
    ViewPager mainVp;
    @BindView(R.id.main_chunvzuo)
    RadioButton mainChunvzuo;
    @BindView(R.id.main_mojiezuo)
    RadioButton mainMojiezuo;
    @BindView(R.id.main_shuangzizuo)
    RadioButton mainShuangzizuo;
    @BindView(R.id.main_tianpingzuo)
    RadioButton mainTianpingzuo;
    @BindView(R.id.main_rg)
    RadioGroup mainRg;
    private Unbinder bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group_view_page);
        bind=ButterKnife.bind(this);
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
                        fragment =  MenuFragment1.newInstance("处女座");
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

    private void changeImageSize() {
        //定义底部标签图片大小
        Drawable drawableFirst = getResources().getDrawable(R.drawable.bg_drawer_navigation_four);
        drawableFirst.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainChunvzuo.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSearch = getResources().getDrawable(R.drawable.bg_drawer_navigation_one);
        drawableSearch.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainMojiezuo.setCompoundDrawables(null, drawableSearch, null, null);//只放上面

        Drawable drawableMe = getResources().getDrawable(R.drawable.bg_drawer_navigation_three);
        drawableMe.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainShuangzizuo.setCompoundDrawables(null, drawableMe, null, null);//只放上面

        Drawable drawable = getResources().getDrawable(R.drawable.bg_drawer_navigation_two);
        drawable.setBounds(0, 0, 100, 100);//第一0是距左右边距离，第二0是距上下边距离，第三100长度,第四宽度
        mainTianpingzuo.setCompoundDrawables(null, drawable, null, null);//只放上面
    }

    @OnClick({R.id.main_chunvzuo, R.id.main_mojiezuo, R.id.main_shuangzizuo, R.id.main_tianpingzuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_chunvzuo:
                mainChunvzuo.setChecked(true);
                break;
            case R.id.main_mojiezuo:
                mainMojiezuo.setChecked(true);
                break;
            case R.id.main_shuangzizuo:
                mainShuangzizuo.setChecked(true);
                break;
            case R.id.main_tianpingzuo:
                mainTianpingzuo.setChecked(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
