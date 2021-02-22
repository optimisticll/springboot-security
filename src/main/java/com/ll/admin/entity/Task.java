package com.ll.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2020/12/30 9:25
 */
@Data
public class Task extends BaseEntity{
    private Integer taskid;

    private String jobname;//任务名

    private String description;//任务描述

    private String cronexpression;//cron表达式

    private String beanclass;//任务执行调度哪个类 包名+类名

    private String jobstatus;//任务状态

    private String jobgroup;//任务分组

    private String createuser;//创建者

    private String updateuser;//更新者



}
