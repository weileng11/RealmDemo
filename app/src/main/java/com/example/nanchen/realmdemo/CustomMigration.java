package com.example.nanchen.realmdemo;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Author:Bruce
 * time:2017/8/24.
 * contact：weileng143@163.com
 * 版本升级  说明：在RealmUtils类填写对应的版本号

 当数据结构发生变化是，需要升级数据库。对于Realm来说，数据库升级就是迁移操作，把原来的数据库迁移到新结构的数据库。（体验：略麻烦）
 例1：User类发生变化，移除age，新增个@Required的id字段。
 User版本：version 0
 String name;
 int    age;
 User版本：version 1
 @Required
 String    id;
 String name;

 链接：http://www.jianshu.com/p/37af717761cc
 *
 * @description  创建迁移类CustomMigration，需要实现RealmMigration接口
 */

public class CustomMigration implements RealmMigration {

    /**
     * 升级数据库
     * @param realm
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0 && newVersion == 1) {
            RealmObjectSchema personSchema = schema.get("User");
            //新增@Required的id
            RealmObjectSchema realmObjectSchema = personSchema
                    .addField("id", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("id", "1");//为id设置值
                        }
                    })
                    .removeField("age");//移除age属性
            oldVersion++;
        }
//        链接：http://www.jianshu.com/p/37af717761cc
    }


//    例2：加入Dog类，User中加入Dog集合。
//    User版本：version 1
//
//    @Required
//    String    id;
//    String name;
//    User版本：version 2
//
//    @Required
//    private String id;
//    private String name;
//    private RealmList<Dog> dogs;
//    Dog类
//
//    public class Dog extends RealmObject {
//        private String name;
//        private int age;
//    }
//    在迁移类CustomMigration中，继续添加处理方法。
//
//            if (oldVersion == 1 && newVersion == 2) {
//        //创建Dog表
//        RealmObjectSchema dogSchema = schema.create("Dog");
//        dogSchema.addField("name", String.class);
//        dogSchema.addField("age", int.class);
//
//        //User中添加dogs属性
//        schema.get("User")
//                .addRealmListField("dogs", dogSchema)
//                .transform(new RealmObjectSchema.Function() {
//                    @Override
//                    public void apply(DynamicRealmObject obj) {
//                        //为已存在的数据设置dogs数据
//                        DynamicRealmObject dog = realm.createObject("Dog");
//                        dog.set("name", "二哈");
//                        dog.set("age", 2);
//                        obj.getList("dogs").add(dog);
//                    }
//                });
//        oldVersion++;
//    }
}
