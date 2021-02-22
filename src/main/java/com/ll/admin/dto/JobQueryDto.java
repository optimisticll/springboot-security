package com.ll.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:17
 */
@Data
public class JobQueryDto implements Serializable {
    private String queryName;

    private Integer queryStatus;
}
