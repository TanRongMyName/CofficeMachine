package com.coffice.shengtao.cofficemachine.activitys;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coffice.shengtao.cofficemachine.R;
import com.coffice.shengtao.cofficemachine.adapter.BaseRecyclerHoder;
import com.coffice.shengtao.cofficemachine.adapter.RecycleDataBaseAdapter;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.dao.CoffeeDao;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.model.Coffee;
import com.coffice.shengtao.cofficemachine.databaseframe.litepal.model.Machine;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderManager;
import com.coffice.shengtao.cofficemachine.pictureframe.base.ImageLoaderOptions;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;
import com.coffice.shengtao.cofficemachine.utils.ToastUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreenDaoActivity extends BaseActivity {

    @BindView(R.id.add)
    Button add;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.myreclcler)
    RecyclerView myreclcler;
    @BindView(R.id.title)
    TextView title;
    //将要保存的coffee
    private List<Coffee> willSaveList;
    //从数据库中获取到的coffee
    private List<Coffee> fromDB;
    //每次点击都递增一次  操出异常 复位为零
    private int conunt=0;

    private RecycleDataBaseAdapter<Coffee>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        binder = ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        initCoffe();
        fromDB = CoffeeDao.queryAll();
        adapter = new RecycleDataBaseAdapter<Coffee>(this,fromDB,R.layout.item_cofe){
            @Override
            public void convert(BaseRecyclerHoder holder, Coffee item, int position, boolean isScrolling) {
                holder.setText(R.id.name_china,item.getName_china());
                holder.setText(R.id.name_english,item.getName_english());
                holder.setText(R.id.sell_price,"¥"+item.getPrice()+"");
                holder.getTextView(R.id.sell_price).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                holder.setText(R.id.discount_price,"¥"+item.getDiscount_price()+"");
                holder.setText(R.id.discriptcoffee,item.getDiscrip());
               // holder.setImageResource(R.id.imageCoffee,R.mipmap.caffee1);
                holder.setImageByUrl(R.id.imageCoffee,item.getImageurl());
            }
        };
        adapter.setOnItemClickListener(new RecycleDataBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, final View view, int position) {

            }
        });
