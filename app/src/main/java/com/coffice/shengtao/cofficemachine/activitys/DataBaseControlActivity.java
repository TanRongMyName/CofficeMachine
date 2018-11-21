package com.coffice.shengtao.cofficemachine.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.dao.CoffeeDao;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;
import com.coffice.shengtao.cofficemachine.utils.aplayutils.FileUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class DataBaseControlActivity extends BaseActivity {
    //拷贝文件 需要写入权限 读取权限
    @BindView(R.id.btInsertDB)
    Button btInsertDB;
    @BindView(R.id.btExportDB)
    Button btExportDB;
    @BindView(R.id.loadOutDB)
    Button loadOutDB;
    @BindView(R.id.addAssitDB)
    Button addAssitDB;
    @BindView(R.id.querysize)
    TextView querysize;
    //数据库操作  ---导入数据库  导出数据库
    //加载外部数据库
    //assets 数据拷贝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_control);
        binder = ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        //一进来就开始初始化数据
        if(CoffeeDao.queryAll()!=null) {
            querysize.setText(CoffeeDao.queryAll().size() + "条");
        }
    }

    @Override
    protected void onDestroy() {
        binder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.btInsertDB, R.id.btExportDB, R.id.loadOutDB, R.id.addAssitDB})
    public void onViewClicked(View view) {
        MyRunnable myRunnable=null;
        switch (view.getId()) {
            case R.id.btInsertDB:
                //ls -l "" 查看详细信息
                // 文件夹的位置----    // 需要考的位置----
                //先是 文件存在 ---本地文件创建  文件拷贝
                myRunnable=new MyRunnable(0);
                break;
            case R.id.btExportDB:
                myRunnable=new MyRunnable(1);
                break;
            case R.id.loadOutDB:
                ToastUtils.showLong(DataBaseControlActivity.this,"初始化的时候切换完事了--使用的是外部数据库 修改需要修改代码");

                break;
            case R.id.addAssitDB:
                myRunnable=new MyRunnable(3);
                //将 assit 中文件拷贝到 data/data/pageckageName/databases/中 完事
                break;
        }

        if(myRunnable!=null){
            myRunnable.run();
        }
    }


    //一是异步线程
    public class MyRunnable implements Runnable{
        //根据不同 的type 执行 命令
        public int type;
        public Context context;
        public MyRunnable(int type){
            this.type=type;
        }
        @Override
        public void run() {
            switch(type){
                case 0:
                    FileUtils.copyFile("/sdcard/aa.db","/data/data/com.coffice.shengtao.cofficemachine/databases/coffic_vending_machine_db1.db");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/data/data/com.coffice.shengtao.cofficemachine/databases/coffic_vending_machine_db1.db")));
                    //查询数据
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(DataBaseControlActivity.this,"文件拷贝成功");
                            if(CoffeeDao.queryAll()!=null) {
                                querysize.setText(CoffeeDao.queryAll().size() + "条");
                            }
                        }
                    });
                    break;
                case 1:
                    FileUtils.copyFile("/data/data/com.coffice.shengtao.cofficemachine/databases/coffic_vending_machine_db1.db","/sdcard/aa.db");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/aa.db")));
                    //查看文件是否存在------
                    LogUtils.d("文件拷贝成功与否："+FileUtils.isFileExits("/sdcard/aa.db"));
                    ToastUtils.showShort(DataBaseControlActivity.this,"文件拷贝成功");
                    break;
                case 2:

                    break;
                case 3:
                    //com.coffice.shengtao.cofficemachine
                    FileUtils.copyDBToDatabases(DataBaseControlActivity.this,"com.coffice.shengtao.cofficemachine","coffic_vending_machine_db1.db");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/data/data/com.coffice.shengtao.cofficemachine/databases/coffic_vending_machine_db1.db")));
                    //查询数据
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShort(DataBaseControlActivity.this,"文件拷---贝成功");
                            LogUtils.d("CoffeeDao.queryAll()===?");
                            CoffeeDao.clearCacsh();
                            //需要重新进入一次
                            if(CoffeeDao.queryAll()!=null) {
                                querysize.setText(CoffeeDao.queryAll().size() + "条");
                            }
                        }
                    });
                    break;
                default:
                     break;
            }
        }
    }




   //拷贝assit 中文件到  data/data/pageckageName/databases/中 完事



//////////////////////-----------权限控制需要这四个就可以了----------------/////////////////////////////
    boolean hasRequestPermission=false;
    private String[] cameraPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onResume() {
        super.onResume();
        if (checkDangerousPermissions(this, cameraPermissions)){
        }else {
            if (!hasRequestPermission){
                showScanCodeTip(cameraPermissions);
                hasRequestPermission=true;
            }
        }
    }
    @Override
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        if (requestCode == REQUEST_CODE_CAMERA){
            if (!granted){
                ToastUtils.showShort(DataBaseControlActivity.this,"没有获取到权限 闪退");
                LogUtils.d("没有获取到权限 闪退");
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }
}
