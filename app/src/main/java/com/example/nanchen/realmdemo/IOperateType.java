package com.example.nanchen.realmdemo;

public class IOperateType {
	 /**
	  *  操作类型(1-添加,2-修改,3-删除)
	  */
	public int OperateType;
	
	/**
	 * 还可以抽象出id,StoreNo.暂时没这样做
	 * 
	 */


	//可跟查询条件
//.or()                      或者
//.beginsWith()              以xxx开头
//.endsWith()                以xxx结尾
//.greaterThan()             大于
//.greaterThanOrEqualTo()    大于或等于
//.lessThan()                小于
//.lessThanOrEqualTo()       小于或等于
//.equalTo()                 等于
//.notEqualTo()              不等于
//.findAll()                 查询所有
//.average()                 平均值
//.beginGroup()              开始分组
//.endGroup()                结束分组
//.between()                 在a和b之间
//.contains()                包含xxx
//.count()                   统计数量
//.distinct()                去除重复
//.findFirst()               返回结果集的第一行记录
//.isNotEmpty()              非空串
//.isEmpty()                 为空串
//.isNotNull()               非空对象
//.isNull()                  为空对象
//.max()                     最大值
//.maximumDate()             最大日期
//.min()                     最小值
//.minimumDate()             最小日期
//.sum()                     求和
}
