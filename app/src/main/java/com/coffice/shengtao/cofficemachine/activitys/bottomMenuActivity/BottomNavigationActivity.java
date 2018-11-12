package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment2;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment3;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomNavigationActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.title)
    TextView title;
    private BottomNavigationBar bottomNavigationBar;
    private MenuFragment1 msgFragment;
    private MenuFragment2 contactsFragment;
    private MenuFragment3 dongtaiFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    int lastSelectedPosition = 0;
    public String titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        titles= (String) getIntent().getExtras().get("MenuWay");
        ButterKnife.bind(this);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        init();
    }

    private void init() {
        //要先设计模式后再添加图标！
        //设置按钮模式  MODE_FIXED表示固定   MODE_SHIFTING表示转移
        //MODE_DEFAULT  MODE_CLASSIC  MODE_SHIFTING
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_CLASSIC);
        //设置背景风格
        // BACKGROUND_STYLE_STATIC表示静态的
        //BACKGROUND_STYLE_RIPPLE表示涟漪的，也就是可以变化的 ，跟随setActiveColor里面的颜色变化
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //添加并设置图标、图标的颜色和文字
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "主页")).setActiveColor(R.color.blue)
                .addItem(new BottomNavigationItem(R.drawable.person, "联系人")).setActiveColor(R.color.red)
                .addItem(new BottomNavigationItem(R.drawable.like, "爱好")).setActiveColor(R.color.orign)
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
        title.setText(titles);
    }

    //设置初始界面
    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layFrame, MenuFragment1.newInstance("主页"));
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (msgFragment == null) {
                    msgFragment = MenuFragment1.newInstance("主页");
                }
                transaction.replace(R.id.layFrame, msgFragment);
                break;
            case 1:
                if (contactsFragment == null) {
                    contactsFragment = MenuFragment2.newInstance("联系人");
                }
                transaction.replace(R.id.layFrame, contactsFragment);
                break;
            case 2:
                if (dongtaiFragment == null) {
                    dongtaiFragment = MenuFragment3.newInstance("爱好");
                }
                transaction.replace(R.id.layFrame, dongtaiFragment);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();

    }

    @Override
    public void onTabUnselected(int position) {
        LogUtils.d("dongtaiFragment", "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }
}
