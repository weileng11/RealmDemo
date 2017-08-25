package com.example.nanchen.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import io.realm.RealmList;

/**
 * Author:Bruce
 * time:2017/8/24.
 * contact：weileng143@163.com*
 */
public class MainActivity extends AppCompatActivity {
    private BaseRealmDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = new BaseRealmDao(this);
        try {
            userDao.deleteAll(Test.class);//先删除所有，以免demo出现主键已经存在的异常
//            userDao.deleteAll(User.class);//先删除所有，以免demo出现主键已经存在的异常
            User user = new User();
            user.setId(10);
            user.setName("小刺猬");
            user.setAge(22);
            user.setHasGrilFriend(true);
            Test test2 = new Test();
            test2.setMid(11);
            test2.setName("一座古城");
            RealmList<Test> tests=new RealmList<>();
            tests.add(test2);
            user.setTest(tests);
            userDao.insert(user);

            Test test = new Test();
            test.setMid(14);
            test.setName("一座古城");
            userDao.insert(test);


             Log.d("flag", "插入小刺猬----" + userDao.getAllUser(new User()).toString());
            Log.d("flag", "一座古城----" + userDao.getAllUser(new Test()).toString());

            for (int i = 0; i < 5; i++) {
                userDao.insert(new User(i,"一座深林"+i,20+i));
            }

            for (int i = 0; i < 5; i++) {
                userDao.insert(new Test(i,"一座古城"+i));
            }
            Log.d("flag","插入5个对象----"+userDao.getAllUser(new Test()).toString());
            Log.d("flag","插入5个对象----"+userDao.getAllUser(new User()).toString());
            Log.d("flag","查询1----"+userDao.findByNameOrAge(new User(),"一座深林1",20));
            Log.d("flag","查询2----"+userDao.findByNameOrAge(new User(),"一座深林2",23));
//            User u2=new User();
//            u2.setId(1);
//            u2.setName("yizuoshenlin");
            User u= (User) userDao.updateUser(new User(),"一座深林3");
            u.setName("yizuoshenlinxx");
            userDao.commitToTransaction();
            Log.d("flag","更新1----"+userDao.findByNameOrAge(new User(),"yizuoshenlinxx",23));
            userDao.deleteUser(new User(),0);//删除0
//            Log.d("flag","删除后查看----"+userDao.getAllUser().toString());
            Log.d("flag","平均年龄----"+userDao.selectMaxAverage(new User()));
            List<User> allUser = userDao.getAllUser(new Test());
            userDao.deleteData(new User(),3);

            Log.d("flag","查询所有的数据----"+ allUser.size());
            Log.d("flag","查询指定删除后的数据----"+userDao.getAllUser(new User()).toString());
            Log.d("flag","查询所有删除的数据----"+ userDao.getAllUser(new User()).size());
            //统一关闭事务
            userDao.closeRealm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    最后在销毁Activity或Fragment时，要取消掉异步任务

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (addTask!=null&&!addTask.isCancelled()){
//            addTask.cancel();
//        }
    }
}
