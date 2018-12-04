package com.coffice.shengtao.cofficemachine.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 普通的对话框
 */
public class NormalDialogActivity extends BaseActivity {

    @BindView(R.id.normaldialog)
    Button normaldialog;
    @BindView(R.id.multibtndialog)
    Button multibtndialog;
    @BindView(R.id.listdialog)
    Button listdialog;
    @BindView(R.id.singlechoicedialog)
    Button singlechoicedialog;
    @BindView(R.id.multicchoicedialog)
    Button multicchoicedialog;
    @BindView(R.id.waitingdialog)
    Button waitingdialog;
    @BindView(R.id.progressdialog)
    Button progressdialog;
    @BindView(R.id.inputdialog)
    Button inputdialog;
    @BindView(R.id.customizedialog)
    Button customizedialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_dialog);
        binder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        normalDialog.setIcon(R.drawable.icon_coffee);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dialog.dismiss();

                    }
                });
        // 显示
        normalDialog.show();
    }

    /* 3个按钮
    @setNeutralButton 设置中间的按钮
     * 若只需一个按钮，仅设置 setPositiveButton 即可
     */
    private void showMultiBtnDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        normalDialog.setIcon(R.drawable.icon_coffee);
        normalDialog.setTitle("我是一个普通Dialog").setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("按钮1",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNeutralButton("按钮2",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNegativeButton("按钮3", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ...To-do
                dialog.dismiss();
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }

    // 2.2 列表Dialog（图3）
    private void showListDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                ToastUtils.showShort(NormalDialogActivity.this, "你点击了" + items[which]);
            }
        });
        listDialog.show();
    }

    //2.3 单选Dialog（图4）
    int yourChoice;

    private void showSingleChoiceDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4"};
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        singleChoiceDialog.setTitle("我是一个单选Dialog");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            ToastUtils.showShort(NormalDialogActivity.this, "你选择了" + items[yourChoice]);
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    // 2.4 多选Dialog（图5）
    ArrayList<Integer> yourChoices = new ArrayList<>();

    private void showMultiChoiceDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4"};
// 设置默认选中的选项，全为false默认均未选中
        final boolean initChoiceSets[] = {false, false, false, false};
        yourChoices.clear();
        AlertDialog.Builder multiChoiceDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        multiChoiceDialog.setTitle("我是一个多选Dialog");
        multiChoiceDialog.setMultiChoiceItems(items, initChoiceSets,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            yourChoices.add(which);
                        } else {
                            yourChoices.remove(which);
                        }
                    }
                });
        multiChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int size = yourChoices.size();
                        String str = "";
                        for (int i = 0; i < size; i++) {
                            str += items[yourChoices.get(i)] + " ";
                        }
                        ToastUtils.showShort(NormalDialogActivity.this,
                                "你选中了" + str);
                    }
                });
        multiChoiceDialog.show();
    }

    // 2.5 等待Dialog（图6）
    private void showWaitingDialog() {
        /* 等待Dialog具有屏蔽其他控件的交互能力
         * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
         * 下载等事件完成后，主动调用函数关闭该Dialog
         */
        ProgressDialog waitingDialog =
                new ProgressDialog(NormalDialogActivity.this);
        waitingDialog.setTitle("我是一个等待Dialog");
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
    }

    // 2.6 进度条Dialog（图7）
    private void showProgressDialog() {
        /* @setProgress 设置初始进度
         * @setProgressStyle 设置样式（水平进度条）
         * @setMax 设置进度最大值
         */
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog =
                new ProgressDialog(NormalDialogActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("我是一个进度条Dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();
        /* 模拟进度增加的过程
         * 新开一个线程，每个100ms，进度增加1
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < MAX_PROGRESS) {
                    try {
                        Thread.sleep(100);
                        progress++;
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 进度达到最大值后，窗口消失
                progressDialog.cancel();
            }
        }).start();
    }

    //2.7 编辑Dialog（图8）
    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(NormalDialogActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        inputDialog.setTitle("我是一个输入Dialog").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.showShort(NormalDialogActivity.this,
                                editText.getText().toString());
                    }
                }).show();
    }
    //自定义 对话框
    private void showCustomizeDialog() {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
         * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
         * dialog_customize.xml可自定义更复杂的View
         */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(NormalDialogActivity.this);
        final View dialogView = LayoutInflater.from(NormalDialogActivity.this)
                .inflate(R.layout.dialog_customize,null);
        customizeDialog.setTitle("我是一个自定义Dialog");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_text =
                                (EditText) dialogView.findViewById(R.id.edit_text);
                        ToastUtils.showShort(NormalDialogActivity.this,
                                edit_text.getText().toString());
                    }
                });
        customizeDialog.show();
    }
    @OnClick({R.id.normaldialog, R.id.multibtndialog, R.id.listdialog, R.id.singlechoicedialog, R.id.multicchoicedialog, R.id.waitingdialog, R.id.progressdialog, R.id.inputdialog, R.id.customizedialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.normaldialog:
                showNormalDialog();
                break;
            case R.id.multibtndialog:
                showMultiBtnDialog();
                break;
            case R.id.listdialog:
                showListDialog();
                break;
            case R.id.singlechoicedialog:
                showSingleChoiceDialog();
                break;
            case R.id.multicchoicedialog:
                showMultiChoiceDialog();
                break;
            case R.id.waitingdialog:
                showWaitingDialog();
                break;
            case R.id.progressdialog:
                showProgressDialog();
                break;
            case R.id.inputdialog:
                showInputDialog();
                break;
            case R.id.customizedialog:
                showCustomizeDialog();
                break;
        }
    }
}

