package com.example.nanchen.realmdemo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Author:Bruce
 * time:2017/8/25.
 * contact：weileng143@163.com
 *
 * @description
 */

public class BaseRealmDao<T extends RealmModel> implements BaseDaoAble {
    /**
     * 一些有用的方法
     * between(), greaterThan(), lessThan(), greaterThanOrEqualTo() & lessThanOrEqualTo()
     * equalTo() & notEqualTo()
     * contains(), beginsWith() & endsWith()
     * isNull() & isNotNull()
     * isEmpty() & isNotEmpty()
     * sum，min，max，average只支持整型数据字段
     * userList.deleteFirstFromRealm(); //删除user表的第一条数据
     * userList.deleteLastFromRealm();//删除user表的最后一条数据
     * results.deleteAllFromRealm();//删除user表的全部数据
     * 参考
     * 链接：http://www.jianshu.com/p/37af717761cc
     * http://blog.csdn.net/sealong_/article/details/54613481
     */
    private Context context;
    private Realm mRealm;

    public BaseRealmDao(Context context) {
        mRealm = RealmUtils.getInstance(context).getRealm();
    }

    /**
     * 同步插入
     *
     * @param rm 需要插入的用户对象
     * @throws SQLException
     */
    @Override
    public void insert(RealmModel rm) throws SQLException {
        mRealm.beginTransaction();//必须先开启事务
        mRealm.copyToRealm(rm);//把User对象复制到Realm
        mRealm.commitTransaction();//提交事务
    }

    /**
     * 返回所有的User对象,并按照名字首字母排序
     *
     * @return User对象表
     * @throws SQLException
     */
    @Override
    public List<T> getAllUser(RealmModel rm) throws SQLException {
        List<T> list = null;
        RealmResults<RealmModel> results = (RealmResults<RealmModel>) mRealm.where(rm.getClass()).findAll();
//        //增序排列
//        dogs=dogs.sort("id");
//        //降序排列
//        dogs=dogs.sort("id", Sort.DESCENDING);
        results.sort("name", Sort.DESCENDING);//针对字符串的排序，但目前并不是支持所有字符集
        list = (List<T>) results;
//        mRealm.close();
        return list;
    }

    /**
     * 查询平均年龄
     *
     * @param
     * @return
     * @throws SQLException
     */
    @Override
    public int selectAllAverage(RealmModel rm) throws SQLException {
        RealmResults<RealmModel> results = (RealmResults<RealmModel>) mRealm.where(rm.getClass()).findAll();
        //查询平均年龄 average // 查询总年龄sum //最大年龄 max
        double age = results.average("age");//.sum //.max
        return (int) age;
    }

    /**
     * 查询总年龄
     *
     * @param
     * @return
     * @throws SQLException
     */
    public int selectSumAverage(RealmModel rm) throws SQLException {
        RealmResults<RealmModel> results = (RealmResults<RealmModel>) mRealm.where(rm.getClass()).findAll();
        //查询平均年龄 average // 查询总年龄sum //最大年龄 max
        Number age = results.sum("age");//.sum //.max
        int sumAge = age.intValue();
        return sumAge;
    }

    /**
     * 查询最大年龄
     *
     * @param rm
     * @return
     * @throws SQLException
     */
    @Override
    public int selectMaxAverage(RealmModel rm) throws SQLException {
        RealmResults<RealmModel> results = (RealmResults<RealmModel>) mRealm.where(rm.getClass()).findAll();
        //查询平均年龄 average // 查询总年龄sum //最大年龄 max
        Number age = results.max("age");//.sum //.max
        int sumAge = age.intValue();
        return sumAge;
    }

    /**
     * 更新一个User对象
     *
     * @param rm 需要更新的用户类
     * @return 返回更新后的User
     * @throws SQLException
     */
    @Override
    public RealmModel updateUser(RealmModel rm) throws SQLException {
        mRealm.beginTransaction();//开启事务
        RealmModel user1 = mRealm.copyToRealmOrUpdate(rm);
        mRealm.commitTransaction();//提交事务
//        mRealm.close();//必须关闭事务
        return user1;
    }

    /**
     * 更新某个字段，返回对象,这里事务结束只暂时在逻辑代码上处理了
     * @param user
     * @param name1 老名字
     * @return
     * @throws SQLException
     */
    @Override
    public RealmModel updateUser(RealmModel user, String name1) throws SQLException {
        mRealm.beginTransaction();//开启事务
        RealmModel results = (RealmModel) mRealm.where(user.getClass())
                .equalTo("name", name1)//查询出name为name1的User对象
                .findFirst();

//                .setName(name2);//修改查询出的第一个对象的名字;
//        mRealm.where(user)
//                .equalTo("name",name1)//查询出name为name1的User对象
//                .findFirst()
//                .setName(name2);//修改查询出的第一个对象的名字
//        mRealm.commitTransaction();
//        mRealm.close();
        return results;
    }

    /*
     * 根据id删除一个User
     * @param id 用户主键
     * @throws SQLException
     */
    @Override
    public void deleteUser(RealmObject rm, int id) throws SQLException {
        RealmObject user = mRealm.where(rm.getClass()).equalTo("id", id).findFirst();//删除id列值为id的行
        mRealm.beginTransaction();
        user.deleteFromRealm();//从数据库删除
        mRealm.commitTransaction();
//        mRealm.close();
    }

