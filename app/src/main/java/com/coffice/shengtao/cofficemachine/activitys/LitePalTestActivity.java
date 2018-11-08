package com.coffice.shengtao.cofficemachine.activitys;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.adapter.BaseRecyclerHoder;
import com.coffice.shengtao.cofficemachine.adapter.RecycleDataBaseAdapter;
import com.coffice.shengtao.cofficemachine.databaseframe.litepal.model.Machine;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LitePalTestActivity extends BaseActivity implements View.OnClickListener{
    private Button add, update, delete;
    private RecyclerView myrecycle;
    private RecycleDataBaseAdapter<Machine>adapter;
    private List<Machine> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal_test);
        initView();
        initEvent();
        initData();

    }

    @Override
    public void initView() {
        super.initView();
        add=findViewById(R.id.add);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        myrecycle=findViewById(R.id.myreclcler);

    }

    @Override
    public void initEvent() {
        super.initEvent();
        add.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        //创建数据库  可以在AppContion 中添加
        SQLiteDatabase db = Connector.getDatabase();
        list = LitePal.findAll(Machine.class);
        adapter = new RecycleDataBaseAdapter<Machine>(this, list, R.layout.item_layout) {
            @Override
            public void convert(BaseRecyclerHoder holder, Machine item, int position, boolean isScrolling) {
                holder.setText(R.id.name, item.getMachineName());
                holder.setText(R.id.machinenumber, item.getNumber());
                holder.setText(R.id.address, item.getAddress());
//                if (item.getImageUrl() != null){
//                    holder.setImageByUrl(R.id.item_image,item.getImageUrl());
//                }else {
//                    holder.setImageResource(R.id.item_image,item.getImageId());
//                }
            }
        };
        adapter.setOnItemClickListener(new RecycleDataBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, final View view, int position) {
                //Toast.makeText(MainActivity.this, String.format(Locale.CHINA,"你点击了第%d项,长按会删除！",position),Toast.LENGTH_SHORT).show();
            }
        });
//
        adapter.setOnItemLongClickListener(new RecycleDataBaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position) {
                // adapter.delete(position);
                return true;
            }
        });
        myrecycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        myrecycle.setAdapter(adapter);


    }
    @Override
    public void onClick(View v) {
        if(list!=null){
            list.clear();
        }
         String massage = null;
         Machine machine;
         switch(v.getId()){
             case R.id.add:
                 machine=new Machine("技诺咖啡机","0001","搜客天地","正常");
                 massage= (machine.save()?"保存成功":"保存失败");
                 break;
             case R.id.update:
                 machine=new Machine("三摩咖啡机","0001","搜客天地","异常");
                 massage=machine.update(1)+"更新返回的Id";
                 //0代表没有修改 或者修改失败  1或者影响的列代表修改成功

                 break;
             case R.id.delete:
                 //删除最后一条信息
                 int id=DataSupport.findLast(Machine.class).getId();
                 massage=LitePal.delete(Machine.class,id)+"删除返回的Id";
                 //0代表没有修改 或者修改失败  1或者影响的列代表删除成功
                 break;
             default:
                 break;
         }
         List list1 = LitePal.findAll(Machine.class);
         list.addAll(list1);
         LogUtils.d("list.size()==="+list.size());
         adapter.notifyDataSetChanged();
         LogUtils.d(massage);
    }



}
