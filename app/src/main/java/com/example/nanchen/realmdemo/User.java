package com.example.nanchen.realmdemo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Author:Bruce
 * time:2017/8/24.
 * contact：weileng143@163.com
 *
 * PrimaryKey——表示该字段是主键
 * PrimaryKey就是主键。使用@PrimaryKey来标注，字段类型必须是字符串（String）或整数（byte，short，int或long）以及它们的包装类型（Byte,Short, Integer, 或 Long）。一个model中不可以存在多个主键，使用字符串字段作为主键意味着字段被索引（注释@PrimaryKey隐式地设置注释@Index）。
 * Required——表示该字段非空
 * 在某些情况下，某些属性是不能为null的。使用@Required可用于用于强行要求其属性不能为空，只能用于Boolean, Byte, Short, Integer, Long, Float, Double, String, byte[] 和 Date。在其它类型属性上使用 @Required修饰会导致编译失败。
 * note：基本数据类型不需要使用注解 @Required，因为他们本身就不可为空。
 * Ignore——表示忽略该字段
 * 如果想忽略某个字段，则可以使用@Ignore修饰该字段，被秀@ignore修饰的字段在存储数据时会忽略该字段。
 * Index——添加搜索索引
 * 使用@index可以为字段添加搜索索引，使用索引以后使得插入的速度变慢，数据量也变得更大。但是在查询速度将变得更快，所以建议只在优化读取性能的特定情况时添加索引。
 */
public class User extends RealmObject {
    //主键必须添加注解
    @PrimaryKey
    private int id;//主键id
    @Required    //注解设为Required代表必须项
    private String name;//姓名
    //    @Index
    private int age;//年龄

    @Ignore   //表示忽视项,数据库不会存储该字段
    private boolean hasGrilFriend;//是否有女朋友

    public RealmList<Test> test;  //多对多

    public User() {
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(String name, int id, int age, boolean hasGrilFriend) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.hasGrilFriend = hasGrilFriend;
    }

    public RealmList<Test> getTest() {
        return test;
    }

    public void setTest(RealmList<Test> test) {
        this.test = test;
    }

    public boolean isHasGrilFriend() {
        return hasGrilFriend;
    }

    public void setHasGrilFriend(boolean hasGrilFriend) {
        this.hasGrilFriend = hasGrilFriend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", hasGrilFriend=" + hasGrilFriend +
                ", test=" + test.size() +
                '}';
    }
}
