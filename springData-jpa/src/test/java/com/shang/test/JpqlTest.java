package com.shang.test;

import com.shang.dao.CustomerDao;
import com.shang.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpqlTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindJPQL() {
        Customer customer = customerDao.findJpql("张三丰");
        System.out.println(customer);
    }

    @Test
    public void testfinByNameAndId() {
        Customer customer = customerDao.finByNameAndId("张三丰", 1l);
        System.out.println(customer);
    }


    /**
     * 测试jpql的更新操作
     * springDataJpa中使用jpql完成 更新/删除 操作
     * 需要手动添加事务的支持
     * 默认会在执行结束之后 回滚事务
     *
     * @Rollback: 设置是否回滚
     */
    @Test
    @Transactional //添加事务的支持
    @Rollback(value = false)
    public void testUpdateCustomer() {
        customerDao.updateCustomer(3L, "湖畔水月花");
    }

    /**
     * 测试 sql 查询
     */
    @Test
    public void testFindSql() {
        List<Object[]> list = customerDao.findSql("张%");
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
    }


    /**
     * 测试 方法命名规则 精确 查询方式
     *
     */
    @Test
    public void testNaming() {
        Customer customer = customerDao.findByCustName("张无忌");
        System.out.println(customer);
    }


    /**
     * 测试 方法命名规则的 模糊 查询方式
     */
    @Test
    public void findByCustNameLike() {
        List<Customer> nameList = customerDao.findByCustNameLike("张%");
        for (Customer customer : nameList) {
            System.out.println(customer);
        }

    }

    /**
     * 测试 方法名称的约定
     *    多个条件的查询
     */
   @Test
    public void findByCustNameLikeAndCustAddressLike() {
        List<Customer> nameList = customerDao.
                findByCustNameLikeAndCustAddressLike("张%","%襄阳市%");
        for (Customer customer : nameList) {
            System.out.println(customer);
        }

    }




}
