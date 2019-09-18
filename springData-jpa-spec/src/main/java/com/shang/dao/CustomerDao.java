package com.shang.dao;
import com.shang.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * jpa中只需要写dao层的接口,不需要编写接口的实现类
 * dao接口规范:
 * 1,需要继承两个接口
 * 2,需要提供相应的泛型
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 *
 * specification: 查询条件
 * 自定义我们自己Specification 实体类
 *    实现
 *       //root 查询的跟对象 (查询任何的属性都可以从根对象中获取)
 *       //CriteriaQuery 顶层的查询对象 ,自定义的查询方式 (一般不用)
 *       //CriteriaBuilder 查询的构造器,封装了很多的查询条件
 *   Predicate toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);
 }
 */
public interface CustomerDao extends
        JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
