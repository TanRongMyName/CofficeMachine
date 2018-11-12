package com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.BaseActivity;
import com.coffice.shengtao.cofficemachine.fragments.BaseFragment;
import com.coffice.shengtao.cofficemachine.fragments.menuFragment.MenuFragment1;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PopWindowFragmentActivity extends BaseActivity {

    @BindView(R.id.frame_content)
    FrameLayout frameContent;

    @BindView(R.id.image_at)
    ImageView imageAt;
    @BindView(R.id.layout_at)
    FrameLayout layoutAt;

    @BindView(R.id.image_auth)
    ImageView imageAuth;
    @BindView(R.id.layout_auth)
    FrameLayout layoutAuth;

    @BindView(R.id.image_space)
    ImageView imageSpace;
    @BindView(R.id.layout_space)
    FrameLayout layoutSpace;

    @BindView(R.id.image_more)
    ImageView imageMore;
    @BindView(R.id.layout_more)
    FrameLayout layoutMore;

    @BindView(R.id.frameMenu)
    FrameLayout frameMenu;
    @BindView(R.id.toggle_btn)
    ImageView toggleBtn;

    @BindView(R.id.plus_btn)
    ImageView plusBtn;


    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_window_fragment);
        ButterKnife.bind(this);
        layoutAt.performClick();
        //图片选择器的使用 ----如果是clicked_状态对应  setCheck
        //                     如果是select_状态对应 setSelect
    }

    @OnClick({R.id.layout_at, R.id.layout_auth, R.id.layout_space, R.id.layout_more, R.id.toggle_btn})
    public void onViewClicked(View view) {
        BaseFragment baseFragment=null;
        switch (view.getId()) {
            case R.id.layout_at:
                baseFragment=MenuFragment1.newInstance("处女座") ;
                clickButton(baseFragment, imageAt, (FrameLayout)view);
                break;
            case R.id.layout_auth:
                baseFragment=MenuFragment1.newInstance("摩羯座") ;
                clickButton(baseFragment, imageAuth, (FrameLayout)view);
                break;
            case R.id.layout_space:
                baseFragment=MenuFragment1.newInstance("双子座") ;
                clickButton(baseFragment, imageSpace, (FrameLayout) view);
                break;
            case R.id.layout_more:
                baseFragment=MenuFragment1.newInstance("天枰座") ;
                clickButton(baseFragment, imageMore, (FrameLayout)view);
                break;
            case R.id.toggle_btn:
                clickToggleBtn();
                break;
        }
    }

    public void clickButton(BaseFragment fragment, ImageView image,FrameLayout frameLayout){
        FragmentTransaction fragmentTransaction = this
                .getSupportFragmentManager().beginTransaction();
        // 替换当前的页面
        fragmentTransaction.replace(R.id.frame_content, fragment);
        // 事务管理提交
        fragmentTransaction.commit();
        imageAt.setSelected(false);
        layoutAt.setSelected(false);
        imageAuth.setSelected(false);
        layoutAuth.setSelected(false);
        imageSpace.setSelected(false);
        layoutSpace.setSelected(false);
        imageMore.setSelected(false);
        layoutMore.setSelected(false);
        //-------------------------------------
        frameLayout.setSelected(true);
        image.setSelected(true);

    }

    /**
     * 点击了中间按钮
     */
    private void clickToggleBtn() {
        showPopupWindow(toggleBtn);
        // 改变按钮显示的图片为按下时的状态
        plusBtn.setSelected(true);
    }
    /**
     * 改变显示的按钮图片为正常状态
     */
    private void changeButtonImage() {
        plusBtn.setSelected(false);
    }
    /**
     * 显示PopupWindow弹出菜单
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.popwindow_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            //popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
            popWindow = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        // PopupWindow的显示及位置设置
        // popWindow.showAtLocation(parent, Gravity.FILL, 0, 0);
        //popWindow.showAsDropDown(parent, 0,0);
        if (Build.VERSION.SDK_INT < 24) {
            popWindow.showAsDropDown(parent);
            Log.d("MainActivity","<24====");
        } else {
            // 获取控件的位置，安卓系统>7.0
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            popWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, location[1]+parent.getHeight());//+
            Log.d("MainActivity",">=24");
        }
        Log.d("MainActivity","popWindow.isshow===="+popWindow.isShowing());

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 改变显示的按钮图片为正常状态
                changeButtonImage();
            }
        });

        // 监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // 改变显示的按钮图片为正常状态
                changeButtonImage();
                popWindow.dismiss();
                return false;
            }
        });
    }
}
