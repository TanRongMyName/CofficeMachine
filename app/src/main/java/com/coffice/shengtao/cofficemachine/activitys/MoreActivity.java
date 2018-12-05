package com.coffice.shengtao.cofficemachine.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coffice.shengtao.cofficemachine.MainActivity;
import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.adapter.BaseRecyclerHoder;
import com.coffice.shengtao.cofficemachine.adapter.RecycleDataBaseAdapter;
import com.coffice.shengtao.cofficemachine.data.model.KnowPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreActivity extends BaseActivity {

    @BindView(R.id.myreclcler)
    RecyclerView myreclcler;
    private RecycleDataBaseAdapter<KnowPoint> adapter;
    private List<KnowPoint> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        binder = ButterKnife.bind(this);
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @Override
    public void initData() {
        super.initData();
        initlistData();
        adapter=new RecycleDataBaseAdapter<KnowPoint>(this,list,R.layout.item_knowedge) {
            @Override
            public void convert(BaseRecyclerHoder holder, KnowPoint item, int position, boolean isScrolling) {
                holder.setText(R.id.tvTitle,item.getTitle());
                holder.setText(R.id.tvDiscript,item.getDiscript());
                holder.setText(R.id.tvUrl,item.getUrl());
            }
        };
        adapter.setOnItemLongClickListener(new RecycleDataBaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position) {
                Intent intent=new Intent(MoreActivity.this,list.get(position).getCls());
                MoreActivity.this.startActivity(intent);
                return false;
            }
        });
        myreclcler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        myreclcler.setAdapter(adapter);





    }


    public void initlistData(){
        list=new ArrayList<>();
        list.add(new KnowPoint("短信认证",
                "这篇文章主要介绍了Android用 Mob 实现发送短信验证码实例，具有一定的参考价值，感兴趣的小伙伴们可以参考一下\n" ,
                "https://www.cnblogs.com/demodashi/p/8512871.html",SMS_AuthenticationActivity.class));

        list.add(new KnowPoint("数据库操作",
                "对数据库的一种导入，导出操作，加载外部外部数据库，同时将Assits中的数据库拷贝到data/data/packageName/databases下\n" ,
                "https://blog.csdn.net/oaitan/article/details/54412660",DataBaseControlActivity.class));
        list.add(new KnowPoint("数据库整合",
                "目标将LitePal 与GreenDao 数据库融合为一体，结果 失败  LitePal 的crud 绑定在实体上，而GreenDao 将Crud 单独生成类方法" ,
                "https://github.com/LitePalFramework/LitePal||https://github.com/greenrobot/greenDAO",DataBaseUseDescripActivity.class));
        list.add(new KnowPoint("及时视频",
                "作为提供实时音视频业务的 PaaS（Platform as a Service）层，SD-RTN 专注为上层（SaaS）业务开放音视频业务的网络基础能力，解决开发者共性问题，营造友好的开发环境，实现敏捷开发，并为实时音视频业务打造“专网”级别沉浸式网络体验" ,
                "https://www.agora.io/cn/network/",AgoraIVedioActivity.class));

        list.add(new KnowPoint("APP共享数据",
                "默认情况下，Android会给每个程序分配一个普通级别互不相同的Uid，如果应用之间要互相调用，只能是Uid相同才行，这就使得共享数据具有了一定安全性，每个软件之间是不能随意获得数据的。而同一个application只有一个Uid，所以application下的Activity之间不存在访问权限的问题。\n",
                "https://blog.csdn.net/amlinsan/article/details/73292219||https://blog.csdn.net/wirelessqa/article/details/8581652",AppShareResourseActivity.class));
        list.add(new KnowPoint("AIDL双进程守护APP",
                "如果存活就杀死从而达到清理软件的作用的，所以我们是可以拿到自己进程和创建新的进程的。而通过AIDL的接口则可以实现跨进程通信，因此，使用双进程并通过进程间的通信是一种可行的解决方案。因此方案一是通过双进程守护来解决这个Android应用保活的。",
                "https://blog.csdn.net/pan861190079/article/details/72773549||https://blog.csdn.net/mynameishuangshuai/article/details/52769116",DoubleProgressActivity.class));
        list.add(new KnowPoint("Android7.0更新包自动安装",
                "Android7.0引入私有目录被限制访问和StrictMode API 。私有目录被限制访问是指在Android7.0中为了提高应用的安全性，在7.0上应用私有目录将被限制访问，这与iOS的沙盒机制类似。StrictMode API是指禁止向你的应用外公开 file:// URI。 如果一项包含文件 file:// URI类型 的 Intent 离开你的应用，则会报出异常。",
                "https://blog.csdn.net/xqkillua/article/details/79114385",AutoInstall_Start7Activity.class));
        list.add(new KnowPoint("DownLoadManger 通知栏提示网络下载工具",
                "在android开发中，经常会使用到文件下载的功能，比如app版本更新等。在api level 9之后，android系统为我们提供了DownLoadManager类，这是android提供的系统服务，我们通过这个服务完成文件下载。整个下载过程全部交给系统负责，不需要我们过多的处理。",
                "https://www.cnblogs.com/jerehedu/p/4377767.html",DownLoadMangerActivity.class));
        list.add(new KnowPoint("RxJava+Retrofit+okhttp",
                "Retrofit是Square公司出品的基于OkHttp封装的一套RESTful（目前流行的一套api设计的风格）网络请求框架。它内部使用了大量的设计模式，以达到高度解耦的目的；它可以直接通过注解的方式配置请求；可以使用不同的Http客户端；还可以使用json Converter序列化数据，直接转换成你期望生成的实体bean；它还支持Rxjava等等等" + "\n",
                "https://www.jianshu.com/p/b5546905ccbc",RxJava_RetrofitActivity.class));
        list.add(new KnowPoint("android代码执行adb shell终端命令",
                "最近在做项目的过程中，遇到了这么一个需求，需要在Android（4.2.2）启动时候执行以下命令，命令的具体含义不再解释：" + "\n",
                "https://blog.csdn.net/fanenqian/article/details/55252109||https://blog.csdn.net/Z_HUALIN/article/details/77878609",ExcueCmdActivity.class));
        list.add(new KnowPoint("android 8种对话框（Dialog）使用方法汇总",
                "Android提供了丰富的Dialog函数，本文介绍最常用的8种对话框的使用方法，包括普通（包含提示消息和按钮）、列表、单选、多选、等待、进度条、编辑、自定义等多种形式.",
                "https://www.cnblogs.com/gzdaijie/p/5222191.html",NormalDialogActivity.class));
        list.add(new KnowPoint("Android最简便使用漂亮的对话框",
                "android原生的dialog太生硬了，之前看到了这个效果非常不错但是没有用过.网上有很多相关的博客，但是介绍啰嗦，用法复杂。今天给大家介绍一种最简单的实现方法。sweet-alert-dialog",
                "https://blog.csdn.net/taa1007/article/details/79551928",SweetAlertDialogActivity.class));
        list.add(new KnowPoint("Android修改全局字体,整个APP字体",
                "Android里面对字体的支持少得可怜，TextView  的 typeface 属性支持 \"Sans\"，\"serif\"，\"monospace\" 这三种字体，没有指定字体的情况下，系统缺省会使用 \"Sans\" 作为文本显示的字体。",
                "https://blog.csdn.net/gold_brick/article/details/52865369",FontLibActivity.class));
    }
}
