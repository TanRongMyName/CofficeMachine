package com.coffice.shengtao.cofficemachine.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity.BottomNavigationActivity;
import com.coffice.shengtao.cofficemachine.activitys.bottomMenuActivity.PopWindowFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomMenuListActivity extends AppCompatActivity {

    @BindView(R.id.menuway1)
    Button menuway1;
    @BindView(R.id.menuway2)
    Button menuway2;
    @BindView(R.id.menuway3)
    Button menuway3;
    @BindView(R.id.menuway4)
    Button menuway4;
    @BindView(R.id.menuway5)
    Button menuway5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.menuway1, R.id.menuway2, R.id.menuway3, R.id.menuway4, R.id.menuway5})
    public void onViewClicked(View view) {
        Intent intent=null;
        Bundle bundle=new Bundle();

        switch (view.getId()) {
            case R.id.menuway1:
                intent=new Intent(BottomMenuListActivity.this,BottomNavigationActivity.class);
                bundle.putString("MenuWay",((Button)view).getText().toString());
                break;
            case R.id.menuway2:
                intent=new Intent(BottomMenuListActivity.this,PopWindowFragmentActivity.class);
                bundle.putString("MenuWay",((Button)view).getText().toString());
                break;
            case R.id.menuway3:
                bundle.putInt("MenuWay",3);
                break;
            case R.id.menuway4:
                bundle.putInt("MenuWay",4);
                break;
            case R.id.menuway5:
                bundle.putInt("MenuWay",5);
                break;
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
