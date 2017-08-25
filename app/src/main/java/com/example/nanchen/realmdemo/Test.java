package com.example.nanchen.realmdemo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Author:Bruce
 * time:2017/8/24.
 * contact：weileng143@163.com
 *
 * @description
 */

public class Test extends RealmObject {
    //主键必须添加注解
    @PrimaryKey
    private int mid;//主键id
    @Required    //注解设为Required代表必须项
    private String name;//姓名

    public Test(){

    }

    public Test(int mid, String name) {
        this.mid = mid;
        this.name = name;
    }


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
