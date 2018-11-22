package com.coffice.shengtao.cofficemachine.databaseframe.greendao.dao;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.coffice.shengtao.cofficemachine.application.MyApplication;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoMaster;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoSession;
import com.coffice.shengtao.cofficemachine.databaseframe.greendao.model.Coffee;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import org.greenrobot.greendao.AbstractDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库管理类
 */
public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance;
    //单例
    public static GreenDaoManager getInstance(){
        if (mInstance==null){
            //保证异步处理安全操作
            synchronized (GreenDaoManager.class){
                if (mInstance==null){
                    mInstance=new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    private GreenDaoManager(){
        if (mInstance==null){
            DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(MyApplication.getInstent(),"coffic_vending_machine_db1.db",null);
            mDaoMaster=new DaoMaster(openHelper.getWritableDatabase());
            mDaoSession=mDaoMaster.newSession();

//            //初始化Greendao 数据   dao泛型   --操作数据库
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "coffic_vending_machine_db1.db", null);
//            //获取可写数据库
//            SQLiteDatabase db = helper.getWritableDatabase();
//            //数据库加密  加载数据出现问题
//            //Database db = helper.getEncryptedWritableDb("123");
//            //获取数据库对象
//            DaoMaster daoMaster = new DaoMaster(db);
//            //获取dao对象管理者
//            daosession = daoMaster.newSession();
              //
              //切换到 sd卡上的数据库
//            DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(new GreenDaoContext(),"coffic_vending_machine_db1.db",null);
//            mDaoMaster=new DaoMaster(openHelper.getWritableDatabase());
//            mDaoSession=mDaoMaster.newSession();
        }
    }
    // 加载外部 数据库  SD 上的
//    public void initDB(){
//        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(
//                new GreenDaoContext(), Constants.DB_NAME,null);
//        mDatabase = dbHelper.getReadableDatabase();
//        mDaoMaster = new DaoMaster(mDatabase);
//        mDaoSession = mDaoMaster.newSession();
//    }



    public DaoMaster getMaster(){
        return mDaoMaster;
    }
    public DaoSession getSession(){
        return mDaoSession;
    }
    public DaoSession getNewSession(){
        mDaoSession=mDaoMaster.newSession();
        return mDaoSession;
    }

    private Map<String, AbstractDao> daos = new HashMap<>();

    public synchronized AbstractDao getDao(Class clazz) throws SQLException {
        AbstractDao dao = null;
        String className = clazz.getSimpleName();
        LogUtils.d(className+"======================className");
        if (daos.containsKey(className)) {
            dao = daos.get(clazz);
        }
        //通过反射 在 session 中获取 classSessionDao
        try {
            Class session=Class.forName("com.coffice.shengtao.cofficemachine.databaseframe.greendao.gen.DaoSession");
            String name="get"+clazz.getSimpleName()+"Dao";
            Method m2=  session.getMethod(name);
            dao= (AbstractDao) m2.invoke(mDaoSession);
            if (dao == null) {
                mDaoSession.getDao(clazz);
                daos.put(className, dao);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return dao;

    }
    //清空session
    public void close() {
        for (String key : daos.keySet()) {
            AbstractDao dao = daos.get(key);
            dao = null;
        }
    }
}
