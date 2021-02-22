package com.ll.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/29 14:16
 */
@Data
public class UserJob implements Serializable {
    private static final long serialVersionUID = 8925514045582235321L;

    private Integer userId;

    private Integer jobId;
}
