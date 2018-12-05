package com.coffice.shengtao.cofficemachine.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertDialogActivity extends BaseActivity {
    private SweetAlertDialog pDialog ;
    @BindView(R.id.showtitle)
    Button showtitle;
    @BindView(R.id.showtitle_context)
    Button showtitleContext;
    @BindView(R.id.model_error)
    Button modelError;
    @BindView(R.id.model_alert)
    Button modelAlert;
    @BindView(R.id.model_success)
    Button modelSuccess;
    @BindView(R.id.custom_header)
    Button customHeader;
    @BindView(R.id.addsubmit)
    Button addsubmit;
    @BindView(R.id.addcancel)
    Button addcancel;
    @BindView(R.id.changestytle)
    Button changestytle;
    @BindView(R.id.progressdialog)
    Button progressdialog;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweet_alert_dialog);
        binder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @OnClick({R.id.showtitle, R.id.showtitle_context, R.id.model_error, R.id.model_alert, R.id.model_success, R.id.custom_header, R.id.addsubmit, R.id.addcancel, R.id.changestytle, R.id.progressdialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showtitle:
                pDialog=new SweetAlertDialog(this)
                        .setTitleText("Here's a message!");
                break;
            case R.id.showtitle_context:
                pDialog=new SweetAlertDialog(this)
                        .setTitleText("Here's a message!")
                        .setContentText("It's pretty, isn't it?");
                break;
            case R.id.model_error:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!");
                break;
            case R.id.model_alert:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!");
                break;
            case R.id.model_success:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!");
                break;
            case R.id.custom_header:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("Sweet!")
                        .setContentText("Here's a custom image.")
                        .setCustomImage(R.drawable.icon_coffee);
                break;
            case R.id.addsubmit:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        });
                break;
            case R.id.addcancel:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plx!")
                        .setConfirmText("Yes,delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        });
                break;
            case R.id.changestytle:
                pDialog=new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        });
                break;
            case R.id.progressdialog:
                pDialog= new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);

//                resetCount()
//                isSpinning()
//                spin()
//                stopSpinning()
//                getProgress()
//                setProgress(float progress)
//                setInstantProgress(float progress)
//                getCircleRadius()
//                setCircleRadius(int circleRadius)
//                getBarWidth()
//                setBarWidth(int barWidth)
//                getBarColor()
//                setBarColor(int barColor)
//                getRimWidth()
//                setRimWidth(int rimWidth)
//                getRimColor()
//                setRimColor(int rimColor)
//                getSpinSpeed()
//                setSpinSpeed(float spinSpeed)
                break;
        }
        pDialog.show();
    }
}
