package com.ll.admin.dto;

import com.ll.admin.entity.BaseEntity;
import lombok.Data;

/**
 * @author lihaoxuan
 * @date 2020/12/29 13:51
 */
@Data
public class DeptDto extends BaseEntity {
    private Integer id;

    private Integer parentId;

    private String checkArr = "0";

    private String title;
}
