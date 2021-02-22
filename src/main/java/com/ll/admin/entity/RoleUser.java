package com.ll.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/21 15:59
 */
@Data
public class RoleUser implements Serializable {
    private static final long serialVersionUID = 8545514045582235838L;

    private Integer userId;

    private Integer roleId;
}
