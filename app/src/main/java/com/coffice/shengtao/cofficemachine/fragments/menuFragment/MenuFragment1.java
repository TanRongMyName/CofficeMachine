package com.coffice.shengtao.cofficemachine.fragments.menuFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.fragments.BaseFragment;



/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment1 extends BaseFragment {

    TextView context;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_menu_fragment1, container, false);
            initView();
            isPrepared = true;
//        实现懒加载
            lazyLoad();
        }
        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }


        return mView;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        context=mView.findViewById(R.id.context);
        if (getArguments() != null) {
            context.setText(getArguments().getString("agrs1"));
        }
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }

    public static MenuFragment1 newInstance(String param1) {
        MenuFragment1 fragment = new MenuFragment1();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null) {
            unbinder.unbind();
        }
    }
}
