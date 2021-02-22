package com.ll.admin.dto;

import com.ll.admin.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/29 10:49
 */
@Data
public class RoleDto extends Role {
    private static final long serialVersionUID = -5784234789156935003L;

    private List<Integer> menuIds;

    private  List<Integer> deptIds;

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }
}
