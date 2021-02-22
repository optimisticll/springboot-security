package com.ll.admin.entity;

import lombok.Data;

/**
 * @author lihaoxuan
 * @date 2020/12/29 13:50
 */
@Data
public class Dept extends BaseEntity {
    private static final long serialVersionUID = 8925514045582235633L;

    private Integer deptId;

    private Integer parentId;

    private String ancestors;

    private String deptName;

    private Integer sort;

    private Integer status;
}
