package com.shang.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户的数据模型
 */
@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_code")
    private String userCode;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_state")
    private String userState;

    /**
     * 多对多关系映射
     * 1 声明表关系的配置
     *  @ManyToMany( targetEntity = Role.class)//多对多
     *  targetEntity :代表对方的实体类字节码
     *
     * 2 配置中间表() 包含两个外键
     *   @JoinTable
     *      name:中间表的名称
     *      joinColumns: 当前对象在中间表中的外键
     *          @JoinColumn的数组
     *              name:外键名
     *              referencedColumnName:参照的表的主键名
     *      inverseJoinColumns: 对方对象在时间表中的外键
     */
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role",
            //joinColumns:  当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "sys_user_id", referencedColumnName = "user_id")},
            //inverseJoinColumns: 对方对象在时间表中的外键
            inverseJoinColumns ={@JoinColumn(name = "sys_role_id",referencedColumnName = "role_id")}
        )
    private Set<Role> roles = new HashSet<Role>(0);

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysUser [userId=" + userId + ", userCode=" + userCode + ", userName=" + userName + ", userPassword="
                + userPassword + ", userState=" + userState + "]";
    }
}
