package com.coffice.shengtao.cofficemachine.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.adapter.listview_spinner.CommonAdapter;
import com.coffice.shengtao.cofficemachine.adapter.listview_spinner.ViewHolder;
import com.coffice.shengtao.cofficemachine.application.MyApplication;
import com.coffice.shengtao.cofficemachine.data.GlobalData;
import com.coffice.shengtao.cofficemachine.data.model.FontBean;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.SPUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * spinner 的使用  万能适配器   自定义的空间属性   app 整个 字体库的设置---
 *
 * 方法1：自定义控件 FontTextView
 * 优点：使用简单方便，不需要额外的工作。
 * 缺点：只能替换一类控件的字体，如果需要替换Button或EditText控件的字体，需要以相同的方式自定义这些控件，这样工作量大。
 * 方法2：递归批量替换某个View及其子View的字体
 * 优点：不需要修改XML布局文件，不需要重写控件，可以批量替换所有继承自TextView的控件的字体，适合需要批量替换字体的场合，如程序的默认字体。
 * 缺点：如果要替换整个App的所有字体，需要在每个有界面的地方批量替换一次，页面多了还是有些工作量的，不过可以在Activity和Fragment的基类中完成这个工作。其次，性能可能差一点，毕竟要递归遍历所有子节点（不过实际使用中没有明显的性能下降程序依然流畅）。
 * 方法3：通过反射替换默认字体
 * 优点：方式2的优点+更加简洁
 * 缺点：字体文件一般比较大，加载时间长而且占内存（不过实际使用中没有明显的性能下降程序依然流畅）。
 * 个人中心设置
 * 我一般都是用第2，3种，简洁高效，现在说一下如何在个人设置里边改变你的app字体：
 * 经实践，第2种方法是最好的，可以实时更新页面。而第三种需要返回重新进入到activity才会看到效果。
 * 方法4
 * 先在BaseActivity注册一个字体改变监听的广播
 * 使用注意：
 * 1.如果字体文件比较大，当设置后可能并不会立即生效，有1~2s的延迟，具体还依据类中控件的数量来定。
 * 2.至关重要，所有的Activity请务必要继承BaseActivity。
 */
public class FontLibActivity extends BaseActivity {

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.fontlibseletcdialog)
    Button fontlibseletcdialog;
    @BindView(R.id.apptextfont)
    Button apptextfont;
    @BindView(R.id.textchangefont)
    TextView textchangefont;
    @BindView(R.id.selfsetfont)
    TextView selfsetfont;

    public List<FontBean> data;
    public String[] fontlibnames;
    public String[]fontlibpaths;
    private AlertDialog.Builder singleChoiceDialog;
    private int TypeFontSelected=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_lib);
        binder = ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        fontlibnames= getResources().getStringArray(R.array.fontlib);
        fontlibpaths= getResources().getStringArray(R.array.fontlibpath);
        data=new ArrayList<>();
        if(fontlibnames.length>0&&fontlibnames.length==fontlibpaths.length){
            LogUtils.d("fontlibnames.length==="+fontlibnames.length);
            for(int i=0;i<fontlibnames.length;i++) {
                data.add(new FontBean(fontlibnames[i],Typeface.createFromAsset(this.getAssets(), fontlibpaths[i])));
            }
        }
        spinner.setAdapter(new CommonAdapter<FontBean>(this,data,android.R.layout.simple_spinner_item) {  //却别 一个紧凑  一个离散
        //spinner.setAdapter(new CommonAdapter<FontBean>(this,data,android.R.layout.simple_spinner_dropdown_item) {  窗口弹出 字体靠近左边

            @Override
            public void convert(ViewHolder holder, FontBean fontBean) {
                holder.setText(android.R.id.text1,fontBean.getFontname());
            }
        });
        //改变字体
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d("选中额是===positon=="+position+"-----"+data.get(position).getFontname());
                //改变字体的状况
                textchangefont.setTypeface(data.get(position).getTypeface());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        singleChoiceDialog =
                new AlertDialog.Builder(FontLibActivity.this);
        singleChoiceDialog.setTitle("请选择文字的字体");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(fontlibnames, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TypeFontSelected=which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TypeFontSelected != -1) {
                            textchangefont.setTypeface(data.get(TypeFontSelected).getTypeface());
                            dialog.dismiss();
                        }
                    }
                });
        singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @OnClick({R.id.fontlibseletcdialog, R.id.apptextfont})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fontlibseletcdialog:
                singleChoiceDialog.show();
                break;
            case R.id.apptextfont:
                LogUtils.d("设置整体的---字体库为：font/fhffont.ttf");
                MyApplication.getInstent().setAPPTypeFont("font/fhffont.ttf");
                //可以将 字体库保存到shareperfence 中-----开机的时候设置 设置的覆盖

//                //发送广播 来 ----设置字体
//                Intent intent = new Intent(GlobalData.TYPEFACECHANGEACTION);
//                String typeface = null;
//                //保存字体设置
//                SPUtils.put(this,GlobalData.FONTTYPE_PATH_KEY, "font/fhffont.ttf");
//                intent.putExtra("typeface", typeface);
//                sendBroadcast(intent);
                break;
        }
    }
}
