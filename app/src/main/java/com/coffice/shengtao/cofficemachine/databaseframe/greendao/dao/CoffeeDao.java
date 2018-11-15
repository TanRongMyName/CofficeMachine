package com.coffice.shengtao.cofficemachine.databaseframe.greendao.dao;

import com.coffice.shengtao.cofficemachine.application.MyApplication;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.model.Coffee;

import java.util.List;

/**
 * Created by wangjitao on 2017/2/13 0013.
 * E-Mail：543441727@qq.com
 * 使用GreenDao 实现简单的增删改查，下面是基本方法
 * 增加单个数据
 * getShopDao().insert(shop);
 * getShopDao().insertOrReplace(shop);
 * 增加多个数据
 * getShopDao().insertInTx(shopList);
 * getShopDao().insertOrReplaceInTx(shopList);
 * 查询全部
 * List< Shop> list = getShopDao().loadAll();
 * List< Shop> list = getShopDao().queryBuilder().list();
 * 查询附加单个条件
 * .where()
 * .whereOr()
 * 查询附加多个条件
 * .where(, , ,)
 * .whereOr(, , ,)
 * 查询附加排序
 * .orderDesc()
 * .orderAsc()
 * 查询限制当页个数
 * .limit()
 * 查询总个数
 * .count()
 * 修改单个数据
 * getShopDao().update(shop);
 * 修改多个数据
 * getShopDao().updateInTx(shopList);
 * 删除单个数据
 * getTABUserDao().delete(user);
 * 删除多个数据
 * getUserDao().deleteInTx(userList);
 * 删除数据ByKey
 * getTABUserDao().deleteByKey();
 */
public class CoffeeDao {
    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param coffee
     */
    public static void insertCoffee(Coffee coffee) {
        MyApplication.getDaoInstant().getCoffeeDao().insertOrReplace(coffee);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteCoffee(long id) {
        MyApplication.getDaoInstant().getCoffeeDao().deleteByKey(id);
    }

    /**
     * 更新数据  需不需要 Id ？
     */
    public static void updateCoffee(Coffee coffee) {
        MyApplication.getDaoInstant().getCoffeeDao().update(coffee);
    }

    /**
     * 查询Type为1的所有数据
     *
     * @return
     */
//    public static List<Coffee> queryShop() {
//        return MyApplication.getDaoInstant().getCoffeeDao().queryBuilder().where(where(ShopDao.Properties.Type.eq(Shop.TYPE_CART)).list();
//
//    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public static List<Coffee> queryAll() {
        return MyApplication.getDaoInstant().getCoffeeDao().loadAll();
    }

    /**
     * 查询最后一条数据
     *
     * @return
     */
    public static Coffee queryLast() {
        List<Coffee> coffess = MyApplication.getDaoInstant().getCoffeeDao().loadAll();
        if (coffess != null&&coffess.size()>0) {
            return coffess.get(coffess.size() - 1);
        } else {
            return null;
        }
    }
}
//    　　Dao中其它的一些方法
//
//            增加单个数据
//
//    getShopDao().insert(shop);
//
//    getShopDao().insertOrReplace(shop);
//
//    增加多个数据
//
//    getShopDao().insertInTx(shopList);
//
//    getShopDao().insertOrReplaceInTx(shopList);
//
//    查询全部
//
//    List< Shop> list = getShopDao().loadAll();
//
//    List< Shop> list = getShopDao().queryBuilder().list();
//
//查询附加单个条件
//
//        .where()
//
//        .whereOr()
//
//查询附加多个条件
//
//        .where(, , ,)
//
//        .whereOr(, , ,)
//
//查询附加排序
//
//        .orderDesc()
//
//        .orderAsc()
//
//查询限制当页个数
//
//        .limit()
//
//        查询总个数
//
//                .count()
//
//    修改单个数据
//
//    getShopDao().update(shop);
//
//    修改多个数据
//
//    getShopDao().updateInTx(shopList);
//
//    删除单个数据
//
//    getTABUserDao().delete(user);
//
//    删除多个数据
//
//    getUserDao().deleteInTx(userList);
//
//    删除数据ByKey
//
//    getTABUserDao().deleteByKey();

