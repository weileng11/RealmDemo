package com.example.nanchen.realmdemo;

import java.sql.SQLException;
import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Author:Bruce
 * time:2017/8/25.
 * contact：weileng143@163.com
 *
 * @description
 */

public interface BaseDaoAble<T extends RealmModel>{
    /**
     * 插入一个用户
     * @param user    需要插入的用户对象
     * @throws SQLException
     */
    void insert(T user) throws SQLException;

    /**
     * 获得所有的用户列表
     * @return 用户列表
     * @throws SQLException
     */
    List<T> getAllUser(T user) throws SQLException;

    /**
     * 查询平均年年龄
     * @return 用户列表
     * @throws SQLException
     */
    int selectAllAverage(T user) throws SQLException;

    /**
     * 查询总年龄
     * @return 用户列表
     * @throws SQLException
     */
    int selectSumAverage(T user) throws SQLException;

    /**
     * 查询最大年龄
     * @return 用户列表
     * @throws SQLException
     */
    int selectMaxAverage(T user) throws SQLException;


    /**
     * 更新一个用户
     * @param user 需要更新的用户类
     * @return      更新后的对象
     * @throws SQLException
     */
    T updateUser(T user) throws SQLException;

    /**
     * 根据姓名修改新姓名
     * @param name1 老名字
     * @param name2 新名字 到界面上直接修改,设置新名字
     * @throws SQLException
     */
    T updateUser(T user,String name1) throws SQLException;

    /**
     * 根据id删除用户
     * @param id 用户主键
     * @throws SQLException
     */
    void deleteUser(RealmObject user, int id) throws SQLException;

    /**
     * 按名字或者年龄查找第一个User
     */
    T findByNameOrAge(T user,String name1,int age1) throws SQLException;

    /**
     * 清楚所有
     * @throws SQLException
     */
    void deleteAll(Class<T> T) throws SQLException;


    //============================大多数情况下，Realm的增删改查操作足够快，可以在UI线程中执行操作。但是如果遇到较复杂的增删改查，
    // 或增删改查操作的数据较多时，就可以子线程进行操作。=============================//

    /**
     * 异步添加用户
     * @param user 需要添加的用户对象
     * @throws SQLException
     */
    void insertUserAsync(T user) throws SQLException;

    /**
     * 异步删除整个对象
     * @param user
     * @throws SQLException
     */
    void deleteUserAsync(RealmObject  user) throws SQLException;

    /**
     * 异步修改
     * @param user
     * @throws SQLException
     */
    void UpdateUserAsync(T user) throws SQLException;
    /**
     * 异步查
     * @param user
     * @throws SQLException
     */
    void selectUserAsync(T user) throws SQLException;
    /**
     * 关闭事务
     */
    void closeRealm();
    /**
     * 关闭提交的事物
     */
    void commitToTransaction();
    //删除指定的数据
    void deleteData(T mlist,int index);
}
