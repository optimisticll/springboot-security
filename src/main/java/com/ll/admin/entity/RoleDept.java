package com.ll.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:11
 */
@Data
public class RoleDept implements Serializable {
    private static final long serialVersionUID = 8925514042332235875L;

    private Integer roleId;

    private Integer deptId;
}
