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
 */
public interface CustomerDao extends
        JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
