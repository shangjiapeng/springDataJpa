package com.shang.test;


import com.shang.dao.CustomerDao;
import com.shang.dao.LinkManDao;
import com.shang.domain.Customer;
import com.shang.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 对象导航查询测试
 *    总结:
 *        从一方查询多方,默认延迟加载
 *        从多方查询一方,默认立即加载
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    /**
     * 测试对象导航查询(查询一个对象的时候,通过此对象查询所有关联的对象)
     * could not initialize proxy - no Session
     */
    @Test
    @Transactional //解决在java代码中的no session 问题
    public void testQuery(){
        //查询客户
        Customer customer = customerDao.getOne(2L);
        //对象导航查询,此客户下的所有的联系人
        Set<LinkMan> linkmans = customer.getLinkmans();
        for (LinkMan linkman : linkmans) {
            System.out.println(linkman);
        }
    }

    /**
     * 对象导航查询延迟加载
     *     一查多 默认使用的是延迟加载形式
     *    调用get方法并不会立即发送加载,而是在使用关联对象的时候才会查询
     * 修改配置,将延迟加载修改为立即加载
     *      fetch :需要配置到多表映射关系 的注解 上
     *        EAGER 立即加载
     *         LAZY  延迟加载
     */
    @Test
    @Transactional //解决在java代码中的no session 问题
    public void testQuery2(){
        //查询客户
        Customer customer = customerDao.findOne(2L);
        //对象导航查询,此客户下的所有的联系人
        Set<LinkMan> linkmans = customer.getLinkmans();
        for (LinkMan linkman : linkmans) {
            System.out.println(linkman);
        }
    }

    /**
     * 从联系人对象导航查询到他的所属客户
     *   默认采用的是立即加载
     * 设置为延迟加载 :fetch = FetchType.LAZY
     */
    @Test
    @Transactional //解决在java代码中的no session 问题
    public void testQuery3(){
        //查询客户
        LinkMan linkMan = linkManDao.findOne(2L);
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }

}
