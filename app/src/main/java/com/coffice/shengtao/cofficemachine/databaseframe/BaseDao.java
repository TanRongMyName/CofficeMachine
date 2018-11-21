package com.coffice.shengtao.cofficemachine.databaseframe;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> {
    /**
     * 增，带事务操作
     *
     * @param t 泛型实体类
     * @return 影响的行数
     * @throws SQLException SQLException异常
     */
public abstract int save(T t);
/**
 * 增或更新，带事务操作
 * @param t 泛型实体类
 * @return Dao.CreateOrUpdateStatus
 * @throws SQLException SQLException异常
 */
//public abstract Dao.CreateOrUpdateStatus saveOrUpdate(T t);


/**
 * 增，带事务操作
 * @param t 泛型实体类集合
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
public abstract int save(List<T> t);


/**
 * 删，带事务操作
 *
 * @param t 泛型实体类
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
public abstract int delete(T t);


/**
 * 删，带事务操作
 *
 * @param list 泛型实体类集合
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
public abstract int delete(List<T> list);


/**
 * 删，带事务操作
 *
 * @param columnNames  列名数组
 * @param columnValues 列名对应值数组
 * @return 影响的行数
 * @throws SQLException              SQLException异常
 * @throws InvalidParameterException InvalidParameterException异常
 */
public abstract int delete(String[] columnNames, Object[] columnValues);


/**
 * 删，带事务操作
 *
 * @param id id值
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
public abstract Long deleteById(Long id);


/**
 * 删，带事务操作
 * @param ids id集合
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
public abstract int deleteByIds(List<Long> ids) ;

/**
 * 删，带事务操作
 *
 * @param preparedDelete PreparedDelete类
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
//public abstract int delete(PreparedDelete<T> preparedDelete);


/**
 * 改，带事务操作
 *
 * @param t 泛型实体类
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
public abstract int update(T t);


/**
 * 改，带事务操作
 * @param preparedUpdate PreparedUpdate对象
 * @return 影响的行数
 * @throws SQLException SQLException异常
 */
//public abstract int update(PreparedUpdate<T> preparedUpdate) ;

/**
 * 查，带事务操作
 *
 * @return 查询结果集合
 * @throws SQLException SQLException异常
 */
public abstract List<T> queryAll() ;


/**
 * 查，带事务操作
 *
 * @param preparedQuery PreparedQuery对象
 * @return 查询结果集合
 * @throws SQLException SQLException异常
 */
//public abstract List<T> query(PreparedQuery<T> preparedQuery) ;


/**
 * 查，带事务操作
 *
 * @param columnName  列名
 * @param columnValue 列名对应值
 * @return 查询结果集合
 * @throws SQLException SQLException异常
 */
public abstract List<T> query(String columnName, String columnValue) ;


/**
 * 查，带事务操作
 *
 * @param columnNames
 * @param columnValues
 * @return 查询结果集合
 * @throws SQLException SQLException异常
 */
public abstract List<T> query(String[] columnNames, Object[] columnValues);


/**
 * 查，带事务操作
 *
 * @param map 列名与值组成的map
 * @return 查询结果集合
 * @throws SQLException SQLException异常
 */
public abstract List<T> query(Map<String, Object> map) ;


/**
 * 查，带事务操作
 *
 * @param id id值
 * @return 查询结果集合
 * @throws SQLException SQLException异常
 */
public abstract T queryById(Integer id) ;
}
