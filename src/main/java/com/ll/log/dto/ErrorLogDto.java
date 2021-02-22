package com.ll.log.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2020/12/28 16:19
 */
@Data
public class ErrorLogDto implements Serializable {
    private String userName;

    private String ip;

    private String params;

    private String description;

    private String exceptionDetail;

    private String method;

    private String browser;

    private Date createTime;
}
