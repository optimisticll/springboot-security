package com.ll.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysDict对象", description="数据字典")
public class Dict extends BaseEntity{
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    private Integer dictId;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典描述")
    private String description;

    @ApiModelProperty(value = "字典排序")
    private Integer sort;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;
}
