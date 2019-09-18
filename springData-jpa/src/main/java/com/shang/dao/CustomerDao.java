package com.shang.dao;


import com.shang.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * jpa中只需要写dao层的接口,不需要编写接口的实现类
 * dao接口规范:
 * 1,需要继承两个接口
 * 2,需要提供相应的泛型
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 * <p>
 * jpql查询方式
 * 需要将jpql 语句配置到接口的方法上
 * 1.特有的查询,需要在dao 接口上配置方法
 * 2 在新添加的方法上,使用注解的形式配置jpql查询语句
 * 3 注解 :@Query
 */
public interface CustomerDao extends
        JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


    /**
     * 根据客户的名称查询客户
     * jpql: from Customer where custName =?
     */
    @Query(value = "from Customer where custName =?")
    public Customer findJpql(String custName);


    /**
     * 根据客户名称和客户的id 进行查询
     * from Customer where custName =? and custId= ?
     * <p>
     * 对于多个占位符参数
     * 赋值的时候,默认的情况下,要与jpql中占位符的顺序保持一致
     * <p>
     * 可以自定占位符参数的位置
     * ? 索引的方式,指定此占位符的取值来源
     *
     * @Query("from Customer where custName =?2 and custId= ?1")
     * public Customer finByNameAndId(Long id,String name);
     */
    @Query("from Customer where custName =? and custId= ?")
    public Customer finByNameAndId(String name, Long id);


    /**
     * 使用jpql完成更新操作
     * 更新2号客户的名称,为:"湖畔水月花"
     * sql:   update cst_customer set cust_name= ? where cust_id= ?
     * jpql:  update Customer set custName= ? where custId= ?
     *
     * @Query: 代表是进行查询
     * @Modifying: 声明此方法是用来进行更新操作
     */

    @Query(value = "update Customer set custName= ?2 where custId= ?1")
    @Modifying
    public void updateCustomer(Long custId, String custName);


    /**
     * 使用sql的形式来查询
     * 查询全部的客户
     * sql select * from cst_customer
     *    Query:配置sql查询
     *      value:sql语句
     *      nativeQuery: 查询方式
     *         true: sql 查询
     *         false:jpql查询
     */
    @Query(value = "select * from cst_customer where cust_name like ? ", nativeQuery = true)
    public List<Object[]> findSql(String custName);


    /**
     * 方法名称的约定
     *   findBy: 查询 (精确查询)
     *    对象中的属性名(首字母大写) : 查询的条件
     *    findByCustName  -- 根据客户的名称查询
     *    在springDataJpa的运行阶段
     *       会根据方法名进行解析
     */
    public  Customer findByCustName(String custName);


    /**
     * 方法名称的约定
     * findBy + 属性名称(根据属性名称完成匹配查询--- 模糊查询)
     *      findBy + 属性名称 + "查询方式(Like |isnull)"
     *
     *      findByCustNameLike
     */
    public List<Customer> findByCustNameLike(String custName);


    /**
     * 方法名称的约定
     *    多个条件的查询
     */
   public List<Customer>  findByCustNameLikeAndCustAddressLike(String custName,String CustAddress);

}
