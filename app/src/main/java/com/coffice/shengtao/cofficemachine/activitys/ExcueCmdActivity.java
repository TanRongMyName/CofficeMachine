package com.coffice.shengtao.cofficemachine.activitys;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.utils.ExeCmdUtils;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//开机的时候 也可以在MyApplication 中执行cmd 命令 指定操作 工具
//httpsblog.csdn.netZ_HUALINarticledetails77878609 adb 命令行
//在做Android开发板相关的开发需求的时候，我们有的时候需要去到Android系统的一个linux终端（adbshell）、
//里面来执行一些命令，以便于查看部分文件内容或者修改部分文件权限等需求，通常我们可以使用Android studio自带的terminal工具运行先adb shell 命令来进行
public class ExcueCmdActivity extends BaseActivity {
    public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String AAA_PATH = SDCARD_ROOT + "/wifidog.conf";

    @BindView(R.id.unistallSilent)
    Button installSilent;
    @BindView(R.id.installlocation)
    Button installlocation;
    @BindView(R.id.updatehosts)
    Button updatehosts;
    @BindView(R.id.copyfile)
    Button copyfile;


    //需要root 权限
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excue_cmd);
        binder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        binder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.unistallSilent, R.id.installlocation, R.id.updatehosts, R.id.copyfile})
    public void onViewClicked(final View view) {
        String cmd3 = null;
        String result = null;
        switch (view.getId()) {
            case R.id.unistallSilent:
                //读取目标文件（绝对路径）指定内容“#TrustedMACList ”的那一行
                //touch file   cat filename 创建文件
                //cmd3="echo -n '/#TrustedMACList /,//p' "+AAA_PATH; //向文件中 写入文件
                //卸载文件----
                cmd3 = "pm uninstall com.jinuo.mhwang.jetinnocoffe";
                //String str3 = new ExeCmdUtils().run(cmd3[0], 10000).getResult();
                //com.jinuo.mhwang.jetinnocoffe
                ////自身卸载失败
                break;
            case R.id.installlocation:
                cmd3 = "pm get-install-location";//现在这个命令没有了
//                 APP_INSTALL_AUTO     = 0;
//                 APP_INSTALL_INTERNAL = 1;  安装在内部
//                 APP_INSTALL_EXTERNAL = 2;  安装在sd卡上
                break;
            case R.id.updatehosts:
                cmd3 = "mount -o rw,remount /system\n" +
                        "echo “127.0.0.1 localhost” > /etc/hosts \n" +
                        "echo “185.31.17.184 github.global.ssl.fastly.net” >> /etc/hosts \n" +
                        "chmod 644 /etc/hosts";
                break;
            case R.id.copyfile:
                cmd3 = "mount -o rw,remount /system \n" + "cp /mnt/sdcard/mnt/internal_sd/test.apk /system/app/";
                //  Android adb shell操作时出现“ XXX ... Read-only file system”解决办法   重新分区  第一句
                //mount -o remount rw  /system
                //chmod 777 test.apk 文件授权
                break;
        }
        final String finalCmd = cmd3;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str3 = new ExeCmdUtils().run(finalCmd, 10000).getResult();
                LogUtils.d("result==" + str3);
                if (view.getId() == R.id.installlocation) {
                    if(str3!=null&&str3.length()>0) {
                        str3 = str3.substring(0, 1);
                        LogUtils.d("result==" + str3);
                        str3 = (Integer.getInteger(str3) == 0 ? "制动安装" : (Integer.getInteger(str3) == 1 ? "安装在内部" : "安装在sd卡"));
                    }else{
                        str3= "制动安装";
                    }
                }
                RunOnUi(str3);
            }
        }).start();
        LogUtils.d("cmd==" + cmd3);
    }

    public void RunOnUi(final String context) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(ExcueCmdActivity.this, context);
            }
        });

    }
}



//         安装APK：
//         pm install [-l] [-r] [-t] [-i INSTALLER_PACKAGE_NAME] [-s] [-f] PATH
//         PATH 指 APK文件绝对路径和文件名。
//         例如：
//         pm install /data/3dijoy_fane.apk
//         这几个参数很有用：
//         -r: 安装一个已经安装的APK，保持其数据不变。
//         -i：指定安装的包名。(没试出来)
//         -s: 安装到SDCard上。
//         -f: 安装到内部Flash上。
//         卸载APK：
//         pm uninstall 包名。
//         例如：
//         pm uninstall com.TDiJoy.fane


//        例如往文件a.txt写入内容Hello World!
//        方法一：
//        [root@cc ~]# vim a.txt
//        Hello World!
//        wq
//        方法二：
//        [root@cc ~]# echo 'Hello World!'>a.txt
//        方法三：
//        cat b.txt
//        Hello World!
//        cat b.txt>a.txt
