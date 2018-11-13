package com.coffice.shengtao.cofficemachine.data.model;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBottomBar<T extends BarTab> implements TabHost.OnTabChangeListener {
    private Activity mActivity;
    private FragmentTabHost mTabHost;
    private int mContainerId;
    private int mViewId;
    private List<T> mItems;

    private int mNormalColor = -1;
    private int mSelectColor = -1;

    private BottomBarListener bottomBarListener;
    public BaseBottomBar(Activity activity, FragmentTabHost tabHost,
                         int containerId, int viewid, List<T> items) {
        mActivity = activity;
        mTabHost = tabHost;
        mContainerId = containerId;
        mViewId = viewid;
        mItems = items == null ? new ArrayList<T>() : items;
        mNormalColor = setNormalColor();
        mSelectColor = setSelectColor();
    }

    protected int setSelectColor() {
        return Color.GREEN;
    }

    protected int setNormalColor() {
        return Color.BLACK;
    }
    //重新设置
    private void reset(T item) {
        getImage(item).setImageResource((Integer) item.getImageNormal());
        getText(item).setText(item.getTitle());
        getText(item).setTextColor(mNormalColor);
    }

    private void select(T item) {
        //图片切换
        getImage(item).setImageResource((Integer) item.getImageSelect());
        getText(item).setText(item.getTitle());
        getText(item).setTextColor(mSelectColor);
    }

    private ImageView getImage(T item) {
        return item.getItemView().getImage();
    }

    private TextView getText(T item) {
        return item.getItemView().getText();
    }

    public BaseBottomBar setBottomBarListener(BottomBarListener bottomBarListener) {
        this.bottomBarListener = bottomBarListener;
        return this;
    }

    public void setCurrentTab(int i) {
        mTabHost.setCurrentTab(i);
    }

    public List<?> getAllItem() {
        return mItems;
    }

    public View getView(int i) {
        T item = (T) mItems.get(i);
        return item.getView();
    }

    public BarTab.ItemView getItemView(int i) {
        T item = (T) mItems.get(i);
        return item.getItemView();
    }
    public void setItems(List<T> items) {
        mItems = items;
        mTabHost.removeAllViews();
        create();
    }

    public void create() {
        if (mTabHost == null) {
            throw new NullPointerException("TabHost is null!");
        }
        mTabHost.setup(mActivity, ((FragmentActivity) mActivity).getSupportFragmentManager(),
                mContainerId);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mItems.size(); i++) {
            T item = (T) mItems.get(i);
            //设置每个Item的view和内部布局
            View view = LayoutInflater.from(mActivity).inflate(mViewId, null, false);
            BarTab.ItemView itemView = new BarTab.ItemView();
            itemView.setImage(getBarImage(view));
            itemView.setText(getBarText(view));
            item.setView(view);
            item.setItemView(itemView);
            //未设置选中图片，默认为正常
            if (item.getImageSelect() == null) {
                item.setImageSelect(item.getImageNormal());
            }
            //设置默认图片
            if (i == 0) {
                select(item);
            } else {
                reset(item);
            }
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(item.getTitle()).setIndicator(view);
            mTabHost.addTab(tabSpec, item.getCls(), item.getBundle());
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        TabWidget widget = mTabHost.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            T item = (T) mItems.get(i);

            if (i == mTabHost.getCurrentTab()) {
                select(item);
                if (bottomBarListener != null) {
                    bottomBarListener.onClick(i, tabId);//重复点击  同样调用了 方法
                    LogUtils.d("tabId==="+tabId+"  i=="+i);
                }
            } else {
                reset(item);
            }
        }
    }

    public interface BottomBarListener {
        void onClick(int i, String title);
    }

    protected abstract TextView getBarText(View view);

    protected abstract ImageView getBarImage(View view);
}
