package com.ll.admin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lihaoxuan
 * @date 2020/12/22 17:30
 */
@Data
public class MenuIndexDto implements Serializable {
    private Integer id;

    private Integer parentId;

    private String title;

    private String icon;

    private Integer type;

    private String href;

    private String permission;

    private List<MenuIndexDto> children;
}