    /**
     * 返回第一个指定名字或者年龄的对象
     * @param name1 名字
     * @param age1  年龄
     */
    @Override
    public T findByNameOrAge(RealmModel rm, String name1, int age1) throws SQLException {
        T user = (T) mRealm.where(rm.getClass())
                .equalTo("name", name1)//相当于where name = name1
                .or()//或，连接查询条件，没有这个方式时会默认是&连接
                .equalTo("age", age1)//相当于where age = age1
                .findFirst();
        //整体相当于select * from (表名) where name = (传入的name) or age = （传入的age）limit 1;
//        mRealm.close();
        return user;
    }


    //===================================================异步方式=====================//

    /**
     * 异步插入User
     *
     * @param
     * @throws SQLException
     */
    @Override
    public void insertUserAsync(final RealmModel rm) throws SQLException {
        //一个Realm只能在同一个线程访问，在子线程中进行数据库操作必须重新获取realm对象
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();//开启事务
                realm.copyToRealm(rm);
                realm.commitTransaction();
                realm.close();//记得关闭事务
            }
        });

//        mRealm.executeTransactionAsync(new Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(user);
//            }
//        }, new Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                ToastUtil.showShortToast(mContext,"收藏成功");
//            }
//        }, new Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                ToastUtil.showShortToast(mContext,"收藏失败");
//            }
//        });
//        mRealm.close();//外面也不能忘记关闭事务
    }

    /**
     * 异步删除
     *
     * @param rm
     * @throws SQLException
     */
    @Override
    public void deleteUserAsync(final RealmObject rm) throws SQLException {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                mRealm.delete(user.getClass());
                RealmObject user1 = mRealm.where(rm.getClass()).equalTo("id", "").findFirst();//删除id列值为id的行
                user1.deleteFromRealm();
            }
        });
    }

    /**
     * 异步修改
     *
     * @param user id自己填写
     * @throws SQLException
     */
    @Override
    public void UpdateUserAsync(final RealmModel user) throws SQLException {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(user.getClass()).equalTo("id", "").findFirst();
            }
        });
    }

    /**
     * 异步查询
     *
     * @param user
     * @throws SQLException
     */
    @Override
    public void selectUserAsync(final RealmModel user) throws SQLException {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.where(user.getClass()).findAllAsync();
            }
        });
    }

    /**
     * 删除所有数据
     *
     * @throws SQLException
     */
    @Override
    public void deleteAll(Class T) throws SQLException {
        mRealm.beginTransaction();
//        mRealm.where(T).findAll().deleteAllFromRealm(); //指定删除
        mRealm.deleteAll();//删除所有
        mRealm.commitTransaction();
//        mRealm.close();
    }

    /**
     * 关闭数据库
     */
    @Override
    public void closeRealm() {
        mRealm.close();
//        mRealm.cancelTransaction();
    }

    /**
     * 关闭事务
     */
    public void commitToTransaction() {
        mRealm.commitTransaction();
    }

    /**
     userList.get(4).deleteFromRealm();//删除指定位置（第4条记录）的记录
     userList.deleteFromRealm(4);//删除指定位置（第4条记录）的记录
     userList.deleteFirstFromRealm(); //删除user表的第一条数据
     userList.deleteLastFromRealm();//删除user表的最后一条数据
     userList.deleteAllFromRealm();//删除user表的全部数据
     删除指定的数据
     */
    @Override
    public void deleteData(RealmModel mlist, final int index) {
        //先查找到数据
        final RealmResults<RealmObject> userList = (RealmResults<RealmObject>) mRealm.where(mlist.getClass()).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                userList.get(index).deleteFromRealm();//删除指定位置（第4条记录）的记录
//                userList.deleteFromRealm(index);//删除指定位置（第4条记录）的记录
//                userList.deleteFirstFromRealm(); //删除user表的第一条数据
//                userList.deleteLastFromRealm();//删除user表的最后一条数据
//                userList.deleteAllFromRealm();//删除user表的全部数据
            }
        });
    }


    //=======================================json============================================//

    /**
     * Realm 解析 JSON 时遵循如下规则：
     * 1.使用包含空值（null）的 JSON 创建对象：
     * 2.对于非必须（可为空值的属性），设置其值为 null；
     * 3.对于必须（不可为空值的属性），抛出异常；
     * 4.使用包含空值（null）的 JSON 更新对象：
     * 5.对于非必须（可为空值的属性），设置其值为 null；
     * 6.对于必须（不可为空值的属性），抛出异常；
     * 7.使用不包含对应属性的 JSON： * 该属性保持不变
     */
   /* **
            * 将Json数据添加到数据表中
 */
    private void insertDataFromJson() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                realm.createOrUpdateObjectFromJson(User.class, "{ id: 2, age: 50, name:\"sealong\",userId:\"admin\",
//                        dogs:[{id:5,name:\"龙骑士\",age:3},{id:6,name:\"猪骑士\",age:4},{id:7,name:\"马骑士\",age:5}]}");
            }
        });
    }
}
