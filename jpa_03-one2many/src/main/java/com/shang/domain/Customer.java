package com.shang.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户实体类
 *     配置映射关系
 *       1 实体类和数据库表的映射关系
 *       2 实体类中属性和表中字段的映射关系
 *       @Entity: 声明实体类
 *       @Table: 配置实体类和表的映射关系
 *          name: 配置数据库表的名称
 *       @Id//声明主键
 *       @GeneratedValue
 *       /配置主键的生成策略,
 *       GenerationType.IDENTITY:自增
 *                  底层的数据库 必须支持自动增长(mysql)
 *       GenerationType.SEQUENCE: 序列
 *                  底层数据库必须支持序列(Oracle)
 *       GenerationType.TABLE:
 *                  jpa提供的一种机制,通过一张数据库表的形式帮助我们完成主键自增
 *       GenerationType.AUTO:
 *                  由程序自动帮助我们选择主键生成策略
 *
 *       @Column:配置属性和字段的映射关系
 *       name:数据表中字段的名称
 */

@Entity
@Table(name = "cst_customer")
public class Customer implements Serializable {

    @Id//声明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cust_id") //指定和表中cust_id字段的映射关系
    private Long custId;//客户端的主键

    @Column(name="cust_name")
    private String custName;//客户名称

    @Column(name="cust_source")
    private String custSource;//客户来源

    @Column(name="cust_industry")
    private String custIndustry;//客户所属行业

    @Column(name="cust_level")
    private String custLevel;//客户的级别

    @Column(name="cust_address")
    private String custAddress;//客户的地址

    @Column(name="cust_phone")
    private String custPhone;//客户的联系方式

    //配置客户和联系人的一对多关系
//    @OneToMany(targetEntity=LinkMan.class)
//    @JoinColumn(name="lkm_cust_id",referencedColumnName="cust_id")
    /**
     * 放弃外键的维护权
     *   mappedBy: 对方配置的 关系属性名称
     *   cascade: 配置级联(一般在操作主体上配置) :
     *                      all     :所有
     *                     merge   :更新
     *                     persist :保存
     *                     remove  :删除
     *   fetch 配置关联对象的加载方式
     *         EAGER 立即加载
     *         LAZY  延迟加载
     */
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<LinkMan> linkmans = new HashSet<LinkMan>(0);


    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public Set<LinkMan> getLinkmans() {
        return linkmans;
    }
    public void setLinkmans(Set<LinkMan> linkmans) {
        this.linkmans = linkmans;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }


}