//
        adapter.setOnItemLongClickListener(new RecycleDataBaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View view, int position) {

                return true;
            }
        });
        myreclcler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        myreclcler.setAdapter(adapter);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @OnClick({R.id.add, R.id.update, R.id.delete})
    public void onViewClicked(View view) {
        if(fromDB!=null){
            fromDB.clear();
        }
        CoffeeDao.clearCacsh();
        Coffee coffee=null;
        switch (view.getId()) {
            case R.id.add:
                conunt=conunt>=willSaveList.size()?0:conunt;
                CoffeeDao.insertCoffee(willSaveList.get(conunt));
                conunt++;
                break;
            case R.id.update:
                //更新最后一个
                coffee=CoffeeDao.queryLast();
                if(coffee!=null){
                    coffee.setPrice(30.00);
                    CoffeeDao.updateCoffee(coffee);
                }else{
                    ToastUtils.showShort(this,"请先插入一条数据");
                }

                break;
            case R.id.delete:
                coffee=CoffeeDao.queryLast();
                if(coffee!=null){
                    CoffeeDao.deleteCoffee(coffee.getId());
                }else{
                    ToastUtils.showShort(this,"请先插入一条数据");
                }

                break;
        }
        coffee=null;
        List list1 = CoffeeDao.queryAll();
        fromDB.addAll(list1);
        if(view.getId()==R.id.delete){
            conunt=list1.size();
        }
        LogUtils.d("list.size()==="+fromDB.size());
        adapter.notifyDataSetChanged();
    }


    /**
     * 初始化coffee 信息----用来添加到 数据库中----
     */
    public void initCoffe(){
        willSaveList=new ArrayList<>();
        //Long id, String name_china, String name_english, String kind, double price, double discount_price, String imageurl
        //id 可以为空
        willSaveList.add(new Coffee(null,"拿铁咖啡","Caffè Latte","咖啡类 coffee",22.00,15.00,
                "https://imgsa.baidu.com/exp/w=480/sign=98ef422ba1cc7cd9fa2d35d109002104/ca1349540923dd54620efeaed309b3de9d8248f2.jpg",
                "浓缩咖啡与牛奶的经典混合。咖啡在底层，牛奶在咖啡上面，最上面是一层奶泡。也可以放一些焦糖就成了焦糖拿铁。"));

        willSaveList.add(new Coffee(null,"白咖啡","Flat Wite","咖啡类 coffee",20.00,15.00,
                "https://imgsa.baidu.com/exp/w=480/sign=52b3d057cf11728b302d8d2af8fec3b3/ac4bd11373f08202d91ccef949fbfbedaa641b6d.jpg",
                "是马来西亚的特产，白咖啡的颜色并不是白色，但是比普通咖啡更清淡柔和，白咖啡味道纯正，甘醇芳香。"));

        willSaveList.add(new Coffee(null,"美式咖啡","Americano","咖啡类 coffee",16.00,14.00,
                "https://imgsa.baidu.com/exp/w=480/sign=70da9204b0119313c743feb8553a0c10/77c6a7efce1b9d16de63c6a7f1deb48f8d54644b.jpg",
                "是最普通的咖啡，属于黑咖啡的一种。在浓缩咖啡中直接加入大量的水制成，口味比较淡，咖啡因含量较高。"));

        willSaveList.add(new Coffee(null,"卡布奇洛","Cappuccino","咖啡类 coffee",22.00,15.00,
                "https://imgsa.baidu.com/exp/w=480/sign=0359b671cb177f3e1034fd0540ce3bb9/34fae6cd7b899e51dd221ed340a7d933c9950d4c.jpg",
                "以等量的浓缩咖啡和蒸汽泡沫牛奶混合的意大利咖啡。咖啡的颜色就像卡布奇诺教会的修士在深褐色的外衣上覆上一条头巾一样，咖啡因此得名。"));

        willSaveList.add(new Coffee(null,"浓缩咖啡","Espresso","咖啡类 coffee",18.00,15.00,
                "https://imgsa.baidu.com/exp/w=480/sign=a67e2a51db33c895a67e9973e1127397/2f738bd4b31c870108d1ef20257f9e2f0608ff95.jpg",
                "属于意式咖啡，就是我们平常用咖啡直接冲出来的那种，味道浓郁，入口微苦，咽后留香。适合上班族。"));

        willSaveList.add(new Coffee(null,"玛琪雅朵","Machiatto","咖啡类 coffee",29.00,25.00,
                "https://imgsa.baidu.com/exp/w=480/sign=82b3e5d0cdfc1e17fdbf8d397a91f67c/562c11dfa9ec8a13a23d0526f503918fa1ecc0d5.jpg",
                "在浓缩咖啡中加上两大勺奶泡就成了一杯马琪雅朵。玛奇朵在意大利文里是印记、烙印的意思，所以象征着甜蜜的印记。"));

        willSaveList.add(new Coffee(null,"康宝蓝","Con Panna","咖啡类 coffee",26.00,24.00,
                "https://imgsa.baidu.com/exp/w=480/sign=6054218489d4b31cf03c95b3b7d4276f/6c224f4a20a44623234e9fa59a22720e0df3d774.jpg",
                "意大利咖啡品种之一，与玛琪雅朵齐名，由浓缩咖啡喝鲜奶油混合而成，咖啡在下面，鲜奶油在咖啡上面。"));

        willSaveList.add(new Coffee(null,"摩卡咖啡","CafeMocha","咖啡类 coffee",28.00,24.00,
                "https://imgsa.baidu.com/exp/w=480/sign=edd29fe703e9390156028c364bed54f9/dc54564e9258d10922d58e75d358ccbf6d814dc6.jpg",
                "是一种最古老的咖啡，是由意大利浓缩咖啡、巧克力酱、鲜奶油和牛奶混合而成，摩卡得名于有名的摩卡港。其独特之甘，酸，苦味，极为优雅。" +
                        "为一般高级人士所喜爱的优良品种。普通皆单品饮用。饮之润滑可口。醇味历久不退。若调配综合咖啡，更是一种理想的品种。"));

        willSaveList.add(new Coffee(null,"焦糖玛琪朵","Caramel Macchiato","咖啡类 coffee",29.00,26.00,
                "https://imgsa.baidu.com/exp/w=480/sign=6bda8fe1fffaaf5184e380b7bc5694ed/314e251f95cad1c80247dbe07d3e6709c83d517c.jpg",
                "由香浓热牛奶上加入浓缩咖啡、香草，最后淋上纯正焦糖而成，“Caramel”就是焦糖的意思。焦糖玛琪朵就是加了焦糖的Macchiato，代表“甜蜜的印记”。"));

        willSaveList.add(new Coffee(null,"维也纳咖啡","Viennese","咖啡类 coffee",28.00,23.00,
                "https://imgsa.baidu.com/exp/w=480/sign=41800d2fbe3eb13544c7b6b3961fa8cb/0ff41bd5ad6eddc49050953c3bdbb6fd53663345.jpg",
                "奥地利最著名的咖啡，由浓缩咖啡、鲜奶油和巧克力混合而成。奶油柔和爽口，咖啡润滑微苦，糖浆即溶未溶。"));

        willSaveList.add(new Coffee(null,"爱尔兰咖啡","Irish Coffee","咖啡类 coffee",29.00,25.00,
                "https://imgsa.baidu.com/exp/w=480/sign=a27a16b87af0f736d8fe4d093a54b382/a8014c086e061d95036517ff79f40ad163d9cad1.jpg",
                "是一种既像酒又像咖啡的咖啡，由爱尔兰威士忌加入浓缩咖啡中，再在最上面放上一层鲜奶油构制而成。可以这样说，爱尔兰咖啡是一种含有酒精的咖啡。"));

    }
}
