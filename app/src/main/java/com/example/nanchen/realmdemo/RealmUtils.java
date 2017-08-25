package com.example.nanchen.realmdemo;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Author:Bruce
 * time:2017/8/25.
 * contact：weileng143@163.com
 * @description
 */
public class RealmUtils {
    private Context context;
    private static RealmUtils mInstance;
    private String realName = "myRealm.realm";

    private RealmUtils(Context context){
        this.context = context;
    }

    public static RealmUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (RealmUtils.class){
                if (mInstance == null){
                    mInstance = new RealmUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获得Realm对象
     * @return
     */
    public Realm getRealm(){
        return Realm.getInstance(new RealmConfiguration.Builder(context).name(realName).schemaVersion(0).build());
    }

//    RealmConfiguration config = new RealmConfiguration.Builder()
//            .name("myrealm.realm") //文件名
//            .schemaVersion(1)
//            .migration(new CustomMigration())//升级数据库
//            .build();

}
