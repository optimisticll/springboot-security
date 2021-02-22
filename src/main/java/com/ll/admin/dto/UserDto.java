package com.ll.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lihaoxuan
 * @date 2020/12/22 16:30
 */
@Data
public class UserDto implements Serializable {
    Integer id;

    String password;

    String phone;

    String roleId;
}
