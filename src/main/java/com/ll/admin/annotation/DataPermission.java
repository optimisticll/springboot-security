package com.ll.admin.annotation;

import java.lang.annotation.*;

/**
 * @author lihaoxuan
 * @date 2020/12/29 11:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";
}
