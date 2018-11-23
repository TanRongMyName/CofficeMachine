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
    }
}
