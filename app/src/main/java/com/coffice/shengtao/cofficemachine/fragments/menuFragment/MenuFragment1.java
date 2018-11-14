package com.coffice.shengtao.cofficemachine.fragments.menuFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.adapter.BaseRecyclerHoder;
import com.coffice.shengtao.cofficemachine.adapter.RecycleDataBaseAdapter;
import com.coffice.shengtao.cofficemachine.fragments.BaseFragment;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment1 extends BaseFragment {

    List<String> arrsources;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            // 需要inflate一个布局文件 填充Fragment
            mView = inflater.inflate(R.layout.fragment_menu_fragment1, container, false);
            isPrepared = true;
            initView();
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
        if(getArguments()!=null) {
            title = (String) getArguments().get("agrs1");
        }
        LogUtils.d("MenuFragment1 title==="+title);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        arrsources=new ArrayList<>();
        for(int i=0;i<100;i++){
            arrsources.add(""+i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new RecycleDataBaseAdapter(getActivity(), arrsources, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(BaseRecyclerHoder holder, Object item, int position, boolean isScrolling) {
                holder.setText(android.R.id.text1,item.toString());
            }
        });

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
        fragmentargs=param1;
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
