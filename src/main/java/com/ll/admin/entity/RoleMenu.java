package com.ll.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/21 16:00
 */
@Data
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 8925514045582235875L;

    private Integer roleId;

    private Integer permissionId;
}
