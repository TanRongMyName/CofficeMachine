package com.coffice.shengtao.cofficemachine.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.coffice.shengtao.cofficemachine.fragments.BaseFragment;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment5;

import java.util.ArrayList;

/**
 * 可能使用不到了   AHbottomnavigation 中的 veiwpage 适配器
 */
public class ViewPagerAdatper extends FragmentPagerAdapter {
    private ArrayList<MenuFragment5> fragments = new ArrayList<>();
    private MenuFragment5 currentFragment;

    public ViewPagerAdatper(FragmentManager fm,ArrayList<MenuFragment5> data) {
        super(fm);
        this.fragments=data;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if(getCurrentFragment()!=object){
            currentFragment= (MenuFragment5) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    public MenuFragment5 getCurrentFragment() {
        return  currentFragment;
    }
}
