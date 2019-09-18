package com.shang.test;

import com.shang.dao.RoleDao;
import com.shang.dao.UserDao;
import com.shang.domain.Role;
import com.shang.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 保存一个用户,保存一个角色
     * 多对多放弃外键的维护权,被动的一方放弃维护权
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testAdd() {
        User user = new User();
        user.setUserName("小李");
        Role role = new Role();
        role.setRoleName("java程序员");

        //配置用户到角色的关系,可以对中间表的数据进行维护
        user.getRoles().add(role);
        //配置角色到用户的关系,可以对中间表的数据进行维护
        role.getUsers().add(user);

        userDao.save(user);
        roleDao.save(role);
    }

    /**
     * 测试级联添加(保存一个用户同时保存他的角色信息)
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testCasCadeAdd() {
        User user = new User();
        user.setUserName("小李");
        Role role = new Role();
        role.setRoleName("java程序员");

        //配置用户到角色的关系,可以对中间表的数据进行维护
        user.getRoles().add(role);
        //配置角色到用户的关系,可以对中间表的数据进行维护
        role.getUsers().add(user);
        userDao.save(user);
    }

    /**
     * 测试级联删除(删除一个用户的同时删除它的角色)
     */
    @Test
    @Transactional  //开启事务
    @Rollback(false)//设置为不回滚
    public void testCasCadeRemove() {
        //1 查询一号用户
        User user = userDao.findOne(1L);
        //2 删除一号用户
        userDao.delete(user);
    }

}
