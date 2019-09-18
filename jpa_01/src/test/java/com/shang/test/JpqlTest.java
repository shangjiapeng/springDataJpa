package com.shang.test;

import com.shang.pojo.Customer;
import com.shang.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * joql测试类
 */
public class JpqlTest {
    /**
     * 查询全部
     * sql  语句查询的是表以及表中的字段信息select *from cst_customer
     * jpql 语句查询的是实体类和类中的属性 from com.shang.pojo.Customer
     */
    @Test
    public void testFindAll() {
        //1 获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3 查询全部
        String jpql = "from com.shang.pojo.Customer";//可以写全限定类名,也可以简写
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //发送查询的jpql语句,并封装结果集
        List resultList = query.getResultList();
        for (Object obj : resultList) {
            System.out.println(obj);
        }
        //4 提交事务
        tx.commit();
        //5 释放资源
        entityManager.close();
    }

    /**
     * 排序查询 倒叙查询全部的客户(根据id)
     * sql  select *from cst_customer order by cust_id DESC
     * jpql from Customer order by custId desc
     */
    @Test
    public void testOrders() {
        //1 获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3 查询全部
        String jpql = "from Customer order by custId desc";//可以写全限定类名,也可以简写
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //发送查询的jpql语句,并封装结果集
        List resultList = query.getResultList();//直接 将查询结果封装成list集合
        for (Object obj : resultList) {
            System.out.println(obj);
        }
        //4 提交事务
        tx.commit();
        //5 释放资源
        entityManager.close();
    }

    /**
     * 统计查询 统计客户的总人数
     * sql  select count(cust_id) from cst_customer
     * jpql select count(custId) from Customer
     */
    @Test
    public void testCount() {
        //1 获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3 查询全部
        //i 根据jpql语句创建Query 查询对象
        String jpql = "select count(custId) from Customer";//可以写全限定类名,也可以简写
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //ii 对参数赋值
        //iii 发送查询并封装结果
        Object result = query.getSingleResult();//得到唯一的结果
        System.out.println(result);
        //4 提交事务
        tx.commit();
        //5 释放资源
        entityManager.close();
    }

    /**
     * 分页查询
     * sql  select * from cst_customer limit ? ?
     * jpql  from Customer
     */
    @Test
    public void testPage() {
        //1 获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3 查询全部
        //i 根据jpql语句 创建Query查询对象
        String jpql = "from Customer";//可以写全限定类名,也可以简写
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //ii 对参数赋值-- 分页参数
        query.setFirstResult(3);
        query.setMaxResults(3);

        //iii 发送查询并封装结果
        List resultList = query.getResultList();//得到唯一的结果
        for (Object obj : resultList) {
            System.out.println(obj);
        }
        //4 提交事务
        tx.commit();
        //5 释放资源
        entityManager.close();
    }


    /**
     * 条件查询  查询客户列表中姓张的客户
     * sql  select * from cst_customer where cust_name like "张%"
     * jpql  from Customer where custName like ?
     */
    @Test
    public void testCondition() {
        //1 获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        //3 查询全部
        //i 根据jpql语句 创建Query查询对象
        String jpql = " from Customer where custName like ? ";//可以写全限定类名,也可以简写
        Query query = entityManager.createQuery(jpql);//创建Query查询对象,query对象才是执行jpql的对象
        //ii 对参数赋值-- 占位符参数
        query.setParameter(1,"张%");

        //iii 发送查询并封装结果
        List resultList = query.getResultList();//得到唯一的结果
        for (Object obj : resultList) {
            System.out.println(obj);
        }
        //4 提交事务
        tx.commit();
        //5 释放资源
        entityManager.close();
    }

}

