package com.ll.admin.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lihaoxuan
 * @date 2020/12/21 17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String  msg;
}
