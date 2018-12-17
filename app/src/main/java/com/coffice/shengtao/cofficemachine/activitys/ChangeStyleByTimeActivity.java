package com.coffice.shengtao.cofficemachine.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

/**
 * 白天黑夜换肤----
 *
 * SkinCompatManager  的缺陷 是 ---布局的背景图片改变不了！！！！！
 * 同时没有 使用---外部皮肤更新
 */
public class ChangeStyleByTimeActivity extends BaseActivity {

    @BindView(R.id.button11)
    Button button11;
    @BindView(R.id.skinlib)
    ImageButton skinlib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sputils是對SharedPreferences的封裝，代碼就不上了，大家理解意思就行了
        if (SPUtils.get(this, GlobalData.THEME_KEY, GlobalData.THEME_DAY).equals(GlobalData.THEME_DAY)) {
            //默認是白天主題
            setTheme(R.style.BaseAppThemeLight);
        } else {
            //否则是晚上主題
            setTheme(R.style.BaseAppThemeNight);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_style_by_time);
        binder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @OnClick({R.id.button11, R.id.skinlib})
    public void onViewClicked(View view) {
        if (SPUtils.get(ChangeStyleByTimeActivity.this, GlobalData.THEME_KEY, GlobalData.THEME_DAY).equals(GlobalData.THEME_DAY)) {
            SPUtils.put(ChangeStyleByTimeActivity.this, GlobalData.THEME_KEY, GlobalData.THEME_NIGHT);
            SkinCompatManager.getInstance().restoreDefaultTheme();
        } else {
            SPUtils.put(ChangeStyleByTimeActivity.this, GlobalData.THEME_KEY, GlobalData.THEME_DAY);
            SkinCompatManager.getInstance().loadSkin("night", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
        }
        switch (view.getId()) {
            case R.id.button11:
                //重新进入一遍
            finish();
            final Intent themeintent = getIntent();
            themeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(themeintent);
            overridePendingTransition(0, 0);
            //或者 recreate();
                break;
            case R.id.skinlib:
            if (SPUtils.get(ChangeStyleByTimeActivity.this, GlobalData.THEME_KEY, GlobalData.THEME_DAY).equals(GlobalData.THEME_DAY)) {
                LogUtils.d("restoreDefaultTheme");
            } else {
                LogUtils.d("loadSkin");
            }
                break;
        }
    }

//    @OnClick({R.id.button11})
//    public void onViewClicked(View view) {

//        if(view.getId()==R.id.button11) {
//            // recreate();//重新创建activity  ----可以设置的时候  ---完事了recreate---
////        boolean cc = SPHelper.getTheme(ctx);
////        cb_theme.setChecked(!cb_theme.isChecked());
////        SPHelper.setTheme(ctx, !cc);
//            finish();
//            final Intent themeintent = getIntent();
//            themeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(themeintent);
//            overridePendingTransition(0, 0);
//        }else if(view.getId()==R.id.skinlib){
//            if (SPUtils.get(ChangeStyleByTimeActivity.this, GlobalData.THEME_KEY, GlobalData.THEME_DAY).equals(GlobalData.THEME_DAY)) {
//                LogUtils.d("restoreDefaultTheme");
//                SkinCompatManager.getInstance().restoreDefaultTheme();
//
//            } else {
//                SPUtils.put(ChangeStyleByTimeActivity.this, GlobalData.THEME_KEY, GlobalData.THEME_DAY);
//                SkinCompatManager.getInstance().loadSkin("night", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
//                LogUtils.d("loadSkin");
//            }
//        }
//    }


}
