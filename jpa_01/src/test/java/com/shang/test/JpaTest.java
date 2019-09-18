package com.shang.test;

import com.shang.pojo.Customer;
import com.shang.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 测试类
 */
public class JpaTest {
    /**
     * 测试jpa的保存 :保存一个客户到数据库中
     * jsp 的操作步骤
     * 1 加载配置文件创建工厂,(实体管理类工厂)对象
     * 2 通过实体管理类工厂获取实体管理类
     * 3 获取事务对象,开启事务
     * 4 完成增删改查操作
     * 5 提交事务(回滚事务)
     * 6 释放资源
     */
    @Test
    public void testSave() {
//        //1 加载配置文件创建工厂,(实体管理类工厂)对象
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
//        //2 通过实体管理类工厂获取实体管理类
//        EntityManager manager = factory.createEntityManager();

        EntityManager manager = JpaUtils.getEntityManager();
        //3 获取事务对象,开启事务
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        //4 完成增删改查操作
        Customer customer = new Customer();
        customer.setCustName("你好jpa...");
        customer.setCustAddress("湖北省武汉市");
        //保存persist()
        manager.persist(customer);
        //5 提交事务
        tx.commit();
        //6 释放资源
        manager.close();
        //  factory.close();
    }

    /**
     * 根据id查询客户
     * 使用find()查询,--->立即加载
     * 1 查到的对象就是当前客户对象本身,
     * 2 会发送sql语句,查询数据库
     */
    @Test
    public void testFind() {
        //1 通过工具类获取entityManager 对象
        EntityManager manager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        //3 增删改查
        Customer customer = manager.find(Customer.class, 1l);
        //  System.out.println(customer);
        //4 提交事务
        transaction.commit();
        //5释放资源
        manager.close();
    }


    /**
     * 根据id查询客户
     * 使用getReference()查询,
     * 1 查到的对象是当前客户对象的动态代理对象,
     * 2 不会立即发送sql语句,当调用查询结果的时候才发送sql,什么时候用,什么时候发送sql
     * 懒加载(延迟加载)
     * 得到的是一个动态代理的对象
     * 什么时候用,什么时候加载
     */
    @Test
    public void testReference() {
        //1 通过工具类获取entityManager 对象
        EntityManager manager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        //3 增删改查
        Customer customer = manager.getReference(Customer.class, 1l);
        // System.out.println(customer);
        //4 提交事务
        transaction.commit();
        //5释放资源
        manager.close();
    }

    /**
     * 删除客户
     */
    @Test
    public void testRemove() {
        //1 通过工具类获取entityManager 对象
        EntityManager manager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        //3 增删改查
        //i 根据id查询
        Customer customer = manager.find(Customer.class, 1L);
        //ii调用remove()
        manager.remove(customer);
        // System.out.println(customer);
        //4 提交事务
        transaction.commit();
        //5释放资源
        manager.close();
    }

    /**
     * 更新客户
     */
    @Test
    public void testUpdate() {
        //1 通过工具类获取entityManager 对象
        EntityManager manager = JpaUtils.getEntityManager();
        //2 开启事务
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        //3 增删改查
        //i 根据id查询
        Customer customer = manager.find(Customer.class, 1L);
        //ii调用remove()
        customer.setCustName("张三丰");

        //merge()
        manager.merge(customer);
        // System.out.println(customer);
        //4 提交事务
        transaction.commit();
        //5释放资源
        manager.close();
    }

}

