package com.coffice.shengtao.cofficemachine.databaseframe.greendao.dao;
import com.coffice.shengtao.cofficemachine.utils.LogUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * greendao 在外部调用   直接操作数据库   一个类不用 重复写多个 是实现增删查改方法
 */
public class GreenDao extends BaseDao {
    private Class clazz;//根据不同的 clazz 获取到sessiondao  操作

    //同时 使用单利
    private static GreenDao greenDao;
    //单例
    public static GreenDao getInstance(){
        if (greenDao==null){
            //保证异步处理安全操作
            synchronized (GreenDao.class){
                if (greenDao==null){
                    greenDao=new GreenDao();
                }
            }
        }
        return greenDao;
    }
    private GreenDao(){

    }
    //可以使用链式编程 ----
    public GreenDao setClass(Class clazz){
        this.clazz=clazz;
        return greenDao;
    }


    @Override
    public int save(Object o) {
        GreenDaoManager.getInstance().getDao(this.clazz).save(o);  //没有返回值
        return 0;
    }

    @Override
    public int save(List t) {
        GreenDaoManager.getInstance().getDao(this.clazz).saveInTx(t);
        return 0;
    }

    @Override
    public int delete(Object o) {
        GreenDaoManager.getInstance().getDao(this.clazz).delete(o);
        return 0;
    }

    @Override
    public int delete(List list) {
        GreenDaoManager.getInstance().getDao(this.clazz).deleteInTx(list);
        return 0;
    }

    @Override
    public int delete(String[] columnNames, Object[] columnValues) {
        //  根据不同的 列的值去删除  ----
        try {
            String sql="delete from "+this.clazz.getSimpleName()+" where "+sqlCreate(columnNames,columnValues)+" ;";
            GreenDaoManager.getInstance().getDao(this.clazz).getDatabase().execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();LogUtils.d(e.toString());
        }
        return 0;
    }
    public String sqlCreate(String[] columnNames,Object[] columnValues) throws Exception {
        StringBuffer sb=new StringBuffer();
        if(columnNames.length!=columnValues.length||columnNames.length==0||columnValues.length==0){
            throw new Exception("列 和 值 不匹配 GreenDao");
        }else{
            for(int i=0;i<columnNames.length;i++)
            sb=sb.append(columnNames[i]+" = "+columnValues[i]+" and ");
        }
        return sb.substring(0,sb.length()-5).toString();
    }

    @Override
    public Long deleteById(Long id) {
        GreenDaoManager.getInstance().getDao(this.clazz).deleteByKey(id);
        return Long.valueOf(0);
    }

    @Override
    public int update(Object o) {
        GreenDaoManager.getInstance().getDao(this.clazz).update(o);
        return 0;
    }

    @Override
    public List queryAll() {

        return GreenDaoManager.getInstance().getDao(this.clazz).loadAll();
    }

    @Override
    public List query(String columnName, String columnValue) {
        if(GreenDaoManager.getInstance().getDao(this.clazz).queryRawCreate(columnName,columnValue)!=null){
            return GreenDaoManager.getInstance().getDao(this.clazz).queryRawCreate(columnName,columnValue).list();
        }
        return null;
    }

    @Override
    public List query(String[] columnNames, Object[] columnValues) {
        try {
            if(GreenDaoManager.getInstance().getDao(this.clazz).queryRawCreate(getStringWhere(columnNames,columnValues))!=null) {
                return GreenDaoManager.getInstance().getDao(this.clazz).queryRawCreate(getStringWhere(columnNames, columnValues)).list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sql  where =====占位符 生成------
     * @param columnNames
     * @param columnValues
     * @return
     * @throws Exception
     */
    public String getStringWhere(String[] columnNames,Object[] columnValues) throws Exception {
        StringBuffer sb=new StringBuffer();
        if(columnNames.length!=columnValues.length||columnNames.length==0||columnValues.length==0){
            throw new Exception("列 和 值 不匹配 GreenDao");
        }else{
            for(int i=0;i<columnNames.length;i++) {
                sb = sb.append(columnNames[i] + "=? and ");
            }
        }
        return sb.substring(0,sb.length()-5).toString();
    }

    @Override
    public Object queryById(Integer id) {
        return GreenDaoManager.getInstance().getDao(this.clazz).loadByRowId(id);
    }

    @Override
    public boolean isTableExists() {
        return false;
    }

    @Override
    public long count() {
        return GreenDaoManager.getInstance().getDao(this.clazz).count();
    }

    //--------map 转换为 clomnes clomunvalue
    @Override
    public List query(Map map) {
        String[] columnNames=null;
        Object[] columnValues=null;
        if(map!=null){
            Iterator<Map.Entry> entries = map.entrySet().iterator();
            columnNames=new String[map.entrySet().size()];
            columnValues=new Object[map.entrySet().size()];
            int i=0;
            while (entries.hasNext()) {
                Map.Entry entry = entries.next();
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                columnNames[i]= (String) entry.getKey();
                columnValues[i]=  entry.getValue();
                i++;
            }
            return query(columnNames,columnValues);
        }
        return null;
    }

    @Override
    public int deleteByIds(List ids) {
        GreenDaoManager.getInstance().getDao(this.clazz).deleteByKeyInTx(ids);
        return 0;
    }
    /**
     * 清除缓存
     */
    public void clearCacsh(){
        GreenDaoManager.getInstance().getDao(this.clazz).detachAll();
    }

    /**
     * 更新 其他都可以执行 sql 语句
     * @param sql
     */
    public void updateBySql(String sql){
        GreenDaoManager.getInstance().getDao(this.clazz).getDatabase().execSQL(sql);
    }

    /**
     * 查询最后一条数据
     *
     * @return
     */
    public  Object queryLast() {
        List<Object> list = queryAll();
        if (list != null&&list.size()>0) {
            return list.get(list.size() - 1);
        } else {
            return null;
        }
    }
}
