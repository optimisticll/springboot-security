package com.ll.admin.entity;

import lombok.Data;

/**
 * @author lihaoxuan
 * @date 2020/12/21 15:57
 */
@Data
public class Menu extends BaseEntity{
    private static final long serialVersionUID = -6525908145032868815L;

    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String icon;

    private Integer type;

    private String url;

    private String permission;

    private Integer sort;
}

