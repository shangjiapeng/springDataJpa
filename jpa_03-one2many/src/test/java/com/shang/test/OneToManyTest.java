package com.shang.test;

import com.shang.dao.CustomerDao;
import com.shang.dao.LinkManDao;
import com.shang.domain.Customer;
import com.shang.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 1对多测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /**
     * 保存操作
     * 需求:
     * 保存一个客户和一个联系人
     * 要求：
     * 创建一个客户对象和一个联系人对象
     * 建立客户和联系人之间关联关系（双向一对多的关联关系）
     * 先保存客户，再保存联系人
     * 问题：
     * 当我们建立了双向的关联关系之后，先保存主表，再保存从表时：
     * 会产生2条insert和1条update.
     * 而实际开发中我们只需要2条insert。
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testAdd() {
        Customer customer = new Customer();
        customer.setCustName("TBD云集中心");
        customer.setCustLevel("VIP客户");
        customer.setCustSource("网络");
        customer.setCustIndustry("商业办公");
        customer.setCustAddress("昌平区北七家镇");
        customer.setCustPhone("010-84389340");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("TBD联系人");
        linkMan.setLkmGender("male");
        linkMan.setLkmMobile("13811111111");
        linkMan.setLkmPhone("010-34785348");
        linkMan.setLkmEmail("98354834@qq.com");
        linkMan.setLkmPosition("老师");
        linkMan.setLkmMemo("还行吧");

        // 配置了客户到联系人的关系
        customer.getLinkmans().add(linkMan); //由于配置了1到多的关联(则发送一条update语句)
        //联系人到客户的关系
        linkMan.setCustomer(customer);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }


    /**
     * 级联操作
     *  操作一个对象的同时,操作它的关联对象
     *  1 需要区分操作主体
     *  2 需要在操作主体的实体类上,添加级联属性(需要添加到多表映射关系的注解上)
     *  3 cascade (配置级联)
     */

    /**
     * 测试级联添加  保存一个客户的同时,保存客户的所有联系人
     *      需要在操作主体的实体类上,添加cascade属性
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testCascadeAdd() {
        Customer customer = new Customer();
        customer.setCustName("百度");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李子");

        linkMan.setCustomer(customer);
        customer.getLinkmans().add(linkMan);
        //只用添加主体类即可
        customerDao.save(customer);
    }

    /**
     * 测试级联删除
     *    删除一号客户的同时,删除1号客户的所有的联系人
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testCascadeRemove() {
        //查询1号客户
        Customer customer = customerDao.findOne(1L);
        //删除1号客户
        customerDao.delete(customer);
    }

}
