package com.coffice.shengtao.cofficemachine.fragments;


import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    public boolean isVisible;

    /**
     * inflate布局文件 返回的view
     */
    public View mView;

    /**
     * 简化 findViewById
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T find(int viewId) {
            return (T) mView.findViewById(viewId);
    }


    //对权限的申请
    private Unbinder unbinder;
    /**
     * 请求权限
     *
     * @param permissions
     * @param requestCode
     */
    public void requestDangerousPermissions(String[] permissions, int requestCode) {
        if (checkDangerousPermissions(permissions)){
            handlePermissionResult(requestCode, true);
            return;
        }
        requestPermissions(permissions, requestCode);
    }

    /**
     * 检查是否已被授权危险权限
     * @param permissions
     * @return
     */
    public boolean checkDangerousPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity() == null){
            return false;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
        }

        if (!handlePermissionResult(requestCode, isGranted)){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 处理请求危险权限的结果
     *
     * @param requestCode
     * @param isGranted 是否允许
     * @return
     */
    public boolean handlePermissionResult(int requestCode, boolean isGranted) {
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }


    /**
     * setUserVisibleHint是在onCreateView之前调用的
     * 设置Fragment可见状态
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /**
         * 判断是否可见
         */
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    private void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    private void onInvisible() {
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    public abstract void lazyLoad();

}




