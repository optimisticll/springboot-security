package com.ll.log.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2020/12/28 16:20
 */
@Data
public class LogDto implements Serializable {
    private String userName;

    private String ip;

    private String params;

    private String description;

    private String method;

    private Long time;

    private String browser;

    private Date createTime;
}
