package com.coffice.shengtao.cofficemachine.databaseframe.litepal.model;

/**
 * 物料   咖啡 糖 牛奶 水 橙汁 奶茶
 */
public class Materiel {
    private int id;
    private String name;
    /**
     * 单位
     */
    private String unit;
//    /**
//     * 剩余量  ----加量是自己手动加入 还是 物料添加完事 自动修改机器数据
//     * 开机重启的时候 自己去获取 物料藏中物品的重量
//     */
//    private double surplus;
    //应该是物料藏的信息
}
