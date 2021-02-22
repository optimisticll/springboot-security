package com.ll.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2020/12/21 15:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
public class Role extends BaseEntity {
    private static final long serialVersionUID = -6525908145032868837L;

    private Integer roleId;

    private String roleName;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
    private String dataScope;

    private String description;

    private Date createtime;

    private Date updatetime;

    /**
     * 判断是否为admin用户
     * @return
     */
    public boolean isAdmin()
    {
        return isAdmin(this.getRoleId());
    }

    public static boolean isAdmin(Integer roleId)
    {
        return roleId != null && 1L == roleId;
    }

    public Role(Integer roleid){
        this.setRoleId(roleid);
    }
}
