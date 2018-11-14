package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.fragments.BaseFragment;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment4;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//https://github.com/armcha/LuseenBottomNavigation
public class LuseenBottomNavigationActivity extends BaseActivity {

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    //    @BindView(R.id.textView)
//    TextView textView;
//    @BindView(R.id.button)
//    Button button;
    private int[] color;
    private int[] image;
    private List<BaseFragment> fragmentList;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luseen_bottom_navigation);
        binder = ButterKnife.bind(this);
        initData();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void initData() {
        super.initData();
        color = getApplicationContext().getResources().getIntArray(R.array.fragment_colors);
        image = new int[]{R.drawable.ic_mic_black_24dp, R.drawable.ic_favorite_black_24dp,
                R.drawable.ic_book_black_24dp, R.drawable.github_circle};
        if (bottomNavigation != null) {
            //bottomNavigation.isWithText(false);
            bottomNavigation.isWithText(true);
            //可以竖着 将菜单栏放在左边
            //bottomNavigation.activateTabletMode();  竖着放没有添加  切换 fragment 事件  同时也没有文字

            bottomNavigation.isColoredBackground(true);
            bottomNavigation.setTextActiveSize(getResources().getDimension(R.dimen.text_active));
            bottomNavigation.setTextInactiveSize(getResources().getDimension(R.dimen.text_inactive));
            bottomNavigation.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.firstColor));
            //bottomNavigation.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "font/black.ttf"));
            bottomNavigation.setFont(getResources().getFont(R.font.black));
        }

//        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
//                ("Record", color[0], image[0]);
//        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
//                ("Like", color[1], image[1]);
//        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
//                ("Books", color[2], image[2]);
//        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
//                ("GitHub", color[3], image[3]);
//
//        bottomNavigation.addTab(bottomNavigationItem);
//        bottomNavigation.addTab(bottomNavigationItem1);
//        bottomNavigation.addTab(bottomNavigationItem2);
//        bottomNavigation.addTab(bottomNavigationItem3);

        fragmentList=new ArrayList<>();
        fragmentList.add(MenuFragment1.newInstance("Record"));
        fragmentList.add(MenuFragment2.newInstance("Like"));
        fragmentList.add(MenuFragment3.newInstance("Books"));
        fragmentList.add(MenuFragment4.newInstance("GitHub"));

        //添加viewpager
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                LogUtils.d(fragmentList.get(position).fragmentargs);
                return fragmentList.get(position).fragmentargs;
            }
        });

        bottomNavigation.setUpWithViewPager(viewpager , color , image);
        bottomNavigation.disableViewPagerSlide();
        bottomNavigation.willNotRecreate(true);
        //可以使用 ViewPager ---adapter   点解切换
        bottomNavigation.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                  viewpager.setCurrentItem(index);
//                switch (index) {
//                    case 0:
////                        textView.setText("Record");
//                        break;
//                    case 1:
////                        textView.setText("Like");
//                        break;
//                    case 2:
////                        textView.setText("Books");
//                        break;
//                    case 3:
////                        textView.setText("GitHub");
//                        break;
//                }
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomNavigation.selectTab(2);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}
