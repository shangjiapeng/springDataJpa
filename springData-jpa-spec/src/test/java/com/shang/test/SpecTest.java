package com.shang.test;

import com.shang.dao.CustomerDao;
import com.shang.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {

    @Autowired
    private CustomerDao customerDao;


    /**
     * 测试 单个条件查询
     */
    @Test
    public void testSpec() {
        //匿名内部类
        /**
         * 自定义查询条件
         *    1 实现Specification 接口(提供泛型:查询的对象类型)
         *    2 实现toPredicate方法(构造查询条件)
         *    3 需要借助方法参数中的两个参数
         *        root:获取需要查询的对象属性
         *        CriteriaBuilder:构造查询条件的,内部封装了很多的查询条件(模糊匹配,精确查询)
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1 获取比较的属性
                Path<Object> custName = root.get("custName");
                //2 构造查询条件
                /**
                 * 第一个参数是 需要比较的属性(path对象)
                 * 第二个参数是 当前需要比较的取值
                 */
                Predicate predicate = cb.equal(custName, "张无忌");
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }


    /**
     * 测试多个条件查询
     */
    @Test
    public void testSpecs() {
        /**
         * root 获取属性
         *    客户名
         *    客户地址
         *
         * cb 构造查询
         *    1 构造客户名的精准查询
         *    2 构造客户地址的精准匹配查询
         *    3 将以上 两个查询练习起来
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //设置属性
                Path<Object> custName = root.get("custName");//客户名
                Path<Object> custAddress = root.get("custAddress");//地址
                //构造查询
                Predicate p1 = cb.equal(custName, "张无忌");//第一个参数 path属性 ;第二个参数 ,属性的取值
                Predicate p2 = cb.equal(custAddress, "湖北武汉");
                //将两个查询条件组合到一起 (与and | 或or)
                Predicate and = cb.and(p1, p2);
                return and;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }


    /**
     * 模糊查询
     * equal: 直接得到对象(属性) 然后进行比较即可
     * gt lt ge le like 得到path对象,根据path指定比较的参数类型,再去进行比较
     * 指定参数的类型 path.as(类型的字节码对象)
     */
    @Test
    public void testSpecLike() {
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //cb:构建查询，添加查询方式   like：模糊匹配
                //root：从实体Customer对象中按照custName属性进行查询
                Path<Object> custName = root.get("custName");//客户名
                Predicate like = cb.like(custName.as(String.class), "张%");
                return like;
            }
        };
        List<Customer> customerList = customerDao.findAll(spec);
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }


    /**
     * 排序查询
     * Sort.Direction.DESC
     * Sort.Direction.ASC
     */
    @Test
    public void testSpecSort() {
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //cb:构建查询，添加查询方式   like：模糊匹配
                //root：从实体Customer对象中按照custName属性进行查询
                Path<Object> custName = root.get("custName");//客户名
                Predicate like = cb.like(custName.as(String.class), "张%");
                return like;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "custId"); //第一个参数是 排序的顺序; 第二个属性是:排序的属性的名称
        List<Customer> customerList = customerDao.findAll(spec, sort);
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }


    /**
     * 分页查询
     * Specification :查询条件
     * Pageable :分页参数
     * <p>
     * 分页参数: 查询的页码, 每页查询的条数
     * findAll(Specification,Pageable);带有条件的分页查询
     * findAll(Pageable);无条件的分页查询
     * 返回 Page(springDataJpa 为我们封装好的pageBean 对象 )
     */
    @Test
    public void testSpecPage() {
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");//客户名
                Predicate like = cb.like(custName.as(String.class), "张%");
                return like;
            }
        };
        /**
         *pageRequest 是 pageable 接口的实现类
         *
         * 创建pageRequest 对象, 需要调用它的构造方法传入两个参数
         *   第一个参数 ;当前查询的页数
         *   第二个参数 ;每页插叙的数量
         */
        Pageable pageable = new PageRequest(0, 2);
        //分页查询
        Page<Customer> page = customerDao.findAll(spec, pageable);
        long count = customerDao.count(spec);
        System.out.println("统计结果为:"+count);
        //获取总页数
        int totalPages = page.getTotalPages();
        //获取总记录数
        long totalElements = page.getTotalElements();
        //获取列表数据
        List<Customer> content = page.getContent();
        System.out.println(content);
    }

}

