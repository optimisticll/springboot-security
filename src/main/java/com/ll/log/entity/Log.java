package com.ll.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2020/12/28 9:56
 */
@Data
public class Log implements Serializable {
    private Integer logid;

    private String username;

    private String ip;

    private String description;

    private String params;

    private String browser;

    private Long time;

    private String type;

    private String method;

    private Date createtime;

    private String exceptiondetail;

    public Log( String type,Long time) {
        this.type = type;
        this.time = time;
    }
}
