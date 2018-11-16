package com.coffice.shengtao.cofficemachine.databaseframe.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.litepal.annotation.Column;

/**
 * E-Mail：543441727@qq.com
 *
 * Bean 对象注释的解释
 *
 * @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 @Property：可以自定义字段名，注意外键不能使用该属性
 @NotNull：属性不能为空
 @Transient：使用该注释的属性不会被存入数据库的字段中
 @Unique：该属性值必须在数据库中是唯一值
 @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 */
//拿铁咖啡  Caffè Latte https://imgsa.baidu.com/exp/w=480/sign=98ef422ba1cc7cd9fa2d35d109002104/ca1349540923dd54620efeaed309b3de9d8248f2.jpg
//浓缩咖啡与牛奶的经典混合。咖啡在底层，牛奶在咖啡上面，最上面是一层奶泡。也可以放一些焦糖就成了焦糖拿铁。

//白咖啡     Flat Wite  https://imgsa.baidu.com/exp/w=480/sign=52b3d057cf11728b302d8d2af8fec3b3/ac4bd11373f08202d91ccef949fbfbedaa641b6d.jpg
//是马来西亚的特产，白咖啡的颜色并不是白色，但是比普通咖啡更清淡柔和，白咖啡味道纯正，甘醇芳香。

        //美式咖啡   Americano  https://imgsa.baidu.com/exp/w=480/sign=70da9204b0119313c743feb8553a0c10/77c6a7efce1b9d16de63c6a7f1deb48f8d54644b.jpg
//是最普通的咖啡，属于黑咖啡的一种。在浓缩咖啡中直接加入大量的水制成，口味比较淡，咖啡因含量较高。

//卡布奇洛   Cappuccino https://imgsa.baidu.com/exp/w=480/sign=0359b671cb177f3e1034fd0540ce3bb9/34fae6cd7b899e51dd221ed340a7d933c9950d4c.jpg
//以等量的浓缩咖啡和蒸汽泡沫牛奶混合的意大利咖啡。咖啡的颜色就像卡布奇诺教会的修士在深褐色的外衣上覆上一条头巾一样，咖啡因此得名。

//浓缩咖啡   Espresso   https://imgsa.baidu.com/exp/w=480/sign=a67e2a51db33c895a67e9973e1127397/2f738bd4b31c870108d1ef20257f9e2f0608ff95.jpg
//  属于意式咖啡，就是我们平常用咖啡直接冲出来的那种，味道浓郁，入口微苦，咽后留香。适合上班族。

//玛琪雅朵   Machiatto  https://imgsa.baidu.com/exp/w=480/sign=82b3e5d0cdfc1e17fdbf8d397a91f67c/562c11dfa9ec8a13a23d0526f503918fa1ecc0d5.jpg
//  在浓缩咖啡中加上两大勺奶泡就成了一杯马琪雅朵。玛奇朵在意大利文里是印记、烙印的意思，所以象征着甜蜜的印记。

//康宝蓝     Con Panna  https://imgsa.baidu.com/exp/w=480/sign=6054218489d4b31cf03c95b3b7d4276f/6c224f4a20a44623234e9fa59a22720e0df3d774.jpg
//意大利咖啡品种之一，与玛琪雅朵齐名，由浓缩咖啡喝鲜奶油混合而成，咖啡在下面，鲜奶油在咖啡上面。

//摩卡咖啡   CafeMocha  https://imgsa.baidu.com/exp/w=480/sign=edd29fe703e9390156028c364bed54f9/dc54564e9258d10922d58e75d358ccbf6d814dc6.jpg
//是一种最古老的咖啡，是由意大利浓缩咖啡、巧克力酱、鲜奶油和牛奶混合而成，摩卡得名于有名的摩卡港。其独特之甘，酸，苦味，极为优雅。为一般高级人士所喜爱的优良品种。普通皆单品饮用。饮之润滑可口。醇味历久不退。若调配综合咖啡，更是一种理想的品种。

//焦糖玛琪朵 Caramel Macchiato https://imgsa.baidu.com/exp/w=480/sign=6bda8fe1fffaaf5184e380b7bc5694ed/314e251f95cad1c80247dbe07d3e6709c83d517c.jpg
//由香浓热牛奶上加入浓缩咖啡、香草，最后淋上纯正焦糖而成，“Caramel”就是焦糖的意思。焦糖玛琪朵就是加了焦糖的Macchiato，代表“甜蜜的印记”。

//维也纳咖啡 Viennese   https://imgsa.baidu.com/exp/w=480/sign=41800d2fbe3eb13544c7b6b3961fa8cb/0ff41bd5ad6eddc49050953c3bdbb6fd53663345.jpg
//奥地利最著名的咖啡，由浓缩咖啡、鲜奶油和巧克力混合而成。奶油柔和爽口，咖啡润滑微苦，糖浆即溶未溶。

//爱尔兰咖啡 Irish Coffee https://imgsa.baidu.com/exp/w=480/sign=a27a16b87af0f736d8fe4d093a54b382/a8014c086e061d95036517ff79f40ad163d9cad1.jpg
//是一种既像酒又像咖啡的咖啡，由爱尔兰威士忌加入浓缩咖啡中，再在最上面放上一层鲜奶油构制而成。可以这样说，爱尔兰咖啡是一种含有酒精的咖啡。


@Entity
public class Coffee {
    //自动增长的id
    @Id(autoincrement = true)
    private Long id;
    //名称
    @Unique
    private String name_china;
    //英文名称
    @Unique
    private String name_english;
    //分类
    @Property(nameInDb = "coffee_kind")
    private String kind;
    //原价
    @Property(nameInDb = "self_price")
    private double price;
    //折扣后的价格
    @Property(nameInDb = "disconunt_kind")
    private double discount_price;
    //图片的url
    private String imageurl;
    //描述
    private String discrip;



    @Generated(hash = 1607565396)
    public Coffee(Long id, String name_china, String name_english, String kind, double price, double discount_price, String imageurl, String discrip) {
        this.id = id;
        this.name_china = name_china;
        this.name_english = name_english;
        this.kind = kind;
        this.price = price;
        this.discount_price = discount_price;
        this.imageurl = imageurl;
        this.discrip = discrip;
    }

    @Generated(hash = 1336473093)
    public Coffee() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_china() {
        return name_china;
    }

    public void setName_china(String name_china) {
        this.name_china = name_china;
    }

    public String getName_english() {
        return name_english;
    }

    public void setName_english(String name_english) {
        this.name_english = name_english;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(double discount_price) {
        this.discount_price = discount_price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDiscrip() {
        return discrip;
    }

    public void setDiscrip(String discrip) {
        this.discrip = discrip;
    }
}
