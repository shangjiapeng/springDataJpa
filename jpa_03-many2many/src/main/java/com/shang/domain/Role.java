package com.shang.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色的数据模型
 */
@Entity
@Table(name="sys_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;
    @Column(name="role_name")
    private String roleName;
    @Column(name="role_memo")
    private String roleMemo;


    //多对多关系映射
    //@ManyToMany(targetEntity = User.class)//配置多表关系
    //@JoinTable(name="sys_user_role",//中间表的名称
            //中间表user_role_rel字段关联sys_role表的主键字段role_id
           // joinColumns={@JoinColumn(name="sys_role_id",referencedColumnName="role_id")},
            //中间表user_role_rel的字段关联sys_user表的主键user_id
           // inverseJoinColumns={@JoinColumn(name="sys_user_id",referencedColumnName="user_id")}
    //)

    //放弃外键的维护权
    @ManyToMany(mappedBy="roles")
    private Set<User> users = new HashSet<User>(0);


    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleMemo() {
        return roleMemo;
    }
    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    @Override
    public String toString() {
        return "SysRole [roleId=" + roleId + ", roleName=" + roleName + ", roleMemo=" + roleMemo + "]";
    }
}

