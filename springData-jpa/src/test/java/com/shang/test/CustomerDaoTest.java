package com.shang.test;

import com.shang.dao.CustomerDao;
import com.shang.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;
    /**
     * 保存客户：调用save(obj)方法
     * 当主键重复时,会覆盖之前的数据
     */
    @Test
    public void testSave() {
        Customer c = new Customer();
        c.setCustName("五爱空明");
        c.setCustLevel("vip");
        customerDao.save(c);
    }

    /**
     * 修改客户：调用save(obj)方法
     *   对于save方法的解释：
     *      如果执行此方法是对象中存在id属性，即为更新操作会先根据id查询，再更新
     *      如果执行此方法中对象中不存在id属性，即为保存操作

     */
    @Test
    public void testUpdate() {
        //根据id查询id为1的客户
        Customer customer = customerDao.findOne(1l);
        //修改客户名称
        customer.setCustName("可爱的姑娘");
        //更新
        customerDao.save(customer);
    }

    /**
     * 根据id删除：调用delete(id)方法
     */
    @Test
    public void testDelete() {
        customerDao.delete(6l);
    }

    /**
     * 根据id查询：调用findOne(id)方法
     */
    @Test
    public void testFindById() {
        Customer customer = customerDao.findOne(2l);
        System.out.println(customer);
    }


    /**
     * 查询所有
     */
    @Test
    public void testFindAll(){
        List<Customer> list = customerDao.findAll();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }


    /**
     * 统计查询 查询客户的总数量
     */
    @Test
    public void testCount(){
        long count = customerDao.count();//查询全部的客户数量
        System.out.println(count);
    }

    /**
     * 判断id为4 的客户是否存在
     */
    @Test
    public void testExist(){
        boolean b = customerDao.exists(4l);//查询全部的客户数量
        System.out.println(b);
    }

    /**
     *根据id从数据库中吃查询
     * @Transaction :保证getOne()正常运行
     *
     * findOne()
     *    em.find()  :立即加载
     *
     * getOne() 返回的是客户的动态代理对象,什么时候用,就什么时候加载
     *   em.getReference()  :延迟加载
     */
    @Test
    @Transactional
    public void testGetOne(){
        Customer customer = customerDao.getOne(4l);
        System.out.println(customer);
    }

}

