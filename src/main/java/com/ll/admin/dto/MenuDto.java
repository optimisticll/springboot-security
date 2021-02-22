package com.ll.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/22 16:08
 */
@Data
public class MenuDto implements Serializable {
    private Integer id;
    private Integer parentId;
    private String checkArr = "0";
    private String title;
}
